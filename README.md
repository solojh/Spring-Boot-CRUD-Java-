## Spring Boot User Module CRUD Operations Tutorial
This repository contains the source code and resources for a video tutorial series on developing a Java web application using Spring Boot, IntelliJ IDEA, and various other technologies. 
In this tutorial, you will learn to implement the User module with CRUD operations (Create, Retrieve, Update, and Delete).

### Software Programs Required
-Java Development Kit (OpenJDK)

-IntelliJ IDEA Ultimate

-MySQL Community Server

-MySQL Workbench

Technologies Used
-Spring Boot Web

-Spring Data JPA & Hibernate

-MySQL Database

-Thymeleaf

-Spring Data JPA Test

### Setting up

1.To run the "spring-boot-crud-intellij" project in IntelliJ IDEA, follow these steps:

Clone the Repository: If you haven't already cloned the repository, you can do so by using Git on the command line:
```git clone https://github.com/solojh/spring-boot-crud-intellij.git```

2.Open IntelliJ IDEA: Launch IntelliJ IDEA on your computer.

3.Open the Project: Click on File > Open and navigate to the directory where you cloned the "spring-boot-crud-intellij" repository. Select the root folder of the project and click OK.

4.Wait for IntelliJ to Import the Project: IntelliJ will start importing the project. Wait for the process to complete.

5.Configure MySQL Database: Make sure you have MySQL Community Server installed and running on your system. Configure the database connection properties in the application.properties file under the src/main/resources directory. You may need to modify the following properties:
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

6.Run the Application: Navigate to the Application.java file located in the src/main/java/com/example/demo directory. Right-click on the file and select Run Application or press Shift + F10.

7.Access the Application: Once the application is running, open a web browser and navigate to http://localhost:8080 to access the application.

