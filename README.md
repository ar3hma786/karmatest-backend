# Admin Console Backend for Managing Sales
This repository provides the backend services for managing sales in an admin console application using Spring Boot. It handles data persistence, business logic, and API endpoints for client-side interactions.

## Prerequisites
Before you begin, ensure you have met the following requirements:

Java Development Kit (JDK): Make sure JDK 11 or higher is installed on your machine.
Maven: Ensure Apache Maven is installed for building the project.
Database: Set up your preferred database (e.g., MySQL, PostgreSQL) and update the configuration in the application.properties file.
Getting Started
To set up the project locally, follow these steps:

1. **Clone the repository:**

Open your terminal and run the following command:

```bash
git clone https://github.com/ar3hma786/karmatest-backend.git
```

2. **Navigate to the project directory:**

```bash
cd your-repo-name
```

3. **Configure the application properties:**

Open the src/main/resources/application.properties file and update the database connection details:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password


spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
```

4. **Build the project:**

Use Maven to build the project:

```bash
mvn clean install
```

5. **Run the application:**

Start the Spring Boot application using Maven:

```bash
mvn spring-boot:run
```

Alternatively, you can run the generated JAR file:

```bash
java -jar target/your-app-name.jar
```

5. **Test the API:**

The application will be running at http://localhost:8080. You can test the API using tools like Postman or cURL.
