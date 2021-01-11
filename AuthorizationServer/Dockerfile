FROM maven:3.6.3-openjdk-11
RUN mkdir -p /AuthorizationServer
WORKDIR /AuthorizationServer
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM maven:3.6.3-openjdk-11
COPY --from=0 /AuthorizationServer/target/AuthorizationServer-0.0.1-SNAPSHOT.jar /AuthorizationServer.jar
EXPOSE 8086
RUN chmod 777 /AuthorizationServer.jar
CMD ["java", "-jar", "/AuthorizationServer.jar"]