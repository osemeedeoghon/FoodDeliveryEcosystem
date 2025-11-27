CREATE DATABASE IF NOT EXISTS food_delivery_db;
USE food_delivery_db;

-- Enterprises (e.g., "Boston Food Delivery", "New York Eats")
CREATE TABLE IF NOT EXISTS enterprises (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL -- 'Restaurant', 'Delivery'
);

-- Organizations within Enterprises (e.g., "Admin", "Kitchen", "DeliveryTeam")
CREATE TABLE IF NOT EXISTS organizations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    enterprise_id INT,
    FOREIGN KEY (enterprise_id) REFERENCES enterprises(id) ON DELETE CASCADE
);

-- Users (System Admins, Enterprise Admins, Managers, Customers, Delivery Men)
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL, -- In a real app, hash this!
    role VARCHAR(50) NOT NULL, -- 'SystemAdmin', 'EnterpriseAdmin', 'Manager', 'Customer', 'DeliveryMan'
    name VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    organization_id INT,
    FOREIGN KEY (organization_id) REFERENCES organizations(id) ON DELETE SET NULL
);

-- Menu Items for Restaurants (Assuming each Restaurant is an Organization or managed by a Manager User)
-- For simplicity, let's link menu items to a 'Restaurant' Organization.
CREATE TABLE IF NOT EXISTS menu_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL, -- Links to organizations(id) where type='Restaurant'
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    FOREIGN KEY (restaurant_id) REFERENCES organizations(id) ON DELETE CASCADE
);

-- Orders (Work Requests)
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL, -- Links to users(id)
    restaurant_id INT NOT NULL, -- Links to organizations(id)
    delivery_man_id INT, -- Links to users(id)
    status VARCHAR(50) NOT NULL, -- 'Placed', 'Accepted', 'Cooking', 'ReadyForPickup', 'OutForDelivery', 'Delivered'
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    delivery_address TEXT,
    comment TEXT,
    FOREIGN KEY (customer_id) REFERENCES users(id),
    FOREIGN KEY (restaurant_id) REFERENCES organizations(id),
    FOREIGN KEY (delivery_man_id) REFERENCES users(id)
);

-- Order Items
CREATE TABLE IF NOT EXISTS order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    menu_item_name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- Initial Seed Data
INSERT INTO users (username, password, role, name) VALUES ('sysadmin', 'sysadmin', 'SystemAdmin', 'System Administrator');
