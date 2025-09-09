package py.com.employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.employee.exception.EmployeeNotFoundException;
import py.com.employee.model.Employee;
import py.com.employee.repository.EmployeeRepository;

import java.util.List;

/**
 * Servicio para manejo de empleados
 * Contiene la logica de negocio principal
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    
    /**
     * Obtiene todos los empleados
     * @return lista de todos los empleados
     */
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    /**
     * Busca un empleado por ID
     * @param id el ID del empleado
     * @return el empleado encontrado
     * @throws EmployeeNotFoundException si no se encuentra el empleado
     */
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Empleado con ID " + id + " no encontrado"));
    }
    
    /**
     * Busca empleados por puesto
     * @param puesto el puesto a buscar
     * @return lista de empleados con ese puesto
     */
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByPuesto(String puesto) {
        return employeeRepository.findByPuestoIgnoreCase(puesto);
    }
    
    /**
     * Crea un nuevo empleado en memoria (aunque se pierda al reiniciar)
     * @param employee el empleado a crear
     * @return el empleado creado con su ID generado
     */
    public Employee createEmployee(Employee employee) {
        // Para cumplir con el requisito de mantener datos en memoria
        // aunque se pierdan al reiniciar, usamos H2 en modo memory
        return employeeRepository.save(employee);
    }
}
