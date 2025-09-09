package py.com.employee.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.employee.exception.EmployeeNotFoundException;
import py.com.employee.model.Employee;
import py.com.employee.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Servicio para manejo de empleados
 * Implementa la interfaz IEmployeeService con toda la logica de negocio
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService implements IEmployeeService {
    
    private final EmployeeRepository employeeRepository;
    
    /**
     * Inicializa datos hardcodeados al arrancar la aplicacion
     */
    @PostConstruct
    public void initializeData() {
        if (employeeRepository.count() == 0) {
            // Hardcodear lista inicial de al menos 3 empleados (requisito tecnico)
            Employee emp1 = new Employee(null, "Carlos Mendoza", "Desarrollador Senior", new BigDecimal("12.5"));
            Employee emp2 = new Employee(null, "Ana Martinez", "Analista de Sistemas", new BigDecimal("8.8"));
            Employee emp3 = new Employee(null, "Luis Rodriguez", "Arquitecto de Software", new BigDecimal("15.2"));
            Employee emp4 = new Employee(null, "Maria Gonzalez", "Product Manager", new BigDecimal("11.0"));
            
            employeeRepository.save(emp1);
            employeeRepository.save(emp2);
            employeeRepository.save(emp3);
            employeeRepository.save(emp4);
        }
    }
    
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
    
    /**
     * Verifica si existe un empleado con el ID dado
     * @param id el ID a verificar
     * @return true si existe, false si no
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }
    
    /**
     * Cuenta el total de empleados
     * @return número total de empleados
     */
    @Transactional(readOnly = true)
    public long countEmployees() {
        return employeeRepository.count();
    }
}
