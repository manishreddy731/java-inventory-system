# Java Inventory Management System

A full-stack web application built from the ground up using core Java technologies. It provides a user-friendly interface to manage a product inventory with complete CRUD (Create, Read, Update, Delete) functionality. This project demonstrates a solid understanding of backend development, database integration, and frontend communication without relying on heavy frameworks like Spring or Hibernate.



---

## ✨ Key Features

* **View All Products:** Displays all products from the database in a clean, responsive table.
* **Add New Products:** A simple form to add new items to the inventory with data validation.
* **Edit Existing Products:** Click an "Edit" button to populate the form with an item's details for easy updating.
* **Delete Products:** Remove items from the inventory with a confirmation prompt.
* **Dynamic UI:** The frontend is powered by vanilla JavaScript using the Fetch API (AJAX) for asynchronous communication, so the page never has to reload to reflect data changes.

---

## 🛠️ Tech Stack

| Category      | Technology                               |
|---------------|------------------------------------------|
| **Backend** | Java, Java Servlets, JDBC                |
| **Frontend** | HTML5, CSS3, Vanilla JavaScript (Fetch API) |
| **Database** | MySQL                                    |
| **Web Server**| Apache Tomcat                            |
| **Build Tool**| Apache Maven                             |

---

## 🚀 Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You will need the following software installed on your machine:
* Java Development Kit (JDK) 11 or higher
* Apache Maven
* Apache Tomcat 10 or higher
* MySQL Server

### Installation and Setup

1.  **Clone the Repository**
    ```bash
    git clone [https://github.com/manishreddy731/java-inventory-system.git](https://github.com/manishreddy731/java-inventory-system.git)
    cd java-inventory-system
    ```

2.  **Database Setup**
    * Make sure your MySQL server is running.
    * Open a MySQL client (like MySQL Workbench) and execute the following script to create the required database and table:
        ```sql
        CREATE DATABASE IF NOT EXISTS inventory_db;

        USE inventory_db;

        CREATE TABLE IF NOT EXISTS product (
          `id` bigint NOT NULL AUTO_INCREMENT,
          `name` varchar(100) NOT NULL,
          `quantity` int NOT NULL,
          `price` double NOT NULL,
          PRIMARY KEY (`id`)
        );
        ```

3.  **Configure Database Connection**
    * Open the file: `src/main/java/com/example/inventory/dao/ProductDAO.java`.
    * Update the `jdbcPassword` variable with the password for your MySQL root user.
        ```java
        private String jdbcPassword = "your_mysql_password"; // <-- CHANGE THIS
        ```

4.  **Build the Project**
    * Open a command prompt or terminal in the project's root directory.
    * Run the following Maven command to build the project. This will download dependencies and package the application into a `.war` file.
        ```bash
        mvn clean install
        ```

5.  **Deploy to Tomcat**
    * Locate the generated `.war` file in the `target/` directory (e.g., `inventory-system-1.0-SNAPSHOT.war`).
    * Copy this file into the `webapps/` directory of your Apache Tomcat installation.
    * Start the Tomcat server by running `startup.bat` (on Windows) or `startup.sh` (on Mac/Linux) from the Tomcat `bin/` folder.

6.  **Access the Application**
    * Open your web browser and navigate to:
        **`http://localhost:8080/inventory-system-1.0-SNAPSHOT/`**

---
## 🏛️ Project Architecture

This application follows a classic 3-Tier Architecture:

1.  **Presentation Layer (Frontend):** The `index.html`, CSS, and JavaScript files that run in the user's browser.
2.  **Logic Layer (Backend):** The Apache Tomcat server runs the Java Servlet (`ProductServlet.java`), which handles incoming HTTP requests and acts as a controller.
3.  **Data Layer (Database):** The `ProductDAO.java` class uses JDBC to communicate with the MySQL database, which stores all the product information.

---
## 📄 License

This project is licensed under the MIT License - see the LICENSE.md file for details.
