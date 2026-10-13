# quickbite
QuickBite - Food Ordering Application using Spring Boot, React and Oracle
# 🍔 QuickBite – Online Food Ordering System

QuickBite is a backend-focused online food ordering application developed using Java and Spring Boot. It provides RESTful APIs for user management, authentication, and order management, with secure access control using Spring Security and JWT authentication.

The project demonstrates practical implementation of REST API development, database integration, exception handling, input validation, pagination, sorting, and role-based authorization.

## 🚀 Features

* **User Management:** Create, retrieve, update, and delete user records.
* **JWT Authentication:** Secure authentication using JSON Web Tokens.
* **Spring Security:** Protect API endpoints and implement role-based access control.
* **Password Encryption:** Secure password storage using BCrypt.
* **Order Management:** Create and manage food orders.
* **Order Tracking:** Retrieve orders by user ID and order status.
* **Pagination and Sorting:** Retrieve paginated and sorted API results.
* **Input Validation:** Validate incoming request data.
* **Exception Handling:** Handle application errors using a global exception handler.
* **RESTful APIs:** Follow REST principles for client-server communication.
* **API Documentation:** Swagger/OpenAPI integration for exploring and testing endpoints.
* **Request Logging:** Log incoming HTTP requests for easier debugging.

## 🛠️ Tech Stack

| Technology        | Purpose                                    |
| ----------------- | ------------------------------------------ |
| Java 21           | Programming language                       |
| Spring Boot 3.5.5 | Backend application development            |
| Spring Web        | REST API development                       |
| Spring Security   | Authentication and authorization           |
| JWT               | Token-based authentication                 |
| Spring Data JPA   | Database access and repository management  |
| Hibernate         | ORM and entity management                  |
| Oracle Database   | Relational database                        |
| Maven             | Dependency management and build automation |
| Swagger / OpenAPI | API documentation                          |
| Postman           | API testing                                |
| Git & GitHub      | Version control and source code management |

## 🏗️ Application Architecture

QuickBite follows a layered architecture to separate responsibilities and make the application easier to maintain.

```text
Client (Postman)
       |
       v
REST Controllers
       |
       v
Service Layer
       |
       v
Repository Layer
       |
       v
Hibernate / JPA
       |
       v
Oracle Database
```

### Architecture Components

* **Controller Layer:** Handles HTTP requests and returns API responses.
* **Service Layer:** Contains business logic and application rules.
* **Repository Layer:** Performs database operations using Spring Data JPA.
* **Entity Layer:** Represents database entities.
* **DTO Layer:** Transfers request and response data between the client and application.
* **Security Layer:** Handles JWT authentication and authorization.
* **Exception Handling Layer:** Provides consistent handling of application errors.

## 🔐 Security Implementation

QuickBite implements authentication and authorization using Spring Security and JWT.

* Authenticates users through login credentials.
* Generates JWT tokens after successful authentication.
* Validates JWT tokens for protected requests.
* Uses BCrypt to hash passwords before storing them.
* Supports role-based access for regular users and administrators.
* Configures stateless authentication for API requests.
* Protects administrative endpoints against unauthorized access.

## 📌 API Endpoints

The following endpoints represent the main API areas of the application.

### Authentication APIs

| Method | Endpoint             | Description                          |
| ------ | -------------------- | ------------------------------------ |
| POST   | `/api/auth/register` | Register a new user                  |
| POST   | `/api/auth/login`    | Authenticate a user and obtain a JWT |

### User Management APIs

| Method | Endpoint          | Description           |
| ------ | ----------------- | --------------------- |
| GET    | `/api/users`      | Retrieve users        |
| GET    | `/api/users/{id}` | Retrieve a user by ID |
| PUT    | `/api/users/{id}` | Update user details   |
| DELETE | `/api/users/{id}` | Delete a user         |

### Order Management APIs

| Method | Endpoint                                    | Description                          |
| ------ | ------------------------------------------- | ------------------------------------ |
| POST   | `/api/orders`                               | Create an order                      |
| GET    | `/api/orders`                               | Retrieve orders                      |
| GET    | `/api/orders/{id}`                          | Retrieve an order by ID              |
| GET    | `/api/orders/user/{userId}`                 | Retrieve orders for a user           |
| GET    | `/api/orders/status/{status}`               | Retrieve orders by status            |
| GET    | `/api/orders/user/{userId}/status/{status}` | Retrieve a user's orders by status   |
| GET    | `/api/orders/page`                          | Retrieve paginated and sorted orders |

### Admin APIs

| Method | Endpoint                | Description                          |
| ------ | ----------------------- | ------------------------------------ |
| GET    | `/api/admin/dashboard`  | Access the admin dashboard           |
| GET    | `/api/admin/users`      | Retrieve users through the admin API |
| PUT    | `/api/admin/users/{id}` | Update a user through the admin API  |
| DELETE | `/api/admin/users/{id}` | Delete a user through the admin API  |

