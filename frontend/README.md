# 🍔 QuickBite – Food Ordering Application

QuickBite is a full-stack food ordering application built using **React, Spring Boot, Spring Data JPA, Spring Security, JWT, and Oracle Database**.

The application allows customers to browse restaurants, view menus, add food items to a cart, place orders, make payments, track orders, and manage their order history. It also provides admin functionality for managing users, restaurants, menu items, orders, and payments.

---

## 🚀 Features

### 👤 User Features

* User registration and login
* JWT-based authentication
* Role-based authorization
* Browse restaurants
* Search restaurants
* View restaurant menu items
* Search menu items
* Add items to cart
* Update cart items
* Remove cart items
* View cart total
* Clear cart
* Place orders
* View order history
* View order details
* Track order status
* Cancel eligible orders
* Make payments
* View payment history

### 🛠️ Admin Features

* Admin authentication
* Admin dashboard
* View users
* Manage users
* Manage restaurants
* Manage menu items
* View all orders
* Update order status
* Delete orders
* View payments
* Manage application data

---

## 🏗️ Project Architecture

```text
QuickBite
│
├── Backend
│   ├── Controller
│   ├── Service
│   ├── Repository
│   ├── Entity
│   ├── DTO
│   ├── Security
│   └── Exception
│
└── Frontend
    ├── Components
    ├── Pages
    ├── Services
    └── CSS
```

---

## 💻 Technologies Used

### Backend

* Java 21
* Spring Boot 3.5.5
* Spring MVC
* Spring Data JPA
* Hibernate
* Spring Security
* JWT Authentication
* BCrypt Password Encoding
* Maven
* Oracle Database
* REST APIs

### Frontend

* React
* Vite
* JavaScript
* HTML5
* CSS3

### Development Tools

* Eclipse
* Visual Studio Code
* Postman
* Git
* GitHub
* Oracle Database

---

## 🔐 Security

QuickBite uses **Spring Security and JWT** for authentication and authorization.

The authentication flow is:

```text
User Login
    ↓
Spring Boot Authentication
    ↓
JWT Token Generated
    ↓
Token Stored by Frontend
    ↓
Token Sent with API Requests
    ↓
JWT Authentication Filter
    ↓
User Authentication
    ↓
Role-Based Authorization
    ↓
API Response
```

Passwords are encrypted using **BCrypt** before being stored in the database.

Sensitive values such as the database password and JWT secret are supplied through environment variables rather than being stored directly in the source code.

---

## 🗄️ Database

QuickBite uses **Oracle Database** as the relational database.

Major entities include:

* User
* Restaurant
* MenuItem
* Food
* Cart
* Order
* OrderItem
* Payment
* Address
* Review
* Category

Spring Data JPA and Hibernate are used for database operations and object-relational mapping.

---

## 🔄 Customer Order Flow

```text
Login
  ↓
Restaurants
  ↓
Select Restaurant
  ↓
View Menu
  ↓
Add Food to Cart
  ↓
View Cart
  ↓
Place Order
  ↓
My Orders
  ↓
Make Payment
  ↓
Payment History
  ↓
Order Details
  ↓
Track Order
```

---

## 📡 REST API

The backend exposes REST APIs for:

```text
/api/auth
/api/users
/api/restaurants
/api/menu-items
/api/cart
/api/orders
/api/payments
/api/admin
```

The backend runs locally on:

```text
http://localhost:8089
```

The React frontend runs locally on:

```text
http://localhost:5173
```

---

## ⚙️ Backend Setup

### 1. Clone the Repository

```bash
git clone https://github.com/AnjaliVastrakar/quickbite.git
```

### 2. Open the Backend

Open the project in Eclipse or another Java IDE.

### 3. Configure Oracle Database

Create/configure your Oracle database and update the required environment variables.

The application uses:

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=SYSTEM
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}
```

Set the following environment variables:

```text
DB_PASSWORD=your_oracle_password
JWT_SECRET=your_secure_jwt_secret
```

**Do not commit real passwords or secret keys to GitHub.**

### 4. Run the Spring Boot Application

Run:

```text
QuickbiteApplication.java
```

The backend will start on:

```text
http://localhost:8089
```

---

## 🎨 Frontend Setup

Navigate to the React frontend directory.

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

The frontend will normally be available at:

```text
http://localhost:5173
```

---

## 🧪 API Testing

REST APIs can be tested using **Postman**.

Example authentication request:

```http
POST /api/auth/login
Content-Type: application/json
```

Example request body:

```json
{
  "email": "your-email@example.com",
  "password": "your-password"
}
```

After successful login, use the returned JWT token for protected APIs:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

## 📂 Backend Package Structure

```text
com.quickbite
│
├── Controller
│   ├── AdminController
│   ├── AuthController
│   ├── CartController
│   ├── FoodController
│   ├── MenuItemController
│   ├── OrderController
│   ├── PaymentController
│   ├── RestaurantController
│   ├── ReviewController
│   └── UserController
│
├── DTO
│
├── Entity
│
├── Exception
│
├── Repository
│
├── Security
│
└── Service
```

---

## 🔑 Key Concepts Implemented

This project demonstrates practical usage of:

* Object-Oriented Programming
* Java Collections
* Java Streams
* Exception Handling
* REST API development
* Dependency Injection
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate ORM
* CRUD operations
* DTO and Entity separation
* Bean Validation
* Global Exception Handling
* Spring Security
* JWT Authentication
* BCrypt Password Encryption
* Role-Based Authorization
* Pagination
* Sorting
* Searching
* Filtering
* Oracle Database Integration
* React
* REST API integration
* Git and GitHub

---

## 📸 Application

QuickBite provides a responsive user interface for:

* Home
* Restaurant browsing
* Menu browsing
* Cart
* Orders
* Payments
* Order tracking
* Admin order management

---

## 🔮 Future Enhancements

Possible future improvements include:

* Online food delivery integration
* Real payment gateway integration
* Email notifications
* SMS notifications
* Restaurant owner dashboard
* Product ratings and reviews
* Advanced order tracking
* Docker deployment
* Cloud deployment
* Microservices architecture
* Kafka-based event processing

---

## 👩‍💻 Author

**Anjali Vastrakar**

Java Backend / Full Stack Developer

GitHub:
https://github.com/AnjaliVastrakar

---

## 📄 License

This project is created for learning, development, and interview preparation purposes.
