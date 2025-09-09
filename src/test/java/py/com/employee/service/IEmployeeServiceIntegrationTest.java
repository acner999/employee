package py.com.employee.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import py.com.employee.exception.EmployeeNotFoundException;
import py.com.employee.model.Employee;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integracion para verificar que el patron de interfaces funciona
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class IEmployeeServiceIntegrationTest {

    @Autowired
    private IEmployeeService employeeService; // Inyeccion por interfaz
    
    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        testEmployee = new Employee(null, "Test Employee", "Test Position", new BigDecimal("8.5"));
    }

    @Test
    void testInterfaceInjection() {
        // Verifica que Spring inyecta correctamente la implementacion
        assertNotNull(employeeService);
        assertTrue(employeeService instanceof EmployeeService);
    }

    @Test
    void testCreateEmployee() {
        // Given
        long initialCount = employeeService.countEmployees();
        
        // When
        Employee created = employeeService.createEmployee(testEmployee);
        
        // Then
        assertNotNull(created.getId());
        assertEquals(testEmployee.getNombre(), created.getNombre());
        assertEquals(initialCount + 1, employeeService.countEmployees());
    }

    @Test
    void testGetEmployeeById() {
        // Given
        Employee created = employeeService.createEmployee(testEmployee);
        
        // When
        Employee found = employeeService.getEmployeeById(created.getId());
        
        // Then
        assertEquals(created.getId(), found.getId());
        assertEquals(created.getNombre(), found.getNombre());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        // When & Then
        assertThrows(EmployeeNotFoundException.class, 
            () -> employeeService.getEmployeeById(999L));
    }

    @Test
    void testExistsById() {
        // Given
        Employee created = employeeService.createEmployee(testEmployee);
        
        // When & Then
        assertTrue(employeeService.existsById(created.getId()));
        assertFalse(employeeService.existsById(999L));
    }

    @Test
    void testGetEmployeesByPuesto() {
        // Given
        employeeService.createEmployee(testEmployee);
        employeeService.createEmployee(new Employee(null, "Other Employee", "Test Position", new BigDecimal("7.2")));
        
        // When
        List<Employee> employees = employeeService.getEmployeesByPuesto("Test Position");
        
        // Then
        assertEquals(2, employees.size());
        assertTrue(employees.stream().allMatch(emp -> "Test Position".equals(emp.getPuesto())));
    }

    @Test
    void testGetAllEmployees() {
        // Given
        long initialCount = employeeService.countEmployees();
        employeeService.createEmployee(testEmployee);
        
        // When
        List<Employee> allEmployees = employeeService.getAllEmployees();
        
        // Then
        assertEquals(initialCount + 1, allEmployees.size());
    }
}
