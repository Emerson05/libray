# 📚 Library Management System - Spring Boot API

This project is a **REST API** for digital library management. It allows for the registration of users and books, while handling a complete loan system with integrated business rules.

## 🚀 Technologies Used

### Back-end
* **Java 17 / 21**
* **Spring Boot 3**
* **Spring Data JPA** (Data persistence)
* **Hibernate** (ORM)
* **PostgreSQL / MySQL** (Relational Database)
* **Bean Validation** (Data integrity and validation)

### Front-end (API Consumption)
* **Vanilla JavaScript** (ES6+)
* **Materialize CSS** (UI based on Google's Material Design)

---

## 🛠️ Key Features

### Book & User Management
* **Full CRUD for Books:** Title, Author, ISBN, Page Count.
* **User registration:** Linked with loan records.

### Loan Business Logic
* **Availability Validation:** Prevents a book from being borrowed if it currently has an active loan.
* **Automatic Date Calculation:** Automatically generates the loan date and calculates the expected return date.
* **Loan Updates:** Ability to extend return deadlines via the `PUT` endpoint.
* **CORS Enabled:** Configured to allow requests from external front-end applications.

---

## 🛣️ API Endpoints

### Books
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/books` | Register a new book |
| `GET` | `/books` | List all available books |
| `DELETE` | `/books/{id}` | Remove a book from the catalog |

### Loans
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/loans` | Register a new loan (with availability check) |
| `GET` | `/loans` | List all active loans |
| `PUT` | `/loans/{id}` | Update/Renew return deadline |
| `DELETE` | `/loans/{id}` | Finish/Delete a loan record (Book Return) |

---

## 🔧 How to Run the Project

### 1. Clone the repository
```bash
git clone [https://github.com/your-username/repository-name.git](https://github.com/your-username/repository-name.git)

2. Database Configuration
Update your database credentials in:
src/main/resources/application.properties

3. Run the Back-end
Using IntelliJ IDEA or via terminal:
./mvnw spring-boot:run

4. Run the Front-end
Simply open the index.html file in any modern web browser.

