# Project Explanation for Viva / Interview

## Architecture

Frontend
  -> Controller
  -> Service
  -> DAO
  -> JDBC
  -> MySQL

## Why JDBC?

JDBC is used to connect Java with MySQL and execute SQL statements. This project uses Spring's JdbcTemplate, which is built on JDBC.

## Controller

`LibraryController.java` exposes REST endpoints to the frontend.

## Service

`LibraryService.java` contains business rules:
- a book must be available before withdrawal
- borrowing period is 7 days
- on-time return earns 10 credits
- late return earns 0 credits

## DAO

DAO classes use JdbcTemplate to run parameterized SQL queries against MySQL.

## Tables

members:
Stores library members and their current credits.

books:
Stores book details and availability.

borrow_records:
Stores withdrawal date, due date, return date, status and credits earned.

## Example flow

1. User selects a member and book.
2. JavaScript sends POST `/api/borrow`.
3. Controller receives the request.
4. Service validates member/book.
5. DAO inserts a borrow record using JDBC.
6. DAO marks the book unavailable.
7. Due date is set to 7 days after withdrawal.
8. On return, the service compares return date and due date.
9. If on time, 10 credits are added to the member.
10. Book becomes available again.
