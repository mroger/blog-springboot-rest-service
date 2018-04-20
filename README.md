# Blog Service - Spring REST

This is a simple blog service that uses Spring Boot REST service

## Build

`mvn clean install`

## Run

`mvn spring-boot:run`

## Swagger

This server API was documented with Swagger 2.0

To view the JSON generated, type in your browser with the server running:

`http://localhost:8080/v2/api-docs`

To view the swagger-ui interface, type the following URL in your browser:

`http://localhost:8080/swagger-ui.html`

and click on the controller links to access the operations and interact with the API

## Postman collection

Is possible to see the Postman collection of requests inside the folder **postman**.

## Future things to add

* Custom error payload
* HATEOAS
* Spring security to apply authentication and authorization
* Documentation via tests with Ascii-doctor
