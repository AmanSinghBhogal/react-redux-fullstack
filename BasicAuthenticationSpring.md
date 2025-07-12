# 🔐 Basic Authentication in Spring Boot

This document explains how **Basic Authentication** is implemented in the Notes App backend using **Spring Boot** and **Spring Security**. It is intended to help future developers understand the authentication flow.

---

## 📁 Project Files Involved

- `SecurityConfig.java` – Configures Spring Security
- `User.java` – User entity representing the database model
- `UserRepository.java` – Interface to access users from DB
- `MyUserDetailsService.java` – Custom `UserDetailsService`
- `UserPrincipal.java` – Custom `UserDetails` implementation
- `UserServiceImpl.java` – Business logic for user operations
- `UserControllerImpl.java` – REST API controller for user endpoints

---

## 🧭 Step-by-Step Authentication Flow

### ✅ Step 1: `User.java` (Entity)
- Annotated with `@Entity`
- Fields: `id`, `username`, `email`, `password`, etc.
- Maps to the `users` table in the database

### ✅ Step 2: `UserRepository.java`
- Extends `JpaRepository<User, Integer>`
- Custom method:
  ```java
  Optional<User> findByEmail(String email);
``

### ✅ Step 3: `UserPrincipal.java`

* Implements `UserDetails` interface
* Wraps the `User` entity and provides:

  ```java
  getUsername(), getPassword(), getAuthorities(), isAccountNonLocked(), etc.
  ```

### ✅ Step 4: `MyUserDetailsService.java`

* Implements `UserDetailsService`
* Loads a user from the DB using email:

  ```java
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
  ```

### ✅ Step 5: `UserServiceImpl.java`

* Handles user operations like registration and retrieval
* Can be called from controllers

### ✅ Step 6: `SecurityConfig.java`

* Configures Spring Security

#### 🔐 Authentication Setup

```java
auth.userDetailsService(myUserDetailsService)
    .passwordEncoder(passwordEncoder());
```

#### 🔒 Authorization and HTTP Security

```java
http
  .csrf().disable()
  .authorizeHttpRequests()
    .requestMatchers("/api/public/**").permitAll()
    .anyRequest().authenticated()
  .and()
  .httpBasic(); // Enables Basic Authentication
```

---

## 🧪 Step 7: Testing Authentication

Use `curl` or Postman to test:

```bash
curl -u user@example.com:password123 http://localhost:8080/api/secure/notes
```

* Sends `Authorization: Basic base64(email:password)`
* Spring uses `MyUserDetailsService` to find the user
* Compares the password using `BCryptPasswordEncoder`
* If valid, access is granted; otherwise, `401 Unauthorized`

---

## 🧠 Summary Diagram

```text
[Client] ---> [Basic Auth Header] ---> [Spring Security Filter]
                                |
                                v
               [MyUserDetailsService (by email)] --> [UserRepository]
                                |
                                v
                   [UserPrincipal (UserDetails)]
                                |
                                v
         [Username + Password match using BCrypt]
                                |
                                v
                    ✅ Access Granted (or ❌ 401)
```

---

## 📌 Optional Improvements

* Add JWT token-based authentication
* Use HTTPS in production
* Add role-based access control (`@PreAuthorize`, `hasRole`)
* Rate-limiting and brute-force protection

---
