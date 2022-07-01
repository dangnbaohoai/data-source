For building and running the application you need: 
--- Back_end
JDK 8 or later
Maven 3.0 or later
spring-boot 2.4.0
tool
IntelliJ IDEA Ultimate 2021.1.1
--- Front_end
Npm
Yarn version 1.22.11
Visual code
    


This project have 3 module for 3 different server. Each module have there own way to install and run a service.
Below is the summary of direction to installation inlocal, for more detail or deloy for production please ascess those file.
[modules]
    - [Back_end] : Readme.md 
        ----------------------------------------------------------------------------------------------------------------------------------
            `mvn spring-boot:run`
            Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources with:

            `mvn clean package`
            Then you can run the JAR file with:

            java -jar target/*.jar
            Instead of mvn you can also use the maven-wrapper ./mvnw to ensure you have everything necessary to run the Maven build.
        ----------------------------------------------------------------------------------------------------------------------------------
    
    - [Front-end admin] : Readme.md 
    - [Front-end user] : Readme.md 
        ------------------------------------------------------------------
            ## Yarn setup using npm
            npm install --global yarn

            ## Yarn set version
            yarn set version 1.22.11

            ## Project setup
            yarn install

            ### Compiles and hot-reloads for development
            yarn serve
        ------------------------------------------------------------------