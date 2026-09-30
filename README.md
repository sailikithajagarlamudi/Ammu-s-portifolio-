# LibraNova — Library Management System

A modern full-stack Library Management System for academic submission.

## Stack

- Java 17
- Spring Boot
- Spring JDBC / JdbcTemplate
- MySQL
- HTML5
- CSS3
- JavaScript
- REST API

## Included features

- Modern responsive library-themed UI
- Dashboard statistics
- 12 sample books
- Book catalog cards
- Member selection
- Withdraw / borrow books
- Automatic 7-day due date
- Return books
- +10 credits for on-time return
- Borrowing history
- MySQL persistence
- Controller → Service → DAO → JDBC architecture

## Run

1. Install JDK 17+, MySQL 8+, Maven.
2. Run `database/library_management.sql` in MySQL Workbench.
3. Edit `src/main/resources/application.properties` and set your MySQL password.
4. Run:
   `mvn spring-boot:run`
5. Open:
   `http://localhost:8080`

## Architecture

Frontend (HTML/CSS/JS)
→ REST Controller
→ Service
→ DAO
→ JdbcTemplate/JDBC
→ MySQL

## Credit rule

- Return on or before due date: +10 credits
- Return after due date: 0 credits

This is an academic/demo project. For production deployment, add authentication, authorization, stronger validation, migrations, logging and tests.
