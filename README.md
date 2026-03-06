# [cite_start]MIE350 Group 13: Personal Project & Career Management System [cite: 681, 729]

[cite_start]Welcome to the backend repository for the Group 13 term project[cite: 681, 729]! 

**IMPORTANT NOTE:** We are no longer using the old `cms` lab files. This is a brand-new, clean Spring Boot project generated via Spring Initializr to ensure our architecture is organized and free of legacy lab code.

## 🚀 Quick Start / Setup

1. Clone this repository to your local machine.
2. Open the folder in IntelliJ IDEA.
3. Wait for Maven to download the dependencies (Spring Web, Spring Data JPA, H2 Database, Lombok, and Validation).
4. Run the main application file (`CareerManagementApplication.main()`).
5. The REST API will be available at `http://localhost:8080`.
6. [cite_start]The H2 Database console will be available at `http://localhost:8080/h2-console`[cite: 422].

## 🏗️ Folder Structure & Architecture

All backend code is located in `src/main/java/com/group13/careermanagement`. 

* `controller/`: Contains the REST API endpoints.
* `controller/dto/`: Data Transfer Objects packed with `@Valid` annotations to catch bad frontend inputs (Targeting the "Exceeds Expectations" rubric grade!).
* `controller/exceptions/`: Global exception handlers to prevent server crashes.
* `model/entity/`: The 4 core database tables.
* `model/repository/`: Spring Data JPA interfaces for database interaction.

## ✅ What's Completed (Current Status)

[cite_start]The core architecture is in place to support our main workflow (organizing career assets and matching them to job applications)[cite: 690]. 
* **Entities Built:** `PortfolioUser`, `Project`, `Skill`, and `JobApplication`.
* **Relationships mapped:** One-to-Many (User -> Projects) and Many-to-Many (Projects <-> Skills, Projects <-> Applications).
* **DTOs & Validation:** DTOs are built to handle foreign keys securely and validate incoming data (e.g., ensuring titles aren't empty).
* **Base Controllers:** REST controllers are set up for basic CRUD operations.

## 🤝 Team Handoffs & Next Steps

### [cite_start]@Linh (Database Management) 
The entity classes (`@Entity`) are ready to go. To get the H2 database fully operational for the prototype, we need your help with:
1. **`application.properties`**: We need to configure the H2 console connection strings here.
2. **`data.sql`**: We need to write the mock SQL `INSERT` statements to populate the database on startup. [cite_start]Keep in mind the project rubric requires at least 50 tuples (rows) across all our tables[cite: 668]!

### [cite_start]@Peter (System Architecture & PM) 
The base API layer is up. [cite_start]Before we integrate with Daisy, Anna, and Fiona on the frontend, we need to collaborate on:
1. **The Filtering Engine**: Reviewing the `PUT` logic in `JobApplicationController` that links selected projects to a specific application.
2. [cite_start]**Public Portfolio View**: We need to design the read-only endpoint (`GET /portfolio/{applicationId}`) that restricts what external viewers (hiring managers) can see[cite: 736]. 

---
*If you run into any "Lombok" or "Persistence" symbol errors in IntelliJ, right-click the `pom.xml` file and select **Maven > Reload project**!*
