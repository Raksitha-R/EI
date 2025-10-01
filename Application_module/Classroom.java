import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class Classroom {
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
