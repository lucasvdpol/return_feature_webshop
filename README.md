Software Advanced (INSOFAD) Project

A full-stack monorepo application consisting of a Java Spring Boot backend and an Angular frontend, orchestrated via Docker.
Structure
    FFS_backend/: Spring Boot application.
    FFS_frontend/: Angular application.
    docker-compose.yml: Service orchestration.

Setup
Environment Variables
    Navigate to luxuryproducts_backend/.
    Create a file named .env.
    Add the following:
    JWT_SECRET=your_secret_key_here

Running with Docker

Ensure Docker Desktop is running. From the project root, run:

docker compose up --build

Ports
    Backend: http://localhost:8080/api
    Frontend: http://localhost:26338/

Admin account:
    Email: admin@gmail.com
    Password: Welkom01!
