# High School Exams System
An Application for creating and executing high school exams in Java and MySql.

## Classes
1.**Teacher** - a teacher can create questions and exams that are saved in the database
2.**Student** - a student can execute both manual and computrized exams, also a student can get statistical data about his grades.
3.**Principal** - a principal can get reports and statistical data about the school.

## Structure
Pay attention to the three modules:
1. **client** - a simple client built using JavaFX and OCSF. We use EventBus (which implements the mediator pattern) in order to pass events between classes
2. **server** - a simple server built using OCSF.
3. **entities** - a shared module where all the entities of the project live.

## Running
1. Run Maven install **in the parent project**.
2. Run the server using the exec:java goal in the server module.
3. Run the client using the javafx:run goal in the client module.
4. Press the button and see what happens!
