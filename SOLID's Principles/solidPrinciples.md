# SOLID's PRINCIPLE

SOLID is an acronym for the first five object-oriented design (OOD) principles. These principles help developers build software that is easy to modify, extend, and maintain over time.

SOLID stands for:

- [S - Single-responsibility Principle](#s---single-responsibility-principle-srp)
- [O - Open-closed Principle](#o---open-closed-principle)
- [L - Liskov Substitution Principle](#l---liskov-substitution-principle)
- [I - Interface Segregation Principle](#i---interface-segregation-principle)
- [D - Dependency Inversion Principle](#d---dependency-inversion-principle)

## S - Single-responsibility Principle (SRP)
The Single Responsibility Principle (SRP) states that a class should have only one reason to change, meaning it should 
have only one job or responsibility. This helps in reducing the complexity of the class, making it easier to maintain.

In simple terms it means that each and every class should have only one responsibility. Best exapmple where SRP is 
implementation is done is in MVC design pattern where model, view and controller has its own responsibility.

### Example of SRP Violation: BankAccount Class

In the following example, the `BankAccount` class violates the **Single Responsibility Principle (SRP)** because it is responsible for both **data management** (account holder, balance, statement) and **business logic** (depositing, withdrawing money, updating statements, printing statements).

```java
// BankAccount class: Violating SRP, it handles both data and operations.
public class BankAccount {
    private String accountHolder;
    private double balance;
    private String statement;

    // Constructor
    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.statement = "Account opened with balance: $" + initialBalance + "\n";
    }

    // Method to deposit money (business logic)
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }
        this.balance += amount;
        updateStatement("Deposited: $" + amount);
    }

    // Method to withdraw money (business logic)
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero.");
            return;
        }
        if (this.balance < amount) {
            System.out.println("Insufficient funds for this withdrawal.");
            return;
        }
        this.balance -= amount;
        updateStatement("Withdrew: $" + amount);
    }

    // Method to update statement (business logic)
    private void updateStatement(String transactionDetails) {
        this.statement += transactionDetails + "\n";
    }

    // Method to print the statement (business logic)
    public void printStatement() {
        System.out.println("Account Statement for " + this.accountHolder + ":");
        System.out.println(this.statement);
    }
}
```
### Example of SRP Violation: BankAccount Class

To adhere to SRP, we can refactor the BankAccount class into two separate classes:
- BankAccount: Handles account data (account holder, balance).
- BankAccountService: Handles business logic (deposit, withdrawal, statement management).
This makes the class managable and readable.

```java
public class BankAccount {
    private String accountHolder;
    private double balance;

    // Constructor
    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    // Getters and setters for account data
    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
```
```java
public class BankAccountService {
    private BankAccount bankAccount;
    private String statement;

    // Constructor
    public BankAccountService(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        this.statement = "Account opened with balance: $" + bankAccount.getBalance() + "\n";
    }

    // Method to deposit money (business logic)
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        updateStatement("Deposited: $" + amount);
    }

    // Method to withdraw money (business logic)
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero.");
            return;
        }
        if (bankAccount.getBalance() < amount) {
            System.out.println("Insufficient funds for this withdrawal.");
            return;
        }
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        updateStatement("Withdrew: $" + amount);
    }

    // Method to update statement (business logic)
    private void updateStatement(String transactionDetails) {
        this.statement += transactionDetails + "\n";
    }

    // Method to print the statement (business logic)
    public void printStatement() {
        System.out.println("Account Statement for " + bankAccount.getAccountHolder() + ":");
        System.out.println(this.statement);
    }
}
```


## O - Open-closed Principle
The Open-Closed Principle (OCP) suggests that classes should be open for extension but closed for modification. 
This means instead of modifying existing code to add new features, extend the class by creating new subclasses
or methods. This avoids breaking existing functionality.

### Before Applying OCP (Violated)
In this scenario, we have a calculateArea() method inside the AreaCalculator class that calculates the area of 
specific shapes, such as Square and Circle. However, every time we add a new shape, we need to modify the AreaCalculator 
class, which violates the Open/Closed Principle.

```java
// Before OCP - Violating Open/Closed Principle
class AreaCalculator {
    public double calculateArea(Object shape) {
        if (shape instanceof Square) {
            Square square = (Square) shape;
            return square.getLength() * square.getLength();
        } else if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            return Math.PI * circle.getRadius() * circle.getRadius();
        }
        return 0;
    }
}

class Square {
    private double length;

    public Square(double length) {
        this.length = length;
    }

    public double getLength() {
        return length;
    }
}

class Circle {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}
```
### After Applying OCP (Compliant)

To comply with the Open/Closed Principle, we can extend the functionality by creating an interface for Shape and implementing
it in different shape classes (Square, Circle, Rectangle, etc.). The AreaCalculator class will not change when we add new 
shapes; it will only need to interact with objects of the Shape interface, making it open for extension, closed for 
modification.

```java
// Shape interface - Open for extension (we can add new shapes) and closed for modification (no need to change existing code)
interface Shape {
    double getArea();
}

// Square class implements Shape
class Square implements Shape {
    private double length;

    public Square(double length) {
        this.length = length;
    }

    @Override
    public double getArea() {
        return length * length;
    }
}

// Circle class implements Shape
class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
}

// Rectangle class implements Shape
class Rectangle implements Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }
}

// AreaCalculator class - now closed for modification
class AreaCalculator {

    public double calculateArea(Shape shape) {
        return shape.getArea();  // Polymorphic call to the getArea() method
    }
}
```

## L - Liskov Substitution Principle
The Liskov Substitution Principle (LSP) states that objects of a superclass should be replaceable with objects of a subclass
without affecting the correctness of the program. In simpler terms,  If a class is a subtype, you should be able to replace 
the parent class with the child class without affecting the behavior of the program.

### Example of LSP Violation:
Consider a Bird class with a method that allows birds to fly. Now, if we create a subclass Ostrich, which is a type of bird
but can't fly, it would violate LSP because an Ostrich is a Bird but doesn't fully conform to the behavior expected from the
superclass (Bird), which is to fly.

```java
class Bird {
    public void fly() {
        System.out.println("Flying...");
    }
}

class Ostrich extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Ostriches cannot fly");
    }
}
```

### Correcting the Code to Follow LSP:
To follow the Liskov Substitution Principle, we should rethink the design to ensure that Ostrich and other birds that cannot
fly still fit within the Bird class structure. Instead of forcing all birds to implement the fly() method, we can generalize
the behavior of movement and let specific birds implement different kinds of movement.

```java
// General Bird class that can move in some way
abstract class Bird {
    public abstract void move();  // Move could mean different things for different birds
}

// Flyable interface for birds that can fly
interface Flyable {
    void fly();
}

// Sparrow class that can fly
class Sparrow extends Bird implements Flyable {
    @Override
    public void move() {
        fly();  // Sparrow moves by flying
    }

    @Override
    public void fly() {
        System.out.println("Sparrow is flying...");
    }
}

// Ostrich class that can't fly, but can run
class Ostrich extends Bird {
    @Override
    public void move() {
        run();  // Ostrich moves by running, not flying
    }

    public void run() {
        System.out.println("Ostrich is running...");
    }
}
```

## I - Interface Segregation Principle
The Interface Segregation Principle (ISP) advises that no client should be forced to depend on methods it does not use.
In other words, clients should not be forced to depend on interfaces they do not use. This means you should break down
large, monolithic interfaces into smaller, more specific ones. Its similar are Single responsibility principle but for 
interface.

### Before ISP:
In the below example the Robot class is forced to implement the eat() method, which doesn't make sense. 
```java
interface Worker {
    void work();
    void eat();
}

class Employee implements Worker {
    @Override
    public void work() {
        System.out.println("Employee working");
    }
    
    @Override
    public void eat() {
        System.out.println("Employee eating");
    }
}

class Robot implements Worker {
    @Override
    public void work() {
        System.out.println("Robot working");
    }
    
    @Override
    public void eat() {
        throw new UnsupportedOperationException("Robot doesn't eat");
    }
}
```
### After ISP:
Now, the Robot class does not have to implement eat(). The Employee class implements both Workable and Eatable,
while the Robot only implements Workable. This follows ISP.
```java
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class Employee implements Workable, Eatable {
    @Override
    public void work() {
        System.out.println("Employee working");
    }

    @Override
    public void eat() {
        System.out.println("Employee eating");
    }
}

class Robot implements Workable {
    @Override
    public void work() {
        System.out.println("Robot working");
    }
}
```

## D - Dependency Inversion Principle
The Dependency Inversion Principle (DIP) states that high-level modules should not depend on low-level modules; both should
depend on abstractions. Additionally, abstractions should not depend on details, but details should depend on abstractions.
This helps in reducing the coupling between modules and promotes easier testing and maintenance.

### Before DIP
The Switch class directly depends on the LightBulb class. This creates tight coupling between the high-level and low-level
modules, which violates DIP.

```java
class LightBulb {
    public void turnOn() {
        System.out.println("Light bulb turned on");
    }

    public void turnOff() {
        System.out.println("Light bulb turned off");
    }
}

class Switch {
    private LightBulb bulb;

    public Switch(LightBulb bulb) {
        this.bulb = bulb;
    }

    public void operate() {
        bulb.turnOn();
    }
}
```
### After DIP
The Switch class now depends on the abstraction Switchable instead of a concrete class like LightBulb. This allows
the Switch class to work with any device that implements Switchable, such as a Fan. This follows DIP, as the high-level
module (Switch) no longer directly depends on the low-level module (LightBulb).

```java
interface Switchable {
    void turnOn();
    void turnOff();
}

class LightBulb implements Switchable {
    @Override
    public void turnOn() {
        System.out.println("Light bulb turned on");
    }

    @Override
    public void turnOff() {
        System.out.println("Light bulb turned off");
    }
}

class Fan implements Switchable {
    @Override
    public void turnOn() {
        System.out.println("Fan turned on");
    }

    @Override
    public void turnOff() {
        System.out.println("Fan turned off");
    }
}

class Switch {
    private Switchable device;

    public Switch(Switchable device) {
        this.device = device;
    }

    public void operate() {
        device.turnOn();
    }
}
```


## Why Use SOLID Principles?

These principles help:
- Reduce code complexity.
- Make software more modular and testable.
- Simplify future changes and extensions.
- Avoid common pitfalls like tightly coupled code or code duplication.

By adhering to SOLID principles, developers can create robust and flexible software systems that are easier to maintain 
over time.
