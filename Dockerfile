
# Etapa 1: Build
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copia os arquivos necessários para utilizar o Maven Wrapper
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN chmod +x mvnw

# Baixa as dependências antes de copiar o restante do código
RUN ./mvnw dependency:go-offline

# Copia o código-fonte
COPY src/ src/

# Compila e empacota a aplicação
RUN ./mvnw clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
