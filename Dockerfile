# imagem base do Java 21
FROM openjdk:21-jdk-slim

# define o diretório de trabalho dentro do container
WORKDIR /app

# copia o jar para dentro do container
COPY target/cep-0.0.1-SNAPSHOT.jar app.jar

# expõe a porta 8080
EXPOSE 8080

# comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
