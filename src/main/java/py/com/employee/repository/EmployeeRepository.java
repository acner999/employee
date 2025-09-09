package py.com.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.employee.model.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    /**
     * Busca empleados por puesto (ignora mayúsculas/minúsculas)
     * @param puesto el puesto a buscar
     * @return lista de empleados con ese puesto
     */
    List<Employee> findByPuestoIgnoreCase(String puesto);
}
