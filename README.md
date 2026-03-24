# 📚 BookStore Backend Application

A backend-only BookStore application built using Spring Boot and JPA. This project provides REST APIs to manage books, users, and orders for an online bookstore system.

---

## 🚀 Features

- User Registration & Authentication APIs
- Book Management (Add, Update, Delete, Fetch)
- Search Books API
- Order Management APIs
- RESTful API Design

---

## 🏗️ Tech Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Hibernate

### Database
- MySQL / Oracle

### Tools
- Maven
- Git & GitHub

---

## 📂 Project Structure

BookStore/  
│── src/  
│   ├── main/  
│   │   ├── java/        # Controllers, Services, Repositories, Entities  
│   │   ├── resources/   # application.properties  
│  
│── pom.xml  
│── README.md

---

## ⚙️ Setup & Installation

### 1. Clone the Repository

git clone https://github.com/sagarchavan07/BookStore.git  
cd BookStore

---

### 2. Configure Database

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/bookstore  
spring.datasource.username=root  
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true

---

### 3. Build the Project

mvn clean install

---

### 4. Run the Application

mvn spring-boot:run

---

### 5. Access APIs

http://localhost:8080/

---

## 🔗 Sample API Endpoints

- GET /books → Get all books
- GET /books/{id} → Get book by ID
- POST /books → Add new book
- PUT /books/{id} → Update book
- DELETE /books/{id} → Delete book

---

## 🧪 Future Enhancements

- Spring Security (JWT Authentication)
- Swagger API Documentation
- Pagination & Sorting
- Global Exception Handling
- Docker Deployment

---

## ⚠️ Known Issues

- Basic validation
- No authentication/authorization (if not implemented)

---

## 🤝 Contributing

1. Fork the repository
2. Create a new branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

---

## 📄 License

This project is for learning and demonstration purposes.

---

## 👨‍💻 Author

Sagar Chavan  
GitHub: https://github.com/sagarchavan07

---

## ⭐ Support

If you like this project, give it a star on GitHub!
