# Food Delivery System

An online food delivery desktop application built with **Java, Swing and MySQL**, simulating how platforms like Uber Eats or Just Eat work. Users can browse restaurants, order food, pay, and manage their profile.

Developed as part of the **Object-Oriented Software Design (OOSD)** module.

## Features

- User registration and login
- View and update personal information (name, address, contact details)
- Browse restaurants and menu items by category
- Add / remove items in a cart and place an order
- Pay by card or cash
- View order history and payment history
- CRUD operations for users, restaurants, menu items, orders and payments in the DAO layer (see [CRUD coverage](#crud-coverage))

## Tech stack

| Area          | Technology                                    |
|---------------|-----------------------------------------------|
| Language      | Java                                          |
| GUI           | Java Swing                                    |
| Database      | MySQL                                         |
| Data access   | JDBC, DAO pattern, `PreparedStatement`        |
| Documentation | Javadoc, ER diagram, use cases, test cases    |

## Screenshots

<!-- Add 3–4 screenshots to docs/screenshots and link them here, e.g.: -->
<!-- ![Login](docs/screenshots/login.png) -->

## Database

The system uses 8 tables:

| Table             | Purpose                                         |
|-------------------|-------------------------------------------------|
| `users`           | User profiles, credentials, delivery address    |
| `restaurants`     | Restaurant listings and details                 |
| `categories`      | Food categories (e.g. Pizza, Drinks)            |
| `menu_items`      | Menu items linked to restaurants and categories |
| `orders`          | Order records with status tracking              |
| `order_items`     | Individual items within each order              |
| `payments`        | Payment transactions                            |
| `payment_history` | Permanent archive of all payments               |

The ER diagram is in `ER Diagram.pdf`.

## Project structure

```
src/
├── DB/           # DatabaseConnection
├── User/         # Login, register, account GUIs, UserDAO, Cart, Session
├── Restaurant/   # Restaurant model and RestaurantDAO
├── MenuItem/     # Menu items, categories, MenuItemDAO
├── Order/        # Orders, order items, OrderDAO, OrderItemsDAO
└── Payment/      # Payment GUI and PaymentDAO
docs/             # Generated Javadoc
database/         # SQL schema
```

## Getting started

### Prerequisites

- - JDK 25 
- MySQL Server 8+

### Setup

1. **Clone the repository**
```
   git clone https://github.com/Mashuka18/OOSD-Project.git
```
2. **Create the database** and import the schema:
```
   mysql -u <user> -p -e "CREATE DATABASE <db_name>;"
   mysql -u <user> -p <db_name> < database/schema.sql
```
3. **Configure the connection** in `src/DB/DatabaseConnection.java` (URL, user, password). Do not commit your real password.
4. **Run the application**
   - From an IDE (VS Code, IntelliJ, Eclipse): run `User.LoginUserGUI`
   - Or from the terminal:
```
     java -jar "OOSD OFDS PROJECT draft.jar"
```

## How an order works

Menu → Cart → Checkout (delivery address) → Payment. An order is created with status `PENDING`, its items and payment are saved, and the status changes to `PAID`.

## Key design highlights

- **DAO pattern:** all SQL lives in dedicated DAO classes (`UserDAO`, `RestaurantDAO`, `OrderDAO`, `PaymentDAO`, ...)
- **`PreparedStatement` everywhere** to prevent SQL injection
- **Dynamic UI:** restaurant cards are generated from database data
- **Packages by domain** (User, Order, Payment, MenuItem, Restaurant)
- `try-with-resources` for connections and statements

## CRUD coverage

Create, read, update and delete are implemented in the DAO classes and demonstrated by standalone classes (e.g. `InsertMenuItem`, `UpdateRestaurant`, `DeletePayment`). The GUI covers registration, login, profile update, browsing, ordering and payment.

## Known limitations & roadmap

- [ ] **Hash passwords** (currently stored as plain text)
- [ ] **Wrap checkout in a database transaction** so creating the order, its items and the payment either all succeed or all roll back
- [ ] **Move business logic out of the GUI** into a service layer (MVC) and replace the static `Cart` / `Session` classes with injected objects
- [ ] **Read database credentials** from environment variables or a git-ignored config file
- [ ] **Use a connection pool** instead of opening a connection per query
- [ ] Add automated tests (JUnit) for the DAOs and order total calculation
- [ ] Real-time order tracking and integration with an external payment gateway

## Documentation

Javadoc is in the `docs/` folder (open `docs/index.html`). Requirements, use case specifications and test cases are in `online food delivery system oosd.docx`.

## Author

Mariia Vanaga
