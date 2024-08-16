
# **Account Ordering Management (AOM) Module**

## **Table of Contents**
- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [API Endpoints](#api-endpoints)
  - [Authentication & Customer Management](#authentication--customer-management)
  - [Balance Management](#balance-management)
  - [Package Management](#package-management)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

## **Project Overview**
The Account Ordering Management (AOM) module is part of a larger Online Charging System (OCS) project. It is designed to manage customer accounts, handle package subscriptions, and manage balances. This module is built using Spring Boot and integrates with OracleDB and VoltDB to ensure efficient data management and high-performance operations.

## **Features**
- **Customer Registration:** Register new customers and associate them with specific packages.
- **Login Authentication:** Secure authentication for customers using their MSISDN and password.
- **Balance Management:** Manage customer balances, including data, SMS, and minutes.
- **Package Management:** Retrieve and manage package details associated with customer accounts.

## **Technologies Used**
- **Java:** Core language used for development.
- **Spring Boot:** Framework for building the AOM module.
- **OracleDB:** Used for persistent storage of customer data.
- **VoltDB:** Real-time operations for package and balance management.
- **RESTful APIs:** For communication between the client and server.

## **API Endpoints**

### **Authentication & Customer Management**

- **Login:**
  - **Endpoint:** `GET /api/auth/login/{MSISDN}/{PASSWORD}`
  - **Description:** Authenticates a customer using their MSISDN and password.

- **Register with Package:**
  - **Endpoint:** `POST /api/auth/registerWithPackage`
  - **Request Body:**
    ```json
    {
      "MSISDN": "11144467890",
      "NAME": "John",
      "SURNAME": "Doe",
      "EMAIL": "johndoe@example.com",
      "PASSWORD": "password",
      "SECURITY_KEY": "security123",
      "PACKAGE_ID": "2"
    }
    ```
  - **Description:** Registers a new customer and assigns them to a selected package.

- **Forgot Password:**
  - **Endpoint:** `GET /api/auth/forgotPassword/{MSISDN}/{securityKey}`
  - **Description:** Allows customers to recover their password using a security key.

### **Balance Management**

- **Get All Balances:**
  - **Endpoint:** `GET /api/balance/getAllBalances`
  - **Description:** Retrieves all customer balances stored in OracleDB.

- **Get Customer Balance by MSISDN:**
  - **Endpoint:** `GET /api/balance/getRemainingCustomerBalanceByMsisdn/{MSISDN}`
  - **Description:** Fetches the remaining data, SMS, and minutes for a specified MSISDN.

### **Package Management**

- **Get All Packages:**
  - **Endpoint:** `GET /api/package/getAllPackages`
  - **Description:** Retrieves a list of all available packages.

- **Get Package Details by MSISDN:**
  - **Endpoint:** `GET /api/package/getPackageDetailsByMsisdn/{MSISDN}`
  - **Description:** Fetches package details for a specific customer.

- **Get Package by ID:**
  - **Endpoint:** `GET /api/package/getPackageById/{packageId}`
  - **Description:** Returns details of a package based on its ID.

## **Setup and Installation**
To set up and run the AOM module locally:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/ErenFB/AOM.git
   ```

2. **Navigate to the project directory:**
   ```bash
   cd AOM
   ```

3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

   *Note: Ensure that OracleDB and VoltDB are correctly configured and running.*

## **Usage**

- **Starting the Application:**
  - Use the Spring Boot command to run the application.
  
- **Testing APIs:**
  - Use tools like Postman to interact with the APIs, perform customer registration, retrieve balances, and manage packages.

## **Troubleshooting**

- **Database Connection Issues:**
  - Verify that OracleDB and VoltDB are properly configured.
  
- **API Errors:**
  - Check the logs for detailed error messages and ensure all required parameters are provided.

## **Contributing**
Contributions are welcome! Please fork the repository and submit a pull request for any improvements or bug fixes.

## **License**
This project is licensed under the MIT License.
