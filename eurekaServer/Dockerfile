FROM maven:3.6.3-openjdk-11
RUN mkdir -p /eurekaServer
WORKDIR /eurekaServer
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM maven:3.6.3-openjdk-11
COPY --from=0 /eurekaServer/target/eurekaServer-0.0.1-SNAPSHOT.jar /eurekaServer.jar
EXPOSE 8761
RUN chmod 777 /eurekaServer.jar
CMD ["java", "-jar", "/eurekaServer.jar"]
