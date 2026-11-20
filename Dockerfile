# ==========================================
# Stage 1: Build con Maven y Eclipse Temurin 21
# ==========================================
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Descargar dependencias para aprovechar cache de capas Docker
COPY demo/pom.xml .
RUN mvn dependency:go-offline -B || true

# Copiar codigo fuente y compilar artefacto
COPY demo/src ./src
RUN mvn clean package -DskipTests

# ==========================================
# Stage 2: Runtime liviano con JRE Temurin 21
# ==========================================
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copiar artefacto generado desde la etapa de compilacion
COPY --from=build /app/target/*.jar app.jar

# Render asigna dinamicamente la variable de entorno PORT
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]
