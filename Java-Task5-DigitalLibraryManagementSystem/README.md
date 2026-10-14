# Lumina — Digital Library Management System
**Oasis Infobyte | Java Development | Task 5**

A responsive web-based library management application built with Java 17, Spring Boot, Thymeleaf, Spring Data JPA and SQLite.

## Features
- Dashboard with catalogue, members, active loans and overdue metrics
- Demo Admin and Reader login
- Searchable book catalogue and category filter
- Add, edit and delete book records
- Register and view members
- Issue and return books; available stock updates automatically
- 14-day loan period and overdue fine calculation at ₹5/day
- Reserve titles and store reader queries in the database
- Responsive dark-navy / blue / mint UI

## Run
1. Install JDK 17+ and Maven 3.9+.
2. Open this folder in IntelliJ IDEA and wait for Maven import.
3. Run `src/main/java/com/oasis/library/DigitalLibraryApplication.java`.
4. Visit `http://localhost:8080`.

Or run `mvn spring-boot:run` from this directory.

## Demo accounts
- Admin: `admin@lumina.local` / `Admin@123`
- Reader: `reader@lumina.local` / `Reader@123`

This is an educational demo. Before production use, add robust authorization, password hashing, CSRF protection and stronger validation. Database `lumina-library.db` is created locally.

## Suggested screenshots
Dashboard, catalogue search, add-book form, members, issue/return, reservations, and reader queries.
