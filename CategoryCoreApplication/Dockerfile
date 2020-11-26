FROM maven:3.6.3-openjdk-11
RUN mkdir -p /CategoryCoreApplication
WORKDIR /CategoryCoreApplication
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM maven:3.6.3-openjdk-11
COPY --from=0 /CategoryCoreApplication/target/CategoryCoreApplication-0.0.1-SNAPSHOT.jar /CategoryCoreApplication.jar
EXPOSE 8082
RUN chmod 777 /CategoryCoreApplication.jar
CMD ["java", "-jar", "/CategoryCoreApplication.jar"]