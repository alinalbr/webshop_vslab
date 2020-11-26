FROM maven:3.6.3-openjdk-11
RUN mkdir -p /UserCoreApplication
WORKDIR /UserCoreApplication
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM maven:3.6.3-openjdk-11
COPY --from=0 /UserCoreApplication/target/UserCoreApplication-0.0.1-SNAPSHOT.jar /UserCoreApplication.jar
EXPOSE 8081
RUN chmod 777 /UserCoreApplication.jar
CMD ["java", "-jar", "/UserCoreApplication.jar"]
