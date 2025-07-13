# 📝 Full-Stack Notes App

A simple full-stack **Notes Application** built with **React.js + Redux** on the frontend and **Spring Boot (Java)** on the backend.  
This project serves as a **reference template** for building full-stack applications, including CRUD operations and REST API integration.

---

## 📁 Project Structure
root/
├── notes_app / # Spring Boot application
└── notes_frontend/ # React.js app with Redux


---

## 🔧 Tech Stack

### 🚀 Frontend
- React.js
- Redux Toolkit
- Axios
- React Router

### 🌐 Backend
- Spring Boot
- Spring Data JPA
- Spring Web
- Basic Authentication
- MySQL
- Hibernate

---

## 📦 Features

- Create, Read, Update, Delete notes
- Optional: User authentication (signup/login)
- Notes color-coded or categorized (optional)
- Timestamps for creation and updates
- Responsive design
- API integration with Axios and Redux

---

## 🗄️ Database Schema

MySQL database with two tables:

### `users`

| Field         | Type              | Description             |
|---------------|-------------------|-------------------------|
| id            | VARCHAR(36) (PK)  | user ID                 |
| username      | VARCHAR(50)       | Unique username         |
| email         | VARCHAR(100)      | Unique email address    |
| password_hash | VARCHAR(255)      | Hashed password         |

### `notes`

| Field      | Type             | Description                    |
|------------|------------------|--------------------------------|
| id         | VARCHAR(36) (PK) | note ID                        |
| user_id    | VARCHAR(36) (FK) |     | Foreign key to `users`   |
| title      | VARCHAR(255)     | Note title                     |
| content    | TEXT             | Note body                      |
| color      | VARCHAR(20)      | Optional (e.g., yellow, blue)  |
| created_at | TIMESTAMP        | Auto-set on note creation      |
| updated_at | TIMESTAMP        | Auto-updated on note update    |

> For complete schema, refer to [DB_Schema.txt](./DB_Schema.txt)

# 🧠 Purpose
- This project is intended to:
- Serve as a reference for full-stack development using Spring Boot and React
- Demonstrate folder structure, REST integration, and Redux usage
- Be modular and extendable for additional features

# 📌 Future Enhancements
- JWT-based Authentication
- Notes search/filter functionality
- Deploy to Render, Netlify, Vercel, or Railway
- Rich text editor or Markdown support