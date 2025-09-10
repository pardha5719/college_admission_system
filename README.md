# College Admission Management System

A full-stack web application built with **Spring Boot**, **Thymeleaf**, and **MySQL** to streamline college admissions. Designed with a spacious, elegant admin dashboard and semantic HTML/CSS for professional presentation.

---

## Overview

This system allows administrators to:
- Register and manage student applications
- Assign courses and track admission status
- Generate print/PDF-ready reports
- Navigate a clean, centered UI with color-coded headings

---

## Tech Stack

| Layer      | Technology                     |
|------------|--------------------------------|
| Frontend   | HTML, CSS, Thymeleaf           |
| Backend    | Java, Spring Boot, Spring MVC  |
| Database   | MySQL, Hibernate ORM           |
| Tools      | Git, Spring Tool Suite (STS)   |
| Deployment | Localhost (Tomcat), GitHub     |

---

## Project Structure
College-admission/
├── src/main/java/com/example/demo/
│   ├── config/        → Configuration files
│   ├── controller/    → Handles HTTP requests
│   ├── model/         → Entity classes (Student, Course, etc.)
│   ├── repository/    → Interfaces for database access
│   └── service/       → Business logic
│
├── src/main/resources/
│   ├── static/        → CSS
│   ├── templates/     → Thymeleaf HTML files
│   └── application.properties → App configuration
│
├── src/test/java/     → Test cases
├── pom.xml            → Maven dependencies
└── README.md          → Project documentation



---

## Getting Started

1 Clone the Repository
git clone https://github.com/partha973/college_admission_system.git
cd college_admission_system

2 Configure MySQL
- Create a database named college_admission_db
- Update application.properties with your DB credentials

3️ Run the Application
- Open in Spring Tool Suite
- Run CollegeAdmissionSystemApplication.java

4 Access the App
http://localhost:8080/








