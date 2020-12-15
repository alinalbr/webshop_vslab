FROM maven:3.6.3-openjdk-11
RUN mkdir -p /zuul
WORKDIR /zuul
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM maven:3.6.3-openjdk-11
COPY --from=0 /zuul/target/zuul-0.0.1-SNAPSHOT.jar /zuul.jar
EXPOSE 8085
RUN chmod 777 /zuul.jar
CMD ["java", "-jar", "/zuul.jar"]