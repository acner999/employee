package py.com.employee.service;

import py.com.employee.model.Employee;

import java.util.List;

/**
 * Interfaz para servicios de empleados
 * Define el contrato para las operaciones de negocio
 */
public interface IEmployeeService {
    
    /**
     * Obtiene todos los empleados
     * @return lista de todos los empleados
     */
    List<Employee> getAllEmployees();
    
    /**
     * Busca un empleado por ID
     * @param id el ID del empleado
     * @return el empleado encontrado
     * @throws py.com.employee.exception.EmployeeNotFoundException si no se encuentra
     */
    Employee getEmployeeById(Long id);
    
    /**
     * Busca empleados por puesto
     * @param puesto el puesto a buscar
     * @return lista de empleados con ese puesto
     */
    List<Employee> getEmployeesByPuesto(String puesto);
    
    /**
     * Crea un nuevo empleado
     * @param employee el empleado a crear
     * @return el empleado creado con ID asignado
     */
    Employee createEmployee(Employee employee);
    
    /**
     * Verifica si existe un empleado con el ID dado
     * @param id el ID a verificar
     * @return true si existe, false si no
     */
    boolean existsById(Long id);
    
    /**
     * Cuenta el total de empleados
     * @return número total de empleados
     */
    long countEmployees();
}
