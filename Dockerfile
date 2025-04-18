FROM gradle:8-jdk21-alpine AS builder 
# Definindo diretório de trabalho para o projeto 
WORKDIR /app 
# Copiando todos os arquivos do projeto para o contêiner (incluindo o gradlew) 
COPY . . 
# Dando permissão para o Gradle Wrapper 
RUN chmod +x gradlew 
# Executando o build da aplicação com o Gradle Wrapper 
RUN gradle build 
# Etapa 2: Imagem final para rodar a aplicação 
FROM gradle:8-jdk21-alpine AS runtime 
# Definindo diretório de trabalho para o contêiner final 
WORKDIR /app 
# Copiando o JAR gerado na etapa anterior 
COPY --from=builder /app/build/libs/*.jar app.jar 
# Expondo a porta usada pela aplicação 
EXPOSE 8080 
# Comando para rodar a aplicação 
ENTRYPOINT ["java", "-jar", "app.jar"]