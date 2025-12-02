# Food Delivery Ecosystem Management System

## Project Overview
A comprehensive multi-enterprise food delivery management platform that connects restaurants, delivery services, and customers through a unified ecosystem.

## Problem Statement
Managing food delivery operations across multiple enterprises requires coordination between restaurants, delivery services, and administrative teams. This system solves the fragmentation problem by providing a unified platform for all stakeholders.

## Key Features
- **Multi-Enterprise Architecture**: Supports multiple restaurant and delivery enterprises
- **5 User Roles**: SystemAdmin, EnterpriseAdmin, Manager, Customer, DeliveryMan
- **Order Management**: Complete workflow from placement to delivery
- **Work Request System**: Inter-enterprise communication and coordination
- **Secure Authentication**: BCrypt password hashing with SQL injection prevention
- **Real-time Analytics**: Dashboard for system insights
- **Complete CRUD Operations**: For all entities (Users, Orders, Enterprises, etc.)

## Technologies Used
- **Frontend**: Java Swing
- **Backend**: Java with Singleton and DAO patterns
- **Database**: MySQL with JDBC
- **Security**: BCrypt for password hashing
- **Design Patterns**: Singleton, DAO, MVC

## Prerequisites
- Java JDK 8 or higher
- MySQL 5.7 or higher
- MySQL Connector/J 8.0.33
- JBCrypt 0.4

## Installation & Setup

### 1. Database Setup
```sql
CREATE DATABASE food_delivery_db;
USE food_delivery_db;
-- Run the schema.sql file from db/ folder
mysql -u root -p food_delivery_db < db/schema.sql
```

### 2. Configure Database Connection
Edit `src/database/MySQLConnection.java` if needed:
- Default URL: `jdbc:mysql://localhost:3306/food_delivery_db`
- Default User: `root`
- Default Password: `password`

### 3. Add Required Libraries
Place these JAR files in the `lib/` folder:
- mysql-connector-java-8.0.33.jar
- jbcrypt-0.4.jar

### 4. Compile the Project
```bash
# Linux/Mac
javac -cp "lib/*:." -d out $(find src -name "*.java")

# Windows
javac -cp "lib\*;." -d out src\*.java src\**\*.java
```

### 5. Run the Application
```bash
# Linux/Mac
java -cp "lib/*:out" Main

# Windows
java -cp "lib\*;out" Main
```

## Default Login Credentials

| Role | Username | Password |
|------|----------|----------|
| System Admin | sysadmin | sysadmin |
| Enterprise Admin | entadmin1 | entadmin1 |
| Manager | manager1 | manager1 |
| Customer | customer1 | customer1 |
| Delivery Person | delivery1 | delivery1 |

## Workflow Demo

### Customer Order Flow
1. Customer logs in → Places order → Selects restaurant → Adds items
2. Manager logs in → Accepts order → Marks ready → Assigns delivery person
3. Delivery person logs in → Picks up order → Delivers → Marks complete

### Work Request Flow
1. Enterprise Admin creates work request (e.g., need delivery service)
2. Another enterprise receives and processes request
3. Status updates tracked throughout

## Project Structure
```
FoodDeliveryEcosystem/
├── src/
│   ├── Main.java (Application entry point)
│   ├── dao/ (Data Access Objects)
│   ├── model/ (Entity classes)
│   ├── ui/ (User Interface)
│   ├── ecosystem/ (Business logic)
│   ├── database/ (DB connection)
│   └── util/ (Utility classes)
├── db/
│   └── schema.sql (Database schema)
├── lib/ (External libraries)
├── docs/ (Documentation)
└── README.md
```

## Features Demonstrated
- ✅ Multiple enterprises (3+)
- ✅ Multiple roles (5 roles)
- ✅ Inter-enterprise work requests (4+ types)
- ✅ Complete CRUD operations
- ✅ Role-based access control
- ✅ Secure authentication
- ✅ Database persistence
- ✅ Professional UI design

## Author
Oseme Edeoghon

## License
Academic Project - Northeastern University
