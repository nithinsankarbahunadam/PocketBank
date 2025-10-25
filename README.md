# 💰 Finance Management System (Java)

A **Java-based Finance Management System** that demonstrates key Java programming concepts such as arrays, collections, exception handling, custom exceptions, and object-oriented programming principles (OOP).  
This project is perfect for beginners who want to practice **core Java concepts** in a real-world financial scenario.

---

## 🧠 Project Overview

The **Finance Management System** allows users to:
- Create and manage bank accounts.
- Deposit and withdraw funds with proper exception handling.
- Simulate monthly balance updates.
- Track and display financial transaction history using Java collections.
- Apply OOP principles like encapsulation, inheritance, polymorphism, and abstraction.
- Handle invalid transactions gracefully with custom exceptions.

This project is implemented as a **Maven-based console application**, making it easy to compile, run, and extend.

---

## ⚙️ Features

### ✅ Core Java Concepts Used
| Concept | Usage |
|----------|--------|
| **Arrays** | To store monthly balances for each account |
| **ArrayList & LinkedList** | To maintain and retrieve transaction histories |
| **Set (HashSet)** | To ensure unique account IDs |
| **Exception Handling** | For handling invalid transactions, insufficient funds, etc. |
| **Custom Exceptions** | `InsufficientBalanceException`, `InvalidTransactionException` |
| **OOP Concepts** | Classes for Account, Customer, Bank; inheritance and encapsulation |
| **Static Members** | To maintain a global account count |
| **Access Modifiers** | To secure class fields and promote encapsulation |

---

## 🏗️ Project Structure

```
FinanceManagementSystem/
│
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/finance/
│   │   │       ├── model/
│   │   │       │   ├── Account.java
│   │   │       │   ├── Customer.java
│   │   │       ├── exceptions/
│   │   │       │   ├── InsufficientBalanceException.java
│   │   │       │   ├── InvalidTransactionException.java
│   │   │       ├── service/
│   │   │       │   └── BankService.java
│   │   │       └── FinanceApp.java
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/finance/
│               └── FinanceAppTest.java
```

---

## 💡 Example Code Snippet

```java
try {
    Account alice = new Account("Alice", 5000);
    alice.deposit(1200);
    alice.withdraw(2000);

    double[] balances = alice.getMonthlyBalances();
    for (int i = 0; i < balances.length; i++) {
        System.out.printf("Month %2d: %.2f%n", i + 1, balances[i]);
    }

} catch (InvalidTransactionException | InsufficientBalanceException e) {
    System.err.println("Transaction failed: " + e.getMessage());
}
```

---

## 🧩 Maven Dependency

```xml
<dependencies>
    <!-- JUnit for unit testing -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.9.3</version>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.9.3</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 🧱 Classes Overview

### **Account.java**
Handles individual account operations like deposit, withdrawal, and monthly balance tracking using arrays.

### **Customer.java**
Represents customer information and account association.

### **BankService.java**
Manages multiple accounts using Java Collections (ArrayList, HashSet).

### **Custom Exceptions**
- `InsufficientBalanceException` – Thrown when balance is not enough for withdrawal.
- `InvalidTransactionException` – Thrown for invalid deposits or negative values.

---

## 🧮 Key Methods

| Method | Description |
|--------|--------------|
| `deposit(double amount)` | Adds funds to account; validates amount |
| `withdraw(double amount)` | Deducts funds; throws exception if insufficient |
| `rollMonthlyBalance()` | Rolls and stores monthly balance into an array |
| `getMonthlyBalances()` | Returns a copy of stored balances |
| `toString()` | Displays account details |

---

## 🧪 Exception Handling Example

```java
try {
    account.withdraw(10000); // may throw exception
} catch (InsufficientBalanceException e) {
    System.err.println("Error: " + e.getMessage());
}
```

---

## 🚀 How to Run

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/nithinsankarbahunadam/FinanceManagementSystem.git
cd FinanceManagementSystem
```

### 2️⃣ Compile the Project
```bash
mvn clean compile
```

### 3️⃣ Run the Application
```bash
mvn exec:java -Dexec.mainClass="com.finance.FinanceApp"
```

### 4️⃣ Run Tests
```bash
mvn test
```

---

## 📈 Sample Output

```
Creating Account for Alice with initial balance: 5000.00
Depositing 1200.00...
Withdrawing 2000.00...
Alice monthly balances (latest last):
  month 01: 4200.00
  month 02: 4400.00
  month 03: 5000.00
```

---

## 🧰 Future Enhancements

- ✅ Add interest calculation for savings accounts
- ✅ Integrate file-based transaction persistence
- ✅ Add GUI using JavaFX or Swing
- ✅ Include REST API backend using Spring Boot

---

## 📚 Concepts Practiced

- Java Collections Framework (List, Set, Map)
- Exception Handling and Custom Exceptions
- OOP Principles (Encapsulation, Inheritance, Polymorphism)
- Arrays and Iteration
- Defensive Copying and Encapsulation Best Practices
- Maven Project Management and JUnit Testing

---

## 👨‍💻 Author

**Nithin Sankar Bahunadam**  
📍 Parsippany, NJ  
🎓 Master's in Big Data Analytics & Information Technology (University of Central Missouri)  
💼 Data Engineer | Java Developer | ML Enthusiast

🔗 [LinkedIn](https://www.linkedin.com/nsankarbahunadam) • [GitHub](https://github.com/nithinsankarbahunadam)

---

## 📝 License

This project is released under the **MIT License** – feel free to use and modify it for learning or personal projects.

---