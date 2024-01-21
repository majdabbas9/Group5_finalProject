# High School Exams System
# Overview
The High School Exams System (Server-Client App) is an advanced solution for managing exams in high schools with a client-server architecture. The system incorporates three main user roles: teachers, students, and principals, each with unique interfaces and functionalities. The project is built using Java, leveraging the Hibernate library for database interactions, JavaFX for the graphical user interface, and SQL for database management. The server-client architecture ensures scalability, centralized control, and efficient communication between users.

# Features
# 1.User Roles
a. Teacher:
Create Exams: Teachers can create and manage exams by specifying details such as exam name, date, duration, and associated subjects.
Assign Questions: Teachers can add and assign questions to exams, along with marking schemes.
Grade Exams: After exams are conducted, teachers can input and manage student grades.

b. Student:
Take Exams: Students can take exams online through the system, adhering to the specified time and guidelines.
View Grades: Students can access their grades and feedback provided by teachers.

c. Principal:
Generate Reports: Principals can generate reports on exam performance, student grades, and other relevant statistics.
User Management: Principals can add or remove users (teachers and students) and manage their roles within the system.

# 2. Java Hibernate Library
The project utilizes the Hibernate library for efficient and object-relational mapping. This allows seamless interaction with the underlying SQL database, ensuring data integrity and reliability.

# 3. JavaFX Interface
The user interfaces for teachers, students, and principals are built using JavaFX, providing a user-friendly and intuitive experience. The graphical elements enhance the overall usability of the system.

# 4. SQL Database
The system relies on a SQL database to store and manage data related to exams, questions, students, teachers, and grades. The database structure is designed to accommodate the diverse needs of the system's users.

## Running
1. Run Maven install **in the parent project**.
2. Run the server using the exec:java goal in the server module.
3. Run the client using the javafx:run goal in the client module.
4. Press the button and see what happens!
