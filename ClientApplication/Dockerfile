FROM maven:3.6.3-openjdk-11
RUN mkdir -p /ClientApplication
WORKDIR /ClientApplication
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM maven:3.6.3-openjdk-11
COPY --from=0 /ClientApplication/target/ClientApplication-0.0.1-SNAPSHOT.jar /ClientApplication.jar
EXPOSE 8090
RUN chmod 777 /ClientApplication.jar
CMD ["java", "-jar", "/ClientApplication.jar"]