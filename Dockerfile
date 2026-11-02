# ============ ETAPA 1: compilar o projeto ============
# Imagem com Maven + JDK 21 (só serve para construir o JAR)
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia SÓ o pom.xml primeiro e baixa as dependências.
# Se você mexer só no código, o Docker reaproveita esta camada
# e o build seguinte fica bem mais rápido (cache).
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Agora copia o código e gera o JAR
COPY src ./src
RUN mvn -B clean package -DskipTests

# ============ ETAPA 2: rodar a aplicação ============
# Imagem enxuta, só com o Java para EXECUTAR (sem Maven, sem código-fonte)
FROM eclipse-temurin:21-jre

WORKDIR /app

# Boa prática: não rodar como administrador dentro do container
RUN useradd --system --no-create-home appuser
USER appuser

# Pega só o JAR pronto da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Documenta a porta que a aplicação usa
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]