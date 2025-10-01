public class Factory {
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
