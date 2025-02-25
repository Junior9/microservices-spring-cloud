# Usa a imagem oficial do OpenJDK 21 baseada no Alpine
FROM eclipse-temurin:21-jdk-alpine

# Cria um usuário não root para rodar a aplicação
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Define o argumento para o arquivo JAR
ARG JAR_FILE=target/*.jar

# Copia o arquivo JAR para dentro do contêiner
COPY ${JAR_FILE} app.jar

# Define o ponto de entrada para rodar o aplicativo
ENTRYPOINT ["java", "-jar", "/app.jar"]
