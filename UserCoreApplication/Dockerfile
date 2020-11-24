FROM maven:3.6.3-openjdk-11
RUN mkdir -p /UserCoreApplication
WORKDIR /UserCoreApplication
COPY . /UserCoreApplication/
EXPOSE 8081
CMD ["mvn", "spring-boot:run"]
