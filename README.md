# Châtop APP

## Prerequiste:

Java 17

Spring Boot

MySQL database (MySQL 8.4 Configurator can be used to create a local database, for exemple)

NPM

## Start the project
Git clone:

> git clone https://github.com/williammiere/Developpez-le-back-end-en-utilisant-Java-et-Spring.git

Install MySQL (MySQL Community Server - GPL):

> Port: 3306

> Create the username and the password 

> Create a new schema named: test

> Give rights for this username on the test schema


Set the user's environment variables in a .env file at the project's root:

> Variable for MySQL datasource url: `URL`

> Variable for MySQL password: `PASSWORD`

> Variable for MySQL username: `USERNAME`

> Variable for the JWT secret: `SECRET`

Go inside folder:

> cd Developpez-le-back-end-en-utilisant-Java-et-Spring

### Front-end

Install dependencies:

> npm install

Launch Front-end:

> npm run start;

Open your browser at http://localhost:4200/

### Back-end

Go inside folder:

> cd back-end

Install dependencies:

> mvn install

Launch Back-end:

> mvn spring-boot:run or use Spring Boot Dashboard extension for VS Code


If you want to change any configuration you can modify `Backend/src/main/resources/application.properties`

Endpoints are available at http://localhost:8080/

Swagger is available at http://localhost:8080/swagger-ui/index.html#/

## Test data set
> Email: `test@test.com`
> Name: `test`
> Pass: `test!31`
