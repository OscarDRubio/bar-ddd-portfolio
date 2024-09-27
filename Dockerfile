# Usa una imagen base de Java
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de tu aplicación
COPY target/spaceship-crud-1.0-SNAPSHOT.jar app.jar

# Expone el puerto que utiliza tu aplicación
EXPOSE 8080

# Comando para ejecutar tu aplicación
CMD ["java", "-jar", "app.jar"]