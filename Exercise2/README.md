# Virtual Classroom Manager 

## 📌 Problem Statement
The **Virtual Classroom Manager** is a Java console-based application that simulates an online classroom environment.  
It allows users to create classrooms, enroll students, schedule assignments, and notify students about new tasks.  
Students can then submit assignments, and the system maintains logs of all activities.

---

## 🎯 Functional Requirements
1. Create and manage multiple classrooms.
2. Add and list students in classrooms.
3. Schedule assignments for classrooms.
4. Notify students of new assignments (Observer pattern).
5. Submit assignments per student.
6. Maintain centralized classroom state using Singleton pattern.
7. Ensure consistent creation of objects using Factory pattern.
8. Log important events with timestamps.

---

## 🛠️ Design Patterns Used
- **Observer Pattern**  
  Used to notify students when a new assignment is scheduled.
  
- **Singleton Pattern**  
  Ensures only one instance of `ClassroomManager` exists to manage all classrooms.

- **Factory Pattern**  
  Used to create `Student`, `Classroom`, and `Assignment` objects consistently.

---
## 📂 Project Structure
Exercise2/
│── README.md
│── VirtualClassApplication.java # Main application
│── ClassroomManager.java # Singleton manager for classrooms
│── Classroom.java # Classroom class with students and assignments
│── Student.java # Observer class (students receive notifications)
│── Assignment.java # Assignment entity
│── Factory.java # Factory class to create objects
│── Observer.java # Observer interface

## ▶️ How to Run

### 1. Compile and Run
```bash
javac *.java

java VirtualClassApplication

