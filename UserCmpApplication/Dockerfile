FROM maven:3.6.3-openjdk-11
RUN mkdir -p /UserCmpApplication
WORKDIR /UserCmpApplication
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM maven:3.6.3-openjdk-11
COPY --from=0 /UserCmpApplication/target/UserCmpApplication-0.0.1-SNAPSHOT.jar /UserCmpApplication.jar
EXPOSE 8083
RUN chmod 777 /UserCmpApplication.jar
CMD ["java", "-jar", "/UserCmpApplication.jar"]
