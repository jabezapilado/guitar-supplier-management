# Guitar Supplier Management System

> Independently developed in **2024** by Jabez Timothy Apilado.

A console-based Java application for managing guitar suppliers and inventory. The system persists supplier and product data in MySQL and provides CRUD workflows through a menu-driven interface.

## Project details

| | |
|---|---|
| **Year developed** | 2024 |
| **Project type** | Independent academic project |
| **Interface** | Java console application |
| **Database** | MySQL via JDBC |

## Features

- Supplier management: add, view, update, and delete supplier records
- Product management: add, view, update, and delete product records
- Browse products by supplier or guitar type
- Supports acoustic, electric, and bass guitars
- Initializes the database tables and sample data on first run

## What I built

- Object-oriented domain models for suppliers and acoustic, electric, and bass guitars
- DAO and manager layers for database-backed supplier and product operations
- Custom input validation and exception handling for user-entered data
- Database initialization that creates the schema, tables, and starter records when needed

## Built with

- Java
- JDBC
- MySQL

## Requirements

- JDK 8 or newer
- MySQL Server
- MySQL Connector/J 9.2.0

## Setup

1. Download `mysql-connector-j-9.2.0.jar` from [Maven Central](https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.2.0/mysql-connector-j-9.2.0.jar).
2. Create a `lib` folder in the project root and place the downloaded file there:

   ```text
   lib/mysql-connector-j-9.2.0.jar
   ```

3. Start your MySQL server and configure the connection through environment variables:

   ```bash
   export DB_URL="jdbc:mysql://localhost:3306/"
   export DB_NAME="guitar_management_db"
   export DB_USER="your_mysql_username"
   export DB_PASSWORD="your_mysql_password"
   ```

   `DB_NAME` defaults to `guitar_management_db` and `DB_URL` defaults to `jdbc:mysql://localhost:3306/` when omitted.

4. Compile and run the application:

   ```bash
   chmod +x compile.sh run.sh
   ./compile.sh
   ./run.sh
   ```

The configured MySQL user needs permission to create the database and tables on the first run.

## Database behavior

On startup, the application connects to MySQL and creates the `guitar_management_db` database if it does not already exist. It then verifies the `suppliers` and `products` tables, creates them when required, and inserts starter supplier and product records.

Database credentials are intentionally supplied through environment variables and are not stored in this repository.

## Project structure

```text
src/com/guitar/
├── dataexceptions/  # Custom validation and exception handling
├── main/            # Application entry point
├── management/      # Supplier and product managers/DAOs
├── models/          # Guitar and supplier data models
└── utils/           # Database connection and initialization
```

## Author

**Jabez Timothy Apilado** — Independent developer, 2024
[GitHub](https://github.com/jabezapilado)

## Acknowledgements

- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) for JDBC database connectivity.
