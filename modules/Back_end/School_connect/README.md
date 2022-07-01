Requirements
For building and running the application you need:

JDK 8 or later
Maven 3.0 or later
spring-boot 2.4.0
tool
IntelliJ IDEA Ultimate 2021.1.1

Running the application locally
There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the de.codecentric.springbootsample.Application class from your IDE.
Alternatively you can use the Spring Boot Maven plugin like so:

Clone
To get started you can simply clone this repository using git:
git clone https://github.com/PhuNguyenThang/Capstone1.git

Build an executable JAR
You can run the application from the command line using:

`mvn spring-boot:run`
Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources with:

`mvn clean package`
Then you can run the JAR file with:

java -jar target/*.jar
Instead of mvn you can also use the maven-wrapper ./mvnw to ensure you have everything necessary to run the Maven build.

To view Swagger 3 API docs
Run the server and browse to localhost:8082/swagger-ui.html

Deploying to Heroku
Application was deploying in https://school-connection.herokuapp.com/swagger-ui.html
