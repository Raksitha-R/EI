import java.util.Scanner;

interface TeachingMode {
    void teach(String courseLevel);
}

class OnlineMode implements TeachingMode {
    public void teach(String courseLevel) {
        System.out.println(courseLevel + " course is taught ONLINE.");
    }
}

class OfflineMode implements TeachingMode {
    public void teach(String courseLevel) {
        System.out.println(courseLevel + " course is taught OFFLINE in classroom.");
    }
}

abstract class Course {
    protected TeachingMode mode;
    public Course(TeachingMode mode) { this.mode = mode; }
    abstract void conductClass();
}

class PrimaryCourse extends Course {
    public PrimaryCourse(TeachingMode mode) { super(mode); }
    public void conductClass() { mode.teach("Primary"); }
}

class SecondaryCourse extends Course {
    public SecondaryCourse(TeachingMode mode) { super(mode); }
    public void conductClass() { mode.teach("Secondary"); }
}

class HigherCourse extends Course {
    public HigherCourse(TeachingMode mode) { super(mode); }
    public void conductClass() { mode.teach("Higher"); }
}

public class Bridge_CourseApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter course level (primary/secondary/higher): ");
        String level = sc.nextLine();

        System.out.print("Enter mode (online/offline): ");
        String mode = sc.nextLine();

        TeachingMode tm = mode.equalsIgnoreCase("online") ? new OnlineMode() : new OfflineMode();
        Course course;

        switch (level.toLowerCase()) {
            case "primary": course = new PrimaryCourse(tm); break;
            case "secondary": course = new SecondaryCourse(tm); break;
            default: course = new HigherCourse(tm); break;
        }

        course.conductClass();
    }
}
