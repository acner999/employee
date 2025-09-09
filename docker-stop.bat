@echo off
echo ========================================
echo  Deteniendo Employee API
echo ========================================

echo.
echo Parando contenedores...
docker-compose down

if errorlevel 1 (
    echo Error al parar los contenedores
    pause
    exit /b 1
)

echo.
echo ========================================
echo  Contenedores detenidos exitosamente!
echo ========================================
echo.
echo Para eliminar tambien las imagenes: docker-compose down --rmi all
echo Para eliminar volumenes: docker-compose down -v
echo.
pause
