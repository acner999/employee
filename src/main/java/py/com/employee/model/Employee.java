package py.com.employee.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entidad que representa un empleado en el sistema
 */
@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(nullable = false)
    private String nombre;
    
    @NotBlank(message = "El puesto no puede estar vacio")
    @Column(nullable = false)
    private String puesto;
    
    @NotNull(message = "El salario es obligatorio")
    @Positive(message = "El salario debe ser mayor a 0 ")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salario; // Salario en millones de guaranies
}
