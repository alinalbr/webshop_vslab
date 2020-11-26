FROM maven:3.6.3-openjdk-11
RUN mkdir -p /ProductCoreApplication
WORKDIR /ProductCoreApplication
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM maven:3.6.3-openjdk-11
COPY --from=0 /ProductCoreApplication/target/ProductCoreApplication-0.0.1-SNAPSHOT.jar /ProductCoreApplication.jar
EXPOSE 8080
RUN chmod 777 /ProductCoreApplication.jar
CMD ["java", "-jar", "/ProductCoreApplication.jar"]
