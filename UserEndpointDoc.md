Here is the **Users Endpoint Documentation** in Markdown format for your API. You can save this as `USERS_ENDPOINTS.md` or add it to your `README.md` under an API section.

---

````markdown
# 📘 Users API Documentation

This document provides a detailed overview of all available `/users` endpoints in the Notes App backend. These endpoints are protected using **Basic Authentication** via Spring Security.

---

## 🔐 Authentication

- **All endpoints except `/users/checkUsername` and `/users` (POST)** require authentication.
- Admin-only access is required for fetching all users.
- Users can only modify/delete their own data unless they are an admin.

---

## 📄 Endpoints Overview

### 1. ✅ Check Username Availability

**`GET /users/checkUsername`**

Checks if a given username is already registered.

- **Query Params**:  
  `username` — (optional) the username to check

- **Response**
```json
{
  "status": "Success",
  "respondedAt": "2025-07-11T18:00:00",
  "response": {
    "data": {
      "alreadyPresent": false
    }
  }
}
````

* **Status Codes**: `200 OK`, `400 Bad Request`

---

### 2. 📥 Register New User

**`POST /users`**

Registers a new user account.

* **Request Body**:

```json
{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "securePassword"
}
```

* **Response**:

```json
{
  "status": "Success",
  "respondedAt": "...",
  "response": {
    "data": {
      "id": "...",
      "username": "...",
      "email": "..."
    }
  }
}
```

* **Status Codes**: `200 OK`, `400 Bad Request`

---

### 3. 🔐 Fetch All Users (Admin Only)

**`GET /users`**

Returns a list of all registered users.
🔒 **Only accessible by `admin`**.

* **Headers**:
  `Authorization: Basic base64(admin:password)`

* **Response**:

```json
{
  "status": "Success",
  "respondedAt": "...",
  "response": {
    "data": [
      {
        "id": "...",
        "username": "...",
        "email": "..."
      },
      ...
    ]
  }
}
```

* **Status Codes**: `200 OK`, `401 Unauthorized`, `400 Bad Request`

---

### 4. ❌ Delete User

**`DELETE /users?Id=<username>`**

Deletes the account of the currently authenticated user or another user if the requester is admin.

* **Query Param**: `Id` — username of the user to delete

* **Authorization**:

  * Allowed if:

    * Authenticated user matches `Id`, **or**
    * Authenticated user is `admin`

* **Response**:

```json
{
  "status": "Success",
  "respondedAt": "...",
  "response": {
    "data": {
      "Id": "johndoe"
    }
  }
}
```

* **Status Codes**: `200 OK`, `400 Bad Request`, `401 Unauthorized`

---

### 5. ✏️ Update User (Partial)

**`PATCH /users`**

Updates fields of the currently authenticated user.

* **Request Body**: (partial fields allowed)

```json
{
  "email": "newemail@example.com"
}
```

* **Authorization**:

  * Only the logged-in user can update their own account

* **Response**:

```json
{
  "status": "Success",
  "respondedAt": "...",
  "response": {
    "data": {
      "username": "johndoe",
      "email": "newemail@example.com"
    }
  }
}
```

* **Status Codes**: `200 OK`, `400 Bad Request`, `401 Unauthorized`

---

## 🧠 Notes

* `@AuthenticationPrincipal` is used to get the currently authenticated user from Spring Security context.
* `userService` handles business logic behind all user operations.
* All responses follow a consistent structure:

  * `status`: `Success` or `Failure`
  * `respondedAt`: timestamp of response
  * `response`: actual data or `null`
  * `failureMsg`: included if the request failed

---

## 📌 Summary Table

| Method | Endpoint               | Auth Required | Role       | Description                 |
| ------ | ---------------------- | ------------- | ---------- | --------------------------- |
| GET    | `/users/checkUsername` | ❌             | Public     | Check if username exists    |
| POST   | `/users`               | ❌             | Public     | Register a new user         |
| GET    | `/users`               | ✅             | Admin      | Fetch all users             |
| DELETE | `/users?Id=<username>` | ✅             | User/Admin | Delete user account         |
| PATCH  | `/users`               | ✅             | User only  | Update current user details |

---
