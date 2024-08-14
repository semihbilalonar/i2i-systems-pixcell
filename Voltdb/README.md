# VoltDbWrapper Class

This class provides an interface to interact with VoltDB, focusing on customer and package management.

## Usage Guidelines

### 1. Connection Setup
- The class automatically connects to a specified VoltDB instance using the IP and port provided during construction. Ensure the correct IP and port values are set in the class before using it.

### 2. Inserting Customers
- `boolean InsertCustomer(long msisdn, String name, String surname, String email, String password, String status, String securityKey)`
  - Inserts a customer into the database without assigning a package (assigns default null package under the hood).
- `boolean InsertCustomerWithPackage(long msisdn, String name, String surname, String email, String password, String status, String securityKey, int package_id)`
  - Inserts a customer and assigns a package, initializing balances (voice, data, SMS) based on the package.

### 3. Fetching Customer Information
- Retrieve customer details using their MSISDN (mobile number):
  - `long getUserID(long MSISDN)`
  - `String getUserName(long MSISDN)`
  - `String getUserSurname(long MSISDN)`
  - `String getUserMail(long MSISDN)`
- Retrieve customer balances:
  - `int getInternetBalance(long MSISDN)`
  - `int getMinutesBalance(long MSISDN)`
  - `int getSmsBalance(long MSISDN)`
  - `int getMoneyBalance(long MSISDN)`

### 4. Package Management
- `VoltTable getPackageDetails(int package_id)`
  - Retrieves all details of a specific package.
- `int[] getPackageIds()`
  - Fetches all available package IDs.
- `List<List<Object>> AllPackages()`
  - Returns a list of all available packages with details.

### 5. Balance Management
- Update customer balances:
  - `boolean setCustomerBalanceByPackageId(long MSISDN, int package_id)`
    - Sets the customer's balance based on the selected package's details.
  - `boolean setMinutesBalance(int amount_minutes, long MSISDN)`
    - Sets the customer’s voice minutes balance to a specified value.
  - `boolean setInternetBalance(int amount_internet, long MSISDN)`
    - Sets the internet balance for a customer to a specific amount.
  - `boolean setSmsBalance(int amount_sms, long MSISDN)`
    - Updates the SMS balance for a customer.
  - `boolean setMoneyBalance(int amount_money, long MSISDN)`
    - Sets the money balance for a customer.
- Increment balances:
  - `boolean updateVoiceBalance(long MSISDN, int amount)`
    - Increases the customer’s voice minutes balance by the specified amount.
  - `boolean updateDataBalance(long MSISDN, int amount)`
    - Increases the customer’s internet data balance by the specified amount.
  - `boolean updateSmsBalance(long MSISDN, int amount)`
    - Increases the customer’s SMS balance by the specified amount.
  - `boolean updateMoneyBalance(long MSISDN, int amount)`
    - Increases the customer’s money balance by the specified amount.

### 6. Error Handling
- `boolean checkResponse(ClientResponse response)`
  - Validates if the `ClientResponse` was successful and if there is any valid data in the response.
  - If a response fails, an exception will be thrown. Make sure to handle exceptions appropriately.

### 7. Extending the Wrapper
- To add new procedures or methods, call VoltDB procedures using `clientInstance.callProcedure()`. Ensure proper procedure names and arguments are passed to avoid runtime errors.

### 8. Data Type Consistency
- **MSISDN** (mobile number) is represented as `long`. Make sure to pass `MSISDN` as `long` in all method calls.

### 9. Batch Operations
- For fetching multiple rows of data:
  - `List<Integer> getPackageIds()`: Retrieves all package IDs.
  - `List<VoltTable> AllPackages()`: Retrieves all available packages with their details.

## Sample Usage

```java
VoltDbWrapper dbWrapper = new VoltDbWrapper();

// Insert a customer without a package
boolean success = dbWrapper.InsertCustomer(1234567890L, "John", "Doe", "john@example.com", "password123", "active", "securityKey");

// Insert a customer with a package
success = dbWrapper.InsertCustomerWithPackage(1234567891L, "Jane", "Smith", "jane@example.com", "password456", "active", "securityKey", 2);

// Fetch a customer's email
String email = dbWrapper.getUserMail(1234567890L);

// Get package details
VoltTable packageDetails = dbWrapper.getPackageDetails(1);

```

## Sample Usage

http://35.198.145.16:32790/

- `http://35.198.145.16:32790/`
  - To access GUI admin panel.
  - Run sql commands directly to test.

![image](https://github.com/user-attachments/assets/ae8d5cd2-3ca0-48fc-b763-fb1567736ba9)


