import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class ClassroomManager {
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
