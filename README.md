# Employee Management System (EMS)

A simple backend project for managing employees using Java and Spring Boot. This project follows RESTful API principles and includes features like CRUD operations, pagination, validation, and exception handling.

## 🚀 Features

- Add, update, delete, and fetch employee details
- RESTful API structure with proper HTTP status codes
- Pagination and sorting support
- Input validation using `javax.validation`
- Exception handling with custom exceptions
- DTO mapping using ModelMapper
- Uses Spring Data JPA for database interactions


## 🛠️ Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Validation
- ModelMapper
- H2 / MySQL (Configurable)
- Maven / Gradle
- Postman / Swagger for testing

## 📁 Project Structure

src
├── main
│ ├── java
│ │ └── com.example.ems
│ │ ├── controller
│ │ ├── dto
│ │ ├── exception
│ │ ├── model
│ │ ├── repository
│ │ ├── service


## 📦 API Endpoints

| Method | Endpoint               | Description            |
|--------|------------------------|------------------------|
| GET    | `/api/employees`       | Get all employees      |
| GET    | `/api/employees/{id}`  | Get employee by ID     |
| POST   | `/api/employees`       | Create a new employee  |
| PUT    | `/api/employees/{id}`  | Update an employee     |
| DELETE | `/api/employees/{id}`  | Delete an employee     |


## ⚙️ Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/shivakumar-m17/ems-rest-api.git
