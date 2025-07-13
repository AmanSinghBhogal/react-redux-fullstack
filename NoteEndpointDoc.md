# 📝 Notes API Documentation

This document details all endpoints under `/notes` in the Notes App backend. These endpoints are secured using Basic Authentication and allow users to perform CRUD operations on their personal notes.

---

## 🔐 Authentication

- All endpoints except `/notes/fetchAll` require valid authentication.
- A user can only manage their own notes, except for admins who can delete any note.

---

## 📄 Endpoints Overview

### 1. 📋 Fetch All Notes

**`GET /notes/fetchAll`**

Fetches all notes stored in the database.

- **Response**
```json
{
  "status": "Success",
  "respondedAt": "...",
  "response": {
    "data": [
      {
        "id": "note123",
        "title": "My Note",
        "description": "Some details...",
        "user_id": "user123",
        "created_at": "...",
        "updated_at": "..."
      }
    ]
  }
}
```

- **Status Codes**: `200 OK`, `400 Bad Request`

---

### 2. ✏️ Create a Note

**`POST /notes`**

Creates a new note for the authenticated user.

- **Auth Required**: ✅
- **Request Body**:
```json
{
  "title": "New Note",
  "description": "Description of note",
  "user_id": "user123"
}
```

- **Response**
```json
{
  "status": "Success",
  "respondedAt": "...",
  "response": {
    "data": {
      "id": "...",
      "title": "...",
      "description": "...",
      "user_id": "...",
      "created_at": "..."
    }
  }
}
```

- **Status Codes**: `200 OK`, `400 Bad Request`, `401 Unauthorized`

---

### 3. 🛠️ Update a Note

**`PATCH /notes`**

Updates one or more fields of an existing note. Only the note owner can perform this action.

- **Auth Required**: ✅
- **Request Body**:
```json
{
  "id": "note123",
  "title": "Updated Title"
}
```

- **Response**
```json
{
  "status": "Success",
  "respondedAt": "...",
  "response": {
    "data": {
      "id": "...",
      "title": "Updated Title",
      "updated_at": "..."
    }
  }
}
```

- **Status Codes**: `200 OK`, `400 Bad Request`, `401 Unauthorized`

---

### 4. ❌ Delete a Note

**`DELETE /notes?id=<note_id>`**

Deletes a specific note. Users can delete only their own notes, while admins can delete any.

- **Auth Required**: ✅

- **Query Param**: `id` — note ID to delete

- **Response**
```json
{
  "status": "Success",
  "respondedAt": "...",
  "response": {
    "data": "Note deleted Successfully."
  }
}
```

- **Status Codes**: `200 OK`, `400 Bad Request`, `401 Unauthorized`, `404 Not Found`

---

## 🧠 Notes

- `@AuthenticationPrincipal` is used to access the currently logged-in user.
- The `user_id` in notes must match the authenticated user's username unless the user is admin.
- All responses follow a consistent structure:
  - `status`: `Success` or `Failure`
  - `respondedAt`: timestamp of response
  - `response`: actual data or `null`
  - `failureMsg`: included on failure

---

## 📌 Summary Table

| Method | Endpoint           | Auth Required | Description                        |
|--------|--------------------|---------------|------------------------------------|
| GET    | `/notes/fetchAll`  | No            | Fetch all notes                    |
| POST   | `/notes`           | ✅            | Create a new note                  |
| PATCH  | `/notes`           | ✅            | Update a note                      |
| DELETE | `/notes?id=<id>`   | ✅            | Delete a note                      |

---
