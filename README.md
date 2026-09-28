# Time Deposit Refactoring Kata - Take-Home Assignment

## XA Bank Time Deposit

### Context
A junior developer implemented domain logic for a time deposit system but did not complete the API functionality. Your task is to refactor the existing codebase to implement all required functionalities based on the provided business requirements, ensuring no breaking changes occur.

### Requirements

1. **API Endpoints**:
    - Create a RESTful API endpoint to update the balances of all time deposits in the database.
    - Create a RESTful API endpoint to retrieve all time deposits.
        - The GET endpoint should return a list of all time deposits with the following schema:
            - `id`
            - `planType`
            - `balance`
            - `days`
            - `withdrawals`

2. **Database Setup**:
    - Store all time deposit plans in a database.
    - Define the following tables:
        - `timeDeposits`:
            - `id`: Integer (primary key)
            - `planType`: String (required)
            - `days`: Integer (required)
            - `balance`: Decimal (required)
        - `withdrawals`:
            - `id`: Integer (primary key)
            - `timeDepositId`: Integer (foreign key, required)
            - `amount`: Decimal (required)
            - `date`: Date (required)

3. **Interest Calculation**:
    - Implement logic to calculate monthly interest based on the plan type:
        - **Basic Plan**: 1% interest
        - **Student Plan**: 3% interest (no interest after 1 year)
        - **Premium Plan**: 5% interest (interest starts after 45 days)
    - No interest is applied for the first 30 days for any existing plans.

4. **Refactoring Constraints**:
    - Do not introduce breaking changes to the shared `TimeDeposit` class or modify the `updateBalance` method signature.
    - Ensure the design is extensible to accommodate future complexities in interest calculations.

5. **Code Quality**:
    - Adhere to SOLID principles, design patterns, and clean code practices where applicable.

6. **AI-Assisted Development**:
    - Set up an AI harness or agent workflow and use it throughout the development for this take-home exercise.
    - Briefly document the tools and setup used (e.g., LLMs, coding assistants, agentic frameworks, configuration).
    - Ensure your AI setup is practical and reproducible.
    - Include any custom rules, system prompts, or agent configurations used.
    - Include a brief summary of which parts of the solution were AI-assisted and why.

### Important Guidelines
- The existing `TimeDepositCalculator.updateBalance` method is functioning correctly. Ensure its behavior remains unchanged after refactoring.
- The final solution must include **exactly two API endpoints**. Do not develop additional endpoints.
- **Do not** create a pull request or a new branch in the ikigai-digital repository. Instead, fork the repository into your own GitHub repository and develop the solution there.
- Handling invalid input or exceptions is not required.
- Use any tools, frameworks, or libraries you find suitable.
- In case of ambiguity, make logical assumptions and justify them in code comments.

### Preferred Stack
- Use an OpenAPI Swagger contract.
- Embrace Hexagonal Architecture.
- Follow atomic commit practices.
- Utilize testcontainers.
- Leverage AI-assisted development tools for code generation, testing, and refactoring.

### Submission Instructions
- Provide clear instructions on how to trigger the endpoints using the Swagger contract.
- Email the link to your public GitHub repository.

---

## Solution Guide & AI Documentation

### 1. How to Run the Application & Trigger Endpoints via Swagger

#### Prerequisites
- Java 17+ (or Docker / Podman)
- Maven 3.9+ (or use Docker Compose)

#### Option A: Running with Docker Compose (Recommended)
From the repository root (`time-deposit/docker`):
```bash
docker compose -f docker/docker-compose.yml up --build -d
```
This spins up PostgreSQL 17 on port `5433` and the Spring Boot application on port `8080`.

#### Option B: Running Locally with Maven
1. Start PostgreSQL:
   ```bash
   docker compose -f docker/docker-compose.yml up -d postgres
   ```
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

#### Option C: Running Integration Tests (Testcontainers)
All business flows and database round-trips can be verified independently without manual container setup:
```bash
mvn clean test
```

#### Triggering Endpoints via Swagger UI
Once the application is running, navigate to:
**[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)** (or `http://localhost:8080/swagger-ui.html`)

1. **`GET /api/v1/time-deposits`**:
   - Expand the endpoint in Swagger UI.
   - Click **Try it out** and then **Execute**.
   - Inspect the 200 OK response returning all deposits with plan types, balances, days, and nested historical withdrawals.
2. **`POST /api/v1/time-deposits/update-balances`**:
   - Expand the endpoint in Swagger UI.
   - Click **Try it out** and then **Execute**.
   - Receives `"Balances updated"` status 200 OK.
   - Re-run `GET /api/v1/time-deposits` to observe the interest applied according to plan business rules.

---

### 2. AI-Assisted Development Report (Section 6)

#### Tools & Setup
- **Coding Assistant / Framework:** Google DeepMind **Antigravity CLI** agentic pair programming harness running Gemini reasoning models.
- **Workflow:** Conversational pair programming combining automated command execution (Maven builds, container orchestration, code edits) with iterative human guidance and reviews.

#### Custom Rules & System Configuration (`GEMINI.md`)
- **Strict conciseness:** Keeping explanations short and focused to maintain high engineering velocity.
- **Strict Hexagonal Architecture:** Separation of domain models and logic from inbound/outbound ports, application use cases, and persistence/web adapters.
- **Pure Dependency Injection:** No unnecessary `new` operators in Spring service components; constructor injection used exclusively.
