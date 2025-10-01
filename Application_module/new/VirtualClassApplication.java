import java.util.*;
import java.util.logging.*;
import java.text.SimpleDateFormat;



interface Observer {
    void update(String message);
}

class Student implements Observer {
    private final String id;

    public Student(String id) { this.id = id; }

    public String getId() { return id; }

    @Override
    public void update(String message) {
        System.out.println(timestamp() + " [Notification to Student " + id + "] " + message);
    }

    @Override
    public String toString() {
        return id;
    }

    private static String timestamp() {
        return "[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]";
    }
}

class Assignment {
    private final String details;
    private final Set<String> submissions = new HashSet<>();

    public Assignment(String details) { this.details = details; }

    public String getDetails() { return details; }

    public void submit(String studentId) {
        submissions.add(studentId);
    }

    public Set<String> getSubmissions() {
        return submissions;
    }

    @Override
    public String toString() {
        return details + " (Submitted by: " + submissions + ")";
    }
}

class Classroom {
    private final String name;
    private final List<Student> students = new ArrayList<>();
    private final List<Assignment> assignments = new ArrayList<>();
    private final Logger logger = Logger.getLogger("VCMLogger");

    public Classroom(String name) { this.name = name; }

    public String getName() { return name; }

    public void addStudent(Student s) {
        if (students.stream().anyMatch(st -> st.getId().equals(s.getId()))) {
            log("Student " + s.getId() + " already exists in " + name);
            return;
        }
        students.add(s);
        log("Student " + s.getId() + " enrolled in " + name);
    }

    public void scheduleAssignment(Assignment a) {
        assignments.add(a);
        log("Assignment scheduled for " + name + ": " + a.getDetails());
        notifyStudents("New assignment scheduled: " + a.getDetails() + " in class " + name);
    }

    public void submitAssignment(String studentId, String details) {
        Optional<Assignment> assignment = assignments.stream()
                .filter(a -> a.getDetails().equals(details))
                .findFirst();

        if (assignment.isEmpty()) {
            log("Assignment " + details + " not found in " + name);
            return;
        }

        if (students.stream().noneMatch(s -> s.getId().equals(studentId))) {
            log("Student " + studentId + " not found in " + name);
            return;
        }

        assignment.get().submit(studentId);
        log("Assignment submitted by Student " + studentId + " in " + name);
    }

    public void listStudents() {
        log("Students in " + name + ": " + students);
    }

    public void listAssignments() {
        if (assignments.isEmpty()) {
            log("No assignments in " + name);
        } else {
            log("Assignments in " + name + ":");
            assignments.forEach(a -> System.out.println(" - " + a));
        }
    }

    private void notifyStudents(String msg) {
        students.forEach(s -> s.update(msg));
    }

    private void log(String message) {
        String ts = "[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]";
        logger.info(ts + " " + message);
    }
}


class Factory {
    public static Classroom createClassroom(String name) {
        return new Classroom(name);
    }
    public static Student createStudent(String id) {
        return new Student(id);
    }
    public static Assignment createAssignment(String details) {
        return new Assignment(details);
    }
}


class ClassroomManager {
    private static ClassroomManager instance;
    private final Map<String, Classroom> classrooms = new HashMap<>();
    private final Logger logger = Logger.getLogger("VCMLogger");

    private ClassroomManager() {}

    public static ClassroomManager getInstance() {
        if (instance == null) {
            instance = new ClassroomManager();
        }
        return instance;
    }

    public void addClassroom(String name) {
        if (classrooms.containsKey(name)) {
            log("Classroom " + name + " already exists.");
            return;
        }
        classrooms.put(name, Factory.createClassroom(name));
        log("Classroom " + name + " created.");
    }

    public void removeClassroom(String name) {
        if (classrooms.remove(name) != null) {
            log("Classroom " + name + " removed.");
        } else {
            log("Classroom " + name + " not found.");
        }
    }

    public Classroom getClassroom(String name) {
        return classrooms.get(name);
    }

