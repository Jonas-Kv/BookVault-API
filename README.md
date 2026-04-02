# BookVault API

A robust, containerized RESTful API for library management. This project demonstrates the implementation of modern web security, database persistence, and professional deployment workflows using the Spring Boot ecosystem.

---

## Overview
**BookVault** enables users to manage book inventories with a clear separation of concerns. It features a strict role-based access model (User/Admin) and stateless authentication via JWT. The entire infrastructure is pre-configured for immediate deployment using Docker.

### Key Features
* **Full Auth System:** Registration and Login powered by Spring Security.
* **Stateless Security:** JWT-based authentication for scalable API access.
* **Data Persistence:** Seamless integration with a PostgreSQL database.
* **Containerization:** Single-command environment setup with Docker Compose.

---

## Tech Stack
* **Language:** Java 21
* **Framework:** Spring Boot 4.0.5 (Data JPA, Security, Web)
* **Database:** PostgreSQL 15+ (latest)
* **Security:** JSON Web Token (JWT), BCrypt Password Encoding
* **Infrastructure:** Docker & Docker Compose
* **Build Tool:** Maven

---

## Installation & Setup

### Prerequisites
* Docker Desktop installed
* Git installed

### Quick Start
1.  **Clone the repository:**
    ```bash
    git clone https://github.com/Jonas-Kv/BookVault-API
    ```
2.  **Configure Environment Variables:**
    Create a `.env` file in the root directory
    
    * **Database Credentials:** Choose your own `DB_USERNAME` and `DB_PASSWORD`.
    * **JWT Secret Key:** You need a secure 256-bit encryption key. You can generate a secure random hex string online (e.g., https://generate-random.org/encryption-keys)

    **Example `.env` content:**
    ```env
    DB_USERNAME=your_custom_admin
    DB_PASSWORD=your_custom_password
    SECRET_KEY=your_generated_64_character_hex_key
    ```
    
3.  **Spin up the Containers:**
    ```bash
    docker-compose up --build
    ```
The API will be accessible at `http://localhost:8080`. The database runs internally on port `5432` (mapped to `5331` externally for local inspection).

---

## 🔑 Authentication & Usage

Since this API is secured with **Spring Security and JWT**, you must provide a valid token for most library operations. You can obtain a token in two ways:

1.  **Register:** Create a new account via `POST /api/v1/auth/register`. You will receive a JWT **immediately** upon successful registration.
2.  **Login:** If you already have an account, send a `POST` request to `/api/v1/auth/authenticate` to receive a new token.

**How to Authorize Requests:**
For all subsequent requests to **Authors** or **Books**, include the token in the request header:
* **Key:** `Authorization`
* **Value:** `Bearer <YOUR_JWT_TOKEN>`

> **Tip:** You can use the provided `request.http` file in the root directory to test all endpoints easily within your IDE.

---

## 📖 API Endpoints (Selection)

### Authentication
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/auth/register` | Register a new account |
| `POST` | `/api/v1/auth/authenticate` | Login and receive a JWT |

### Authors
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/authors` | Get all authors (without their books) |
| `GET` | `/api/v1/authors/{id}` | Get a single author (without books) |
| `GET` | `/api/v1/authors/books` | Get all authors including their books |
| `GET` | `/api/v1/authors/{id}/books` | Get a specific author with all their books |
| `POST` | `/api/v1/authors` | Create a new author |
| `PATCH` | `/api/v1/authors/{id}` | Update an existing author's name |
| `DELETE` | `/api/v1/authors/{id}` | Delete an author and all their associated books |

### Books
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/books` | Get all books with full details |
| `GET` | `/api/v1/books/all` | Get a list of all available book titles |
| `GET` | `/api/v1/books/{id}` | Get a single book by its ID |
| `GET` | `/api/v1/books/findISBN?title=...` | Find the ISBN of a book by its title |
| `POST` | `/api/v1/books` | Create a new book and link it to an author |
| `DELETE` | `/api/v1/books/{id}` | Delete a specific book (author remains) |

---

## Roadmap & Future Improvements

### Phase 1: Quality & Documentation (In Progress)
* [ ] **Swagger/OpenAPI:** Integrate `springdoc-openapi` for interactive API documentation.
* [ ] **Unit Testing:** Reach >80% code coverage using JUnit 5 and Mockito.
* [ ] **Validation:** Implement strict input validation using `spring-boot-starter-validation`.

### Phase 2: Cloud & DevOps
* [ ] **CI/CD:** Automated build and test pipelines via GitHub Actions.
* [ ] **Testcontainers:** Migration of integration tests to real, ephemeral Docker instances.
* [ ] **Monitoring:** Health checks and metrics via Spring Boot Actuator & Prometheus.

### Phase 3: Frontend Integration
* [ ] **Dashboard:** Create a visual overview of library statistics (total books, authors, etc.).
* [ ] **Single Page Application (SPA):** Build a modern web interface using React or Angular.
* [ ] **State Management:** Implement secure storage for JWTs (e.g., HttpOnly Cookies or LocalStorage).


---

## 👤 Contact
Jonas Kviring – https://www.linkedin.com/in/jonas-kviring/  
Project Link: [https://github.com/[YOUR_USERNAME]/bookvault-api](https://github.com/[YOUR_USERNAME]/bookvault-api)
