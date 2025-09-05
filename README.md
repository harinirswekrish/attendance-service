# Attendance Service – Mini School ERP  

## 📌 Overview  
The **Attendance Service** is a microservice in the **Mini School ERP System** responsible for managing attendance records of students for specific courses on specific dates.  

It ensures proper validation:
- A student must be **enrolled in a course** before attendance can be marked.  
- Once attendance is marked for `(studentId, courseId, date)`, it **cannot be modified or overwritten**.  

This service communicates securely using **JWT authentication** (validated via Redis) and relies on data from **Student Service** and **Enrollment Service**.  

---

## 🚀 Features  
- **Mark attendance** for a student in a course on a specific date (Staff only).  
- **Get attendance records** for all students of a specific course on a specific date (Admin & Staff).  
- **Validation & Error Handling** with clear response codes.  
- **Immutable attendance** (no updates once recorded).  

---

## 🛠️ Tech Stack  
- **Java 17** (Java 8+ supported)  
- **Spring Boot 3.x**  
- **Spring Data JPA (Hibernate)**  
- **PostgreSQL**  
- **Redis (for JWT blacklisting/session mgmt)**  
- **Spring Security with JWT**  
- **Swagger/OpenAPI**  
- **Maven**  

---

## 🗄️ Database Schema
**Table: attendances**

| Column                                            | Type                      | Constraints                                |
| ------------------------------------------------- | ------------------------- | ------------------------------------------ |
| id                                                | BIGSERIAL PK              | Primary Key                                |
| student\_id                                       | BIGINT FK                 | REFERENCES students(id)                    |
| course\_id                                        | BIGINT FK                 | REFERENCES courses(id)                     |
| attendance\_date                                  | DATE                      | NOT NULL                                   |
| status                                            | VARCHAR(20)               | NOT NULL (`PRESENT` / `ABSENT`)            |
| marked\_by                                        | BIGINT FK                 | REFERENCES users(id) → staff who marked it |
| marked\_at                                        | TIMESTAMP                 | DEFAULT now()                              |
| UNIQUE(student\_id, course\_id, attendance\_date) | Prevent duplicate marking |                                            |

## 🚀 Setup & Run

### Prerequisites
- JDK 8+  
- Maven 3+  
- PostgreSQL  

### Steps
```bash
# Clone repository
git clone https://github.com/harinirswekrish/attendance-service.git
cd course-service

# Update application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=yourpassword

jwt.secret=yourSecretKey
jwt.expiration=3600000
