# Payment Processing Microservices Platform

## 📌 Project Purpose
This project is a backend application designed to simulate a secure payment processing platform. It demonstrates a modern microservices architecture by separating concerns into distinct, independently deployable services. The platform handles user authentication, payment initiation, webhook integration from payment providers, and transaction tracking.

This project was built to showcase backend development and DevOps skills, including REST API design, security implementation, database isolation, and containerization.

## 🏗 Architecture Overview
The system is built using a **Monorepo** structure containing two primary Spring Boot microservices. To enforce data isolation, each service manages its own dedicated PostgreSQL database via Docker.

1. **Service A (Auth Service):**
   * Acts as the identity provider for the platform.
   * Handles secure user registration (hashing passwords using BCrypt).
   * Manages user login and issues stateless JSON Web Tokens (JWT) for secure cross-service communication.
   * *Runs on port: `8081`*

2. **Service B (Payment Service):**
   * Manages the core business logic for transactions.
   * Initiates payments and generates unique reference IDs.
   * Tracks payment statuses (INITIATED, PENDING, SUCCESS, FAILED).
   * Exposes secure webhook endpoints to receive simulated updates from third-party payment providers, utilizing idempotency keys to prevent duplicate processing.
   * *Runs on port: `8082`*

## 🛠 Technologies & Tools
* **Language:** Java (17/21)
* **Framework:** Spring Boot (Spring Web, Spring Data JPA, Spring Security)
* **Database:** PostgreSQL (2 separate instances)
* **Security:** JSON Web Tokens (JJWT), BCrypt
* **Infrastructure & DevOps:** Docker, Docker Compose
* **Build Tool:** Gradle (Kotlin DSL) / Maven

## 🚀 Getting Started

### Prerequisites
To run this project locally, ensure you have the following installed:
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) (Running with Linux containers)
* Java Development Kit (JDK 17 or 21)
* Git

### Local Setup Instructions

**1. Clone the repository**
```bash
git clone [https://github.com/YOUR_GITHUB_USERNAME/YOUR_REPO_NAME.git](https://github.com/leomuko/Spring-interview-test.git)
cd main

# Navigate to the Auth Service and start its database
cd auth-service
docker-compose up -d

# Navigate to the Payment Service and start its database
cd ../payment-service
docker-compose up -d

Method,Endpoint,Description,Auth Required
POST,/api/auth/register,"Registers a new user with phone, email, and password.",
POST,/api/auth/login,"Authenticates a user and returns a JWT.",
