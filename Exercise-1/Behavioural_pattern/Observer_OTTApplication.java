import java.util.*;

interface Subscriber {
    void update(String movie);
}

class User implements Subscriber {
    private String name;
    public User(String name) { this.name = name; }
    public void update(String movie) {
        System.out.println(name + " received notification: New release -> " + movie);
    }
}

class OTTPlatform {
    private List<Subscriber> subscribers = new ArrayList<>();
    public void subscribe(Subscriber s) { subscribers.add(s); }
    public void notifyUsers(String movie) {
        for (Subscriber s : subscribers) s.update(movie);
    }
}

public class Observer_OTTApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OTTPlatform netflix = new OTTPlatform();

        System.out.print("Enter number of users to subscribe: ");
        int n = sc.nextInt(); sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter username: ");
            String name = sc.nextLine();
            netflix.subscribe(new User(name));
        }

        System.out.print("Enter new movie/series release: ");
        String movie = sc.nextLine();

        netflix.notifyUsers(movie);
    }
}
