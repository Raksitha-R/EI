import java.util.HashSet;
import java.util.Set;

public class Assignment {
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
