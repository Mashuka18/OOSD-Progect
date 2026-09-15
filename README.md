# Food Delivery System

An online food delivery system built with **Java** and **MySQL**, simulating how real-world platforms like Uber Eats or Just Eat work. Users can browse restaurants, order food, track payments, and manage their profile — all in one application.

Developed as part of the Object-Oriented Software Design (OOSD) module.

---

## Description

This project allows users to create an account, log in, and manage their personal information (name, address, contact details). Once logged in, users can explore restaurants and menu items, place orders, and view their order and payment history.

The system is built using **object-oriented programming principles**, with functionality split across dedicated classes for users, orders, restaurants, and payments. Data is persisted in a **MySQL** database with full **CRUD** (Create, Read, Update, Delete) support across all core entities.

## Features

- User registration and login
- View and update personal information (name, address, etc.)
- Browse restaurants and menu items
- Add/remove items from cart and place orders
- Make payments (card or cash)
- View order history and payment history
- Full CRUD operations on users, restaurants, menu items, and orders

## Tech Stack

- **Language:** Java
- **Database:** MySQL
- **Data Access:** DAO pattern with `PreparedStatement` (SQL-injection safe)
- **UI:** Java Swing (GUI-based)
- **Architecture:** Object-Oriented Design

## Database Structure

The system is built on 8 core tables:

| Table | Purpose |
|---|---|
| `users` | User profiles, credentials, delivery address |
| `restaurants` | Restaurant listings and details |
| `categories` | Food categories (e.g. Pizza, Drinks) |
| `menu_items` | Menu items, linked to restaurants and categories |
| `orders` | Order records with status tracking |
| `order_items` | Individual items within each order |
| `payments` | Active payment transactions |
| `payment_history` | Permanent archive of all payments |

*Full ER diagrams and table schemas are available in the [project documentation](./docs).*

## Key Design Highlights

- **Prepared statements** used throughout the DAO layer to prevent SQL injection
- **Dynamic UI rendering** — restaurant cards are generated dynamically from database data
- **Separation of concerns** between GUI classes (e.g. `DashboardGUI`, `AccountGUI`) and data access classes (e.g. `UserDAO`, `RestaurantDAO`)

## Getting Started

### Prerequisites
- Java (JDK 8+)
- MySQL Server

### Setup
1. Clone the repository
   ```bash
   git clone https://github.com/Mashuka18/OOSD-Project.git
   ```
2. Set up the MySQL database using the schema provided in `/docs`
3. Update database connection details in `DatabaseConnection.java`
4. Compile and run the application

## Documentation

Full project documentation — including requirements, ER diagrams, use case specifications, and test cases — is available in the `/docs` folder.

## Conclusion

This project demonstrates practical skills in Java application development, relational database design, and object-oriented software architecture. Potential future improvements include real-time order tracking, enhanced security (password hashing), and integration with external payment gateways.

---

**Author:** Mariia Vanaga
