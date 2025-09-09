package py.com.employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import py.com.employee.exception.EmployeeNotFoundException;
import py.com.employee.model.Employee;
import py.com.employee.service.EmployeeService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests unitarios para EmployeeController
 * Prueba todos los endpoints de la API
 */
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees() throws Exception {
        // Given
        List<Employee> employees = Arrays.asList(
                new Employee(1L, "Juan Perez", "Desarrollador", new BigDecimal("8.5")),
                new Employee(2L, "Maria Garcia", "Analista", new BigDecimal("7.2"))
        );
        when(employeeService.getAllEmployees()).thenReturn(employees);

        // When & Then
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Juan Perez"))
                .andExpect(jsonPath("$[1].nombre").value("Maria Garcia"));
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee_WhenEmployeeExists() throws Exception {
        // Given
        Employee employee = new Employee(1L, "Juan Perez", "Desarrollador", new BigDecimal("8.5"));
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        // When & Then
        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.puesto").value("Desarrollador"))
                .andExpect(jsonPath("$.salario").value(8.5));
    }

    @Test
    void getEmployeeById_ShouldReturn404_WhenEmployeeNotExists() throws Exception {
        // Given
        when(employeeService.getEmployeeById(anyLong()))
                .thenThrow(new EmployeeNotFoundException("Empleado con ID 999 no encontrado"));

        // When & Then
        mockMvc.perform(get("/api/employees/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Empleado con ID 999 no encontrado"));
    }

    @Test
    void getEmployeesByPuesto_ShouldReturnFilteredEmployees() throws Exception {
        // Given
        List<Employee> developers = Arrays.asList(
                new Employee(1L, "Juan Perez", "Desarrollador", new BigDecimal("8.5")),
                new Employee(3L, "Carlos Lopez", "Desarrollador", new BigDecimal("9.2"))
        );
        when(employeeService.getEmployeesByPuesto("Desarrollador")).thenReturn(developers);

        // When & Then
        mockMvc.perform(get("/api/employees/search")
                        .param("puesto", "Desarrollador"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].puesto").value("Desarrollador"))
                .andExpect(jsonPath("$[1].puesto").value("Desarrollador"));
    }

    @Test
    void createEmployee_ShouldReturnCreatedEmployee_WhenValidInput() throws Exception {
        // Given
        Employee inputEmployee = new Employee(null, "Ana Rodriguez", "Designer", new BigDecimal("7.8"));
        Employee savedEmployee = new Employee(9L, "Ana Rodriguez", "Designer", new BigDecimal("7.8"));
        
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(savedEmployee);

        // When & Then
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputEmployee)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(9))
                .andExpect(jsonPath("$.nombre").value("Ana Rodriguez"))
                .andExpect(jsonPath("$.puesto").value("Designer"))
                .andExpect(jsonPath("$.salario").value(7.8));
    }

    @Test
    void createEmployee_ShouldReturn400_WhenInvalidInput() throws Exception {
        // Given - Employee with invalid data (empty name, negative salary)
        Employee invalidEmployee = new Employee(null, "", "Designer", new BigDecimal("-5.0"));

        // When & Then
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmployee)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }
}