    public void listClassrooms() {
        if (classrooms.isEmpty()) {
            log("No classrooms available.");
        } else {
            log("Classrooms: " + classrooms.keySet());
        }
    }

    private void log(String message) {
        String ts = "[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]";
        logger.info(ts + " " + message);
    }
}



public class VirtualClassApplication {
    public static void main(String[] args) {
        // Configure logger to print only once (no duplicate handlers)
        Logger logger = Logger.getLogger("VCMLogger");
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);

        Scanner sc = new Scanner(System.in);
        ClassroomManager manager = ClassroomManager.getInstance();

        System.out.println("Virtual Classroom Manager Started. Type 'help' for commands.");

        while (true) {
            System.out.print(">> ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                System.out.println("Exiting Virtual Classroom Manager. Goodbye!");
                break;
            }
            if (input.equalsIgnoreCase("help")) {
                printHelp();
                continue;
            }
            try {
                processCommand(input, manager);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error processing command", e);
            }
        }
    }

    private static void processCommand(String input, ClassroomManager manager) {
        String[] parts = input.split("\\s+", 2);
        String cmd = parts[0];

        switch (cmd) {
            case "add_classroom" -> {
                if (parts.length < 2) { System.out.println("Usage: add_classroom <ClassName>"); return; }
                manager.addClassroom(parts[1]);
            }
            case "remove_classroom" -> {
                if (parts.length < 2) { System.out.println("Usage: remove_classroom <ClassName>"); return; }
                manager.removeClassroom(parts[1]);
            }
            case "list_classrooms" -> manager.listClassrooms();
            case "add_student" -> {
                String[] args = parts[1].split("\\s+");
                if (args.length < 2) { System.out.println("Usage: add_student <StudentID> <ClassName>"); return; }
                Classroom c = manager.getClassroom(args[1]);
                if (c == null) { System.out.println("Classroom " + args[1] + " does not exist."); return; }
                c.addStudent(Factory.createStudent(args[0]));
            }
            case "list_students" -> {
                Classroom c = manager.getClassroom(parts[1]);
                if (c == null) { System.out.println("Classroom " + parts[1] + " does not exist."); return; }
                c.listStudents();
            }
            case "schedule_assignment" -> {
                String[] args = parts[1].split("\\s+", 2);
                if (args.length < 2) { System.out.println("Usage: schedule_assignment <ClassName> <AssignmentDetails>"); return; }
                Classroom c = manager.getClassroom(args[0]);
                if (c == null) { System.out.println("Classroom " + args[0] + " does not exist."); return; }
                c.scheduleAssignment(Factory.createAssignment(args[1]));
            }
            case "list_assignments" -> {
                Classroom c = manager.getClassroom(parts[1]);
                if (c == null) { System.out.println("Classroom " + parts[1] + " does not exist."); return; }
                c.listAssignments();
            }
            case "submit_assignment" -> {
                String[] args = parts[1].split("\\s+", 3);
                if (args.length < 3) { System.out.println("Usage: submit_assignment <StudentID> <ClassName> <AssignmentDetails>"); return; }
                Classroom c = manager.getClassroom(args[1]);
                if (c == null) { System.out.println("Classroom " + args[1] + " does not exist."); return; }
                c.submitAssignment(args[0], args[2]);
            }
            default -> System.out.println("Unknown command. Type 'help' for options.");
        }
    }

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println(" - add_classroom <ClassName>");
        System.out.println(" - remove_classroom <ClassName>");
        System.out.println(" - list_classrooms");
        System.out.println(" - add_student <StudentID> <ClassName>");
        System.out.println(" - list_students <ClassName>");
        System.out.println(" - schedule_assignment <ClassName> <AssignmentDetails>");
        System.out.println(" - list_assignments <ClassName>");
        System.out.println(" - submit_assignment <StudentID> <ClassName> <AssignmentDetails>");
        System.out.println(" - help");
        System.out.println(" - exit / quit");
    }
}
