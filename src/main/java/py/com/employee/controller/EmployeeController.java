package py.com.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.employee.model.Employee;
import py.com.employee.service.EmployeeService;

import java.util.List;

/**
 * Controlador REST para manejo de empleados
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee Management", description = "API para gestion de empleados")
public class EmployeeController {
    
    private final EmployeeService employeeService;
    
    /**
     * Obtiene todos los empleados registrados
     */
    @GetMapping
    @Operation(summary = "Obtener todos los empleados", description = "Retorna la lista completa de empleados")
    @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida exitosamente")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
    
    /**
     * Busca un empleado por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener empleado por ID", description = "Retorna un empleado específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<Employee> getEmployeeById(
            @Parameter(description = "ID del empleado", required = true)
            @PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }
    
    /**
     * Busca empleados por puesto de trabajo
     */
    @GetMapping("/search")
    @Operation(summary = "Buscar empleados por puesto", description = "Retorna empleados que coincidan con el puesto especificado")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    public ResponseEntity<List<Employee>> getEmployeesByPuesto(
            @Parameter(description = "Puesto a buscar", required = true)
            @RequestParam String puesto) {
        List<Employee> employees = employeeService.getEmployeesByPuesto(puesto);
        return ResponseEntity.ok(employees);
    }
    
    /**
     * Crea un nuevo empleado
     */
    @PostMapping
    @Operation(summary = "Crear nuevo empleado", description = "Crea un nuevo empleado en memoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de empleado inválidos")
    })
    public ResponseEntity<Employee> createEmployee(
            @Parameter(description = "Datos del empleado a crear", required = true)
            @Valid @RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }
}
