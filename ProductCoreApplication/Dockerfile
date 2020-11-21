FROM maven:3.6.3-jdk-11
RUN mkdir -p /ProductCoreApplication
WORKDIR /ProductCoreApplication
COPY . /ProductCoreApplication/
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]
