import java.text.SimpleDateFormat;
import java.util.Date;

public class Student implements Observer {
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
