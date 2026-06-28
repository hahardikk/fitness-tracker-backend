# 🏋️ Fitness Tracker Backend

A RESTful Fitness Tracker API built with Spring Boot. 
It provides secure authentication, activity tracking, dashboard analytics, and AI-powered fitness recommendations.

## 🚀 Live API

Backend:
https://fitness-tracker-backend-klk2.onrender.com

Frontend:
https://fitness-tracker-ui.onrender.com

API Documentation (Swagger):
https://fitness-tracker-backend-klk2.onrender.com/swagger-ui/index.html

---

## ✨ Features

- JWT Authentication
- User Registration & Login
- CRUD Operations for Activities
- Dashboard Statistics
- Activity Distribution
- AI Recommendation Generation
- PostgreSQL Database
- Docker Support
- Global Exception Handling
- Validation
- RESTful APIs

---

## 🛠 Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Docker
- JWT
- Lombok

---

## 📂 Project Structure

```
src/
├── controller/
├── service/
├── repository/
├── entity/
├── dto/
├── config/
├── security/
├── exception/
└── util/
```

---

## ⚙️ Environment Variables

Configure the following variables:

```properties
SPRING_DATASOURCE_URL=YOUR_DATABASE_URL

SPRING_DATASOURCE_USERNAME=YOUR_DATABASE_USERNAME

SPRING_DATASOURCE_PASSWORD=YOUR_DATABASE_PASSWORD

JWT_SECRET=YOUR_JWT_SECRET

GROQ_API_KEY=YOUR_GROQ_API_KEY
```

---

## 💻 Installation

Clone repository

```bash
git clone https://github.com/hahardikk/fitness-tracker-backend.git
```

Move into project

```bash
cd fitness-tracker-backend
```

Run application

```bash
./mvnw spring-boot:run
```

or

```bash
mvn spring-boot:run
```

---

## 🐳 Docker

Build Image

```bash
docker build -t fitness-tracker-backend .
```

Run Container

```bash
## 🐳 Docker

### Build Image

```bash
docker build -t fitness-tracker-backend .
```

### Run Container

```bash
docker run -d \
-p 8080:8080 \
-e SPRING_DATASOURCE_URL=YOUR_DATABASE_URL \
-e SPRING_DATASOURCE_USERNAME=YOUR_DATABASE_USERNAME \
-e SPRING_DATASOURCE_PASSWORD=YOUR_DATABASE_PASSWORD \
-e JWT_SECRET=YOUR_JWT_SECRET \
-e GROQ_API_KEY=YOUR_GROQ_API_KEY \
--name fitness-tracker-backend \
fitness-tracker-backend
```
```

---

## 📡 API Base URL

```
http://localhost:8080/api
```

Production

```
https://fitness-tracker-backend-klk2.onrender.com/api
```

---

## 🔑 Authentication

Protected endpoints require:

```
Authorization: Bearer <JWT_TOKEN>
```

---

## 📋 Main APIs

### Authentication

```
POST /api/users/register
POST /api/users/login
```

### Activities

```
GET /api/activities
POST /api/activities
PUT /api/activities/{id}
DELETE /api/activities/{id}
```

### Dashboard

```
GET /api/dashboard
GET /api/dashboard/distribution
```

### AI Recommendation

```
POST /api/recommendation/generate/{activityId}
GET /api/recommendation/activity/{activityId}
```

---

## 👨‍💻 Author

Hardik Saini

GitHub:
https://github.com/hahardikk
