# Student Management System

A Spring Boot web application to manage student records, enter marks, and generate performance reports.

## Features
- Add, view, update, delete students (CRUD)
- Search students by name
- Marks entry per subject
- Report with total, average and grade (printable)

## Tech Stack
Java 17, Spring Boot 3, Spring Data JPA (Hibernate), MySQL, Thymeleaf

## Setup
1. Install JDK 17+, Maven, MySQL
2. Set your MySQL password in `src/main/resources/application.properties`
3. Run: `mvn spring-boot:run`
4. Open http://localhost:8080

The database `student_db` and tables are created automatically.

## Grading
A+ (90+), A (80+), B (70+), C (60+), D (40+), F (below 40)

## Author
YOUR NAME
