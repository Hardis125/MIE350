# Group 13: Personal Project & Career Management System (Backend)

## 📌 Status Update
Hey Peter and Linh, 

To get us moving quickly for the Web Application Prototype deliverable, I repurposed the `cms` Spring Boot project from our MIE350 labs. I deleted all the original lab files (`Course`, `Student`, `Professor`, etc.) and set up our custom architecture. 

[cite_start]The codebase now contains the foundational Entities, DTOs with validation, and Exception Handlers to help us hit the "Exceeds Expectations" tier on the prototype grading rubric[cite: 750].

## 📂 Current Folder Structure
All of our active code is located inside `src/main/java/com/example/cms/`. Here is how it is organized:

* **`model/entity/`**: Contains our 4 core entities (`PortfolioUser`, `Project`, `Skill`, `JobApplication`) mapped with `@OneToMany` and `@ManyToMany` relationships.
* **`controller/dto/`**: Contains the Data Transfer Objects (`ProjectDto`, `JobApplicationDto`, etc.). [cite_start]These include `javax.validation` tags (like `@NotBlank` and `@NotNull`) to validate frontend inputs before they hit the database[cite: 750].
* [cite_start]**`controller/exceptions/`**: Contains `ResourceNotFoundException` and a `GlobalExceptionHandler` to catch bad requests and prevent server crashes[cite: 750].
* **`controller/`**: Contains the initial `ProjectController` with basic CRUD operations.
* **`model/repository/`**: *Currently empty. Needs to be populated by the Database Manager.*

---

## 🛠 Next Steps: Linh (Database Management)
[cite_start]Linh, since you are leading the database management, your goal is to connect our entities to the H2 database and populate it with test data.

- [ ] **Create the Repositories:** Inside `model/repository/`, create the interface files for our entities (e.g., `ProjectRepository.java`, `PortfolioUserRepository.java`) and have them extend `JpaRepository`.
- [ ] **Write Custom Queries:** If we need specific SQL filtering (like searching projects by skill), write the `@Query` annotations inside those repositories.
- [ ] **Initialize `data.sql`:** Open `src/main/resources/data.sql` and write the `INSERT INTO` statements to mock up our database. [cite_start]**Important:** The project specification requires us to have at least 50 total tuples (rows) across all our tables[cite: 668].
- [ ] [cite_start]**Test H2 Console:** Boot up the app and check `http://localhost:8085/h2-console` to ensure the tables are generating correctly[cite: 422].

## 🛠 Next Steps: Peter (System Architecture)
[cite_start]Peter, as my fellow System Architecture lead [cite: 729][cite_start], we need to finish building out the REST API endpoints so the frontend team (Daisy, Anna, Fiona) can connect their UI.

- [ ] **Complete the Controllers:** Build out the `PortfolioUserController`, `SkillController`, and `JobApplicationController` inside the `controller/` folder.
- [ ] **Build the Filtering Logic:** In `JobApplicationController`, write the logic that takes a list of `Project IDs` from the frontend and links those specific projects to a `JobApplication` entity.
- [ ] [cite_start]**Test with Insomnia:** Use the Insomnia REST client from Lab 2 to send `GET`, `POST`, `PUT`, and `DELETE` requests to ensure our DTO validations and Exception Handlers are working correctly[cite: 303, 305].

---

## 🚀 How to Run the Application
1. Open the project in IntelliJ IDEA.
2. Navigate to `src/main/java/com/example/cms/CmsApplication.java`.
3. [cite_start]Right-click the file and select **Run 'CmsApplication.main()'**[cite: 186].
4. [cite_start]Look for `APPLICATION IS RUNNING` in the terminal[cite: 243]. 

[cite_start]*Note: The H2 database resets every time the application is restarted[cite: 312]. Make sure to add any permanent test data to `data.sql`!*