> **Note:** Endpoint availability and exact paths depend on the controller mappings in your source code. Check your controllers and security configuration before publishing this API reference.

## ⚙️ Getting Started

Follow these steps to run QuickBite locally.

### Prerequisites

Install the following software before starting:

* Java Development Kit (JDK) 21
* Maven
* Oracle Database
* Git
* Postman (for API testing)

### 1. Clone the Repository

```bash
git clone https://github.com/AnjaliVastrakar/quickbite.git
```

Navigate to the project directory:

```bash
cd quickbite
```

### 2. Configure the Database

Create or select an Oracle database user and configure the database connection in `src/main/resources/application.properties`.

Example configuration:

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8010
```

**Important:** Adjust the Oracle connection URL and credentials to match your local database configuration.

If your application uses additional properties for JWT secrets or other settings, configure those as well.

Do not commit real database passwords, JWT secrets, or other sensitive credentials to GitHub.

### 3. Configure Environment Variables

Configure the database credentials and JWT secret using environment variables supported by your application.

For example:

```text
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
JWT_SECRET=your_secure_jwt_secret
```

Make sure the property names match those referenced in your application's configuration files.

### 4. Build the Application

Run the following command from the project directory:

```bash
mvn clean install
```

### 5. Start the Application

Run QuickBite using Maven:

```bash
mvn spring-boot:run
```

Alternatively, run the generated JAR:

```bash
java -jar target/quickbite-app.jar
```

The exact JAR filename depends on your Maven build configuration.

The application is configured to run at:

```text
http://localhost:8010
```

### 6. Test the APIs

Use Postman to test the application's REST endpoints.

1. Start the QuickBite application.
2. Register a user using the registration endpoint.
3. Log in with valid credentials.
4. Copy the JWT token returned by the login API.
5. Open the **Authorization** tab in Postman.
6. Select **Bearer Token** and paste the token.
7. Send requests to protected endpoints.
8. Test user management, order management, pagination, sorting, and admin access.

For protected endpoints, ensure the authenticated user has the required role.

## 🧪 Example API Requests

### User Registration

**POST** `/api/auth/register`

Example request body:

```json
{
  "name": "Test User",
  "email": "testuser@example.com",
  "password": "******",
  "phone": "987654****"
}
```

Use the exact request fields and endpoint supported by your registration DTO and controller.

### User Login

**POST** `/api/auth/login`

Example request body:

```json
{
  "email": "testuser@example.com",
  "password": "******"
}
```

Use the response returned by your login endpoint to obtain the JWT token.

### Create an Order

**POST** `/api/orders`

Example request body:

```json
{
  "userId": 21,
  "totalAmount": 2500,
  "status": "PLACED"
}
```

Replace `userId` with an existing user ID in your database. Include the JWT token if the endpoint requires authentication.

### Pagination and Sorting

**GET** `/api/orders/page?page=0&size=5`

Example with sorting:

```text
/api/orders/page?page=0&size=5&sortBy=totalAmount&direction=asc
```

These query parameters are examples; use the exact parameter names supported by your controller.

## 📚 Key Concepts Demonstrated

This project helped me practice and apply the following backend development concepts:

* Object-Oriented Programming in Java
* Building RESTful web services with Spring Boot
* Dependency Injection
* Layered application architecture
* Spring Data JPA and Hibernate
* Relational database integration with Oracle
* DTOs and entity management
* JWT-based authentication
* Role-based authorization with Spring Security
* Password hashing using BCrypt
* Input validation and global exception handling
* Pagination and sorting
* API testing using Postman
* Maven-based project management
* Git and GitHub version control

## 🎯 Project Objectives

The primary objective of QuickBite is to build a secure and maintainable backend for an online food ordering system while gaining practical experience in Java backend development.

The project focuses on API development, database operations, authentication, authorization, error handling, and commonly used Spring Boot features.

## 🔮 Future Enhancements

Potential enhancements for the project include:

* Food menu and category management
* Restaurant management
* Shopping cart functionality
* Order history and order status updates
* Payment gateway integration
* Kafka-based asynchronous order processing
* Automated unit and integration testing
* Frontend integration
* Cloud deployment and monitoring

These are potential future improvements and should not be considered implemented features unless they are present in the codebase.

## 👩‍💻 Author

**Anjali Vastrakar**

* GitHub: [AnjaliVastrakar](https://github.com/AnjaliVastrakar)
* LinkedIn: [Anjali Vastrakar](https://www.linkedin.com/in/anjali-vastrakar/)

## ⭐ Support

If you find this project useful for learning Java backend development, feel free to explore the repository and share your feedback.

---
