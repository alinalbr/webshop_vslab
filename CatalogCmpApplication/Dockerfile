FROM maven:3.6.3-openjdk-11
RUN mkdir -p /CatalogCmpApplication
WORKDIR /CatalogCmpApplication
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM maven:3.6.3-openjdk-11
COPY --from=0 /CatalogCmpApplication/target/CatalogCmpApplication-0.0.1-SNAPSHOT.jar /CatalogCmpApplication.jar
EXPOSE 8084
RUN chmod 777 /CatalogCmpApplication.jar
CMD ["java", "-jar", "/CatalogCmpApplication.jar"]
