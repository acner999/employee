-- Datos de ejemplo para empleados (salarios en millones de guaraníes)
INSERT INTO employees (nombre, puesto, salario) VALUES 
('Carlos Mendoza', 'Desarrollador Senior', 12.5),
('Ana Martinez', 'Analista de Sistemas', 8.8),
('Luis Rodriguez', 'Project Manager', 15.2),
('Maria Gonzalez', 'QA Tester', 7.5),
('Jose Villamayor', 'DevOps Engineer', 11.0),
('Carmen Silva', 'UX Designer', 9.3);

-- Ejemplos de consultas
-- SELECT * FROM employees WHERE salario > 10.0; -- Empleados que ganan más de 10 millones
-- SELECT AVG(salario) FROM employees; -- Promedio salarial
