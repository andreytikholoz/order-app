# Order Application

This is a simple order management application written in Java, using JDK 11. It utilizes Maven 3 for dependency management and the H2 database for data storage.

## Features

- **Add a New Product**: You can add a new product to the database with its ID, name, price, and quantity.

- **Get List of Available Goods**: Retrieve a list of all available products.

- **Place a New Order**: Create a new order with a list of products, status, creation date, and total price.

- **Mark an Order as Paid**: Set the status of an order as "paid".

## Prerequisites

- JDK 11: [Download JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)

- Maven 3: [Download Maven](https://maven.apache.org/download.cgi)

## Getting Started

1. **Clone the Repository**:

    ```bash
    git clone https://github.com/yourusername/order-application.git
    ```

2. **Build the Project**:

    ```bash
    cd order-application
    mvn clean install
    ```

3. **Run the Application**:

    ```bash
    java -jar target/order-application.jar
    ```

   The application will start at `http://localhost:8080`.

## Usage

- **Add a New Product**:

  Send a POST request to `http://localhost:8080/product` with a JSON body containing the product details (ID, name, price, quantity).

- **Get List of Available Goods**:

  Send a GET request to `http://localhost:8080/product`.

- **Place a New Order**:

  Send a POST request to `http://localhost:8080/order` with a JSON body containing the order details (ID, product list, status, creation date, price).

- **Mark an Order as Paid**:

  Send a PUT request to `http://localhost:8080/order/pay?id=orderId`, replacing `orderId` with the actual order ID.

## Database

The application uses an H2 in-memory database for data storage. The database is created and populated with sample data when the application starts.

## Contributing

Feel free to open issues and pull requests for any improvements or bug fixes.

