import java.util.Scanner;
import java.util.logging.*;

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
        System.out.println(" - exit/quit");
    }
    
}
