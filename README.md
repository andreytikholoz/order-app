# Order Application

This is a simple order management application written in Java, utilizing JDK 11. It employs Maven 3 for dependency management and the H2 database for data storage.

## Features

- **Add a New Product**: Allows a manager to add a new product to the database with its ID, name, price, and quantity.

- **Get List of Available Goods**: Retrieves a list of all available products. Both clients and managers can use this feature.

- **Place a New Order**: Allows users to create a new order with a list of products, status, creation date, and total price.

- **Mark an Order as Paid**: Allows users to set the status of an order as "paid".

## Prerequisites

- JDK 11: [Download JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)

- Maven 3: [Download Maven](https://maven.apache.org/download.cgi)

## Authentication and Roles

Basic authentication is employed to distinguish between clients and managers. When sending a request to **Add a New Product**, the `Authorization` header should be included with the manager's username and password, encoded in Base64 format.

## Getting Started

1. **Clone the Repository**:

    ```bash
    git clone https://github.com/andreytikholoz/order-app
    ```

2. **Build the Project**:

    ```bash
    cd order-app
    mvn clean install
    ```

3. **Run the Application**:

    ```bash
    java -jar target/order-app.jar
    ```

   The application will start at `http://localhost:8080`.

## Usage

- **Add a New Product**:

  Send a POST request to `http://localhost:8080/product` with a JSON body containing the product details (name, price, quantity). This request can only be made by a manager.

- **Get List of Available Goods**:

  Send a GET request to `http://localhost:8080/product`. This request can be made by both clients and managers.

- **Place a New Order**:

  Send a POST request to `http://localhost:8080/order` with a JSON body containing the order details (product list, total price). This request can only be made by a client.

- **Mark an Order as Paid**:

  Send a PATCH request to `http://localhost:8080/order/pay?id=orderId`, replacing `orderId` with the actual order ID. This request can only be made by a client.

## Database

The application uses an H2 in-memory database for data storage. The database is created and populated with sample data when the application starts.
