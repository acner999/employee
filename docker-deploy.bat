@echo off
echo ========================================
echo  Employee API - Docker Deployment
echo ========================================

echo.
echo 1. Compilando la aplicacion...
call mvnw.cmd clean package -DskipTests

if errorlevel 1 (
    echo Error al compilar la aplicacion
    pause
    exit /b 1
)

echo.
echo 2. Construyendo imagen Docker...
docker-compose build

if errorlevel 1 (
    echo Error al construir la imagen Docker
    pause
    exit /b 1
)

echo.
echo 3. Levantando contenedores...
docker-compose up -d

if errorlevel 1 (
    echo Error al levantar los contenedores
    pause
    exit /b 1
)

echo.
echo ========================================
echo  Aplicacion levantada exitosamente!
echo ========================================
echo.
echo URLs disponibles:
echo  - API: http://localhost:8080
echo  - Swagger: http://localhost:8080/swagger-ui/index.html
echo  - H2 Console: http://localhost:8080/h2-console
echo  - Health Check: http://localhost:8080/actuator/health
echo.
echo NOTA: Los salarios estan en millones de guaranies
echo Ejemplo: 12.5 = 12,500,000 Gs.
echo.
echo Para ver logs: docker-compose logs -f employee-api
echo Para parar: docker-compose down
echo.
pause
