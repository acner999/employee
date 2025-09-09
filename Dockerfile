# Usar imagen base de OpenJDK 21
FROM openjdk:21-jdk-slim

# Información del mantenedor
LABEL maintainer="employee-api"

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR de la aplicación
# El JAR se genera en target/ después de ejecutar mvn package
COPY target/employee-*.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Configurar variables de entorno
ENV SPRING_PROFILES_ACTIVE=docker

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
