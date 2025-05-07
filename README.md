# Warehouse Management System

This is a simple warehouse management system built with Java. It is operated through the command line.

## Building and Running

This project was built using Maven and uses Java 21. Java 21 is required on your system to run locally.

In your IDE you can run the project directly by running the Main class, or you can build it using Maven and run the jar file.

You can also build the Dockerfile and run the image.

### Build with Maven

To build the project with Maven, run the following command in the root directory of the project:

```bash
mvn clean package -DskipTests
```

Some tests interact directly with the CLI, so they are skipped in this command as it requires the JAR file to exist.

To run the project, use the following command:

```bash
java -jar target/warehouse-management-system-1.0-SNAPSHOT.jar
```

### Run with Docker

To build the Docker image, run the following command in the root directory of the project:

```bash
docker build -t warehouse-management-system .
```

This will create a Docker image named `warehouse-management-system`.

To run the Docker image, use the following command:

```bash
docker run -it --rm warehouse-management-system
```

This will open an interactive shell where you can enter commands.

The image can be built with other tools such as podman. Please refer to their documentation for equivalent commands.

## Commands

Commands are entered in the command line interface (CLI) and are case-insensitive.

Most commands will require additional input. This input will be prompted after the command is entered. 
For example, if you enter the command `ORDER_STOCK`, the system will prompt you for the supplier name, item name, quantity, and price. 
After entering this information, the system will process the order.

The system will respond with a message indicating the success or failure of the command.

Valid commands are:

- HELP: List available commands
- EXIT: Exit the application
- ORDER_STOCK: Order stock from a supplier
- TAKE_DELIVERY: Take delivery of stock
- CUSTOMER_ORDER: Create a customer order
- REGISTER_SUPPLIER: Register a new supplier
- REGISTER_ITEM: Register a new item in the inventory
- FIND_ORDERS: Find customer orders
- FIND_INVENTORY: Find items in the inventory
- FIND_SUPPLIERS: Find suppliers
- FIND_STOCK_ORDERS: Find stock orders
- FINANCIAL_REPORT: Generate a financial report
- UPDATE_SUPPLIER_INFO: Update existing supplier information

## System Design

See [Design documentation](design/design.md) for the system design.