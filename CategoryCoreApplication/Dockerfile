FROM maven:3.6.3-openjdk-11
RUN mkdir -p /CategoryCoreApplication
WORKDIR /CategoryCoreApplication
COPY . /CategoryCoreApplication/
EXPOSE 8082
CMD ["mvn", "spring-boot:run"]
