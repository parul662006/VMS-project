# Vulnerability Management System (VMS)

A simple and clean Java Spring Boot project designed to manage CVE data, user registration, admin panel, and task workflows.  
This project follows a modular structure with proper validations, authentication, and UI integration.

---

## 🚀 Features

### 🔐 Authentication
- Admin Registration  
- Admin Login  
- Input validation for all fields
- Role-based access (Admin/User)  

### 🗂️ CVE Data Management
- Upload CVE data (CSV/Excel support depending on requirement)  
- Store and display vulnerabilities  
- Search vulnerabilities by ID, name, severity, etc.  
- Delete or update CVE entries  

### 🧩 UI Integration
- Modern UI pages for login, registration, dashboard  
- Connected with backend using Thymeleaf (or your UI setup)  
- Mobile-friendly responsive design  

### 🛠️ Additional Functionalities
- Email Notification (on registration or actions)  
- Error handling + validation messages  
- Pagination and sorting  
- Separate dev branch for development  
- Clean folder structure  

---

## 📁 Project Structure

src/
├── main/
│ ├── java/
│ │ └── com/example/myproject/
│ │ ├── config/ # Spring configurations
│ │ ├── controller/ # REST controllers
│ │ ├── converter/ # Entity ↔ DTO converters
│ │ ├── dto/ # Data Transfer Objects
│ │ ├── enumCode/ # Enums for codes/status/constants
│ │ ├── globalException/ # Global exception handlers
│ │ ├── model/ # Entity/model classes
│ │ ├── repository/ # JPA repositories
│ │ ├── response/ # Custom response classes
│ │ ├── service/ # Service layer
│ │ ├── utility/ # Helper/utility functions
│ │ └── viewController/ # Controllers for returning UI pages
│ │
│ ├── resources/
│ │ ├── static/ 
│ │ ├── templates/ # UI templates (Thymeleaf)
│ │ │ ├── admin-add-cve.html
│ │ │ ├── admin-delete-cve.html
│ │ │ ├── admin-login.html
│ │ │ ├── admin-registration.html
│ │ │ ├── admin-update-cve.html
│ │ │ ├── dashboard.html
│ │ │ ├── login.html
│ │ │ ├── registration.html
│ │ │ └── upload-cve-data.html
│ │ └── application.properties
│ │
└── test/

---

## 🔧 Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Thymeleaf / HTML / CSS (UI Part)**
- **H2 / MySQL (as required)**
- **Gradle / Maven**
- **Lombok**

---

## ✨ Future Enhancements
- Improved Dashboard UI
- Bulk upload support for CVE data
- Export reports (PDF/Excel)

---
## 🧡 Author
**Parul Kumawat**  
Java Developer | Spring Boot
