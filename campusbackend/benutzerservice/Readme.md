# How to start this service with docker?

## Getting Started

- Download Docker Desktop Client from here: https://www.docker.com/
- Download Maven from here: https://maven.apache.org/
or you use your eclipse/intellij maven to install this build.

### Installing

Go into the benutzerservice directory and intall the build and ready up for docker:
```
mvn clean install
```

## Running the service

Stay in the directory and open the terminal(powershell, bash etc.) and type:
```
docker-compose build
```
This creates the docker build of the service.
```
docker-compose up
```
This starts the services.
```
docker-compose down
```
This ends the services.

## RESTApi Swagger Website

http://localhost:8082/swagger-ui.html

## Built With

This service will be build with one of the Following Frameworks
* [Spring](https://spring.io/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Swagger](https://swagger.io/) - RestAPI Documentation
