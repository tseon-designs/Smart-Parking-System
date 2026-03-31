
# Smart Parking Management System
A professional Enterprise Domain Model for managing parking logistics, built with Java 17, Spring Boot, and Clean Architecture.

 ## Project Overview 
 This system has evolved from a simple data-holding application into a robust Domain-Driven Design (DDD) implementation. It manages parking spots, vehicle check-ins/outs, and automated fee calculations while ensuring strict business rule enforcement at the code level.

## Architectural Highlights
***1. Domain-Driven Design (DDD)***
- 	Aggregate Roots: The ParkingLot and ParkingSession act as guardrails. You can no longer modify a ParkingSpot in isolation; all changes must go through the Aggregate Root to ensure data consistency.
- 	Behavioral Logic: Moved away from "Anemic Models." Entities now contain logic like occupySpot() and calculateTotalRevenue(), placing business rules where the data lives.
* 	Value Objects: Objects like Vehicle are immutable and defined by their attributes (License Plate, Type) rather than just database IDs.
***2. Clean Architecture & CQRS***
-	Command Use Cases: Dedicated logic for actions that change state (e.g., CheckInVehicle).
-	Read Use Cases: Optimized logic for retrieving data (e.g., GetAvailableSpots).
-	Independence: The core business logic (Domain) has zero dependencies on external frameworks, making it highly testable and future-proof.
***3. Professional Persistence***
-	Hibernate ORM: Advanced mapping of Java objects to relational tables.
-	Numeric Identifiers: Swapped String IDs for Long types to leverage database AUTO_INCREMENT for better performance.
-	Cascading Operations: Saving an Aggregate automatically persists all its internal components in a single atomic transaction.

 ## Getting Started
Prerequisites
-	Java 17 or higher
-	Maven 3.6+
## Installation & Running
1.	Clone the repository:
```Bash```
 `git clone https://github.com/tseon-designs/Smart-Parking-System.git`
2.	Navigate to the project directory:
```Bash```
`cd smart-parking`
3.	Run the application using the Maven Wrapper:
Bash
### Windows
`.\mvnw spring-boot:run`

### Mac/Linux
`./mvnw spring-boot:run`

## Testing
The project includes a comprehensive suite of tests:
-	Unit Tests: Validating domain logic and fee calculations.
-	Integration Tests: Ensuring the Application layer communicates correctly with the Database.
Bash
`./mvnw test `

## Tech Stack
-	Backend: Java 17, Spring Boot 3.x
-	Data: Spring Data JPA, Hibernate, H2/PostgreSQL
-	Testing: JUnit 5, Mockito
-	Patterns: DDD, Clean Architecture, CQRS, Factory Pattern

***Developed as an exploration of Enterprise Java Architecture.***

