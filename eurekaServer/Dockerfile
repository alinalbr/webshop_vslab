FROM maven:3.6.3-openjdk-11
RUN mkdir -p /eurekaServer
WORKDIR /eurekaServer
COPY . /eurekaServer/
EXPOSE 8761
CMD ["mvn", "spring-boot:run"]
