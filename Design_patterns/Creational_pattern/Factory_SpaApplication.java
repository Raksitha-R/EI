import java.util.Scanner;

interface SpaService {
    void performService();
}

class Massage implements SpaService {
    public void performService() {
        System.out.println("Performing relaxing Massage...");
    }
}

class Facial implements SpaService {
    public void performService() {
        System.out.println("Performing glowing Facial treatment...");
    }
}

class Sauna implements SpaService {
    public void performService() {
        System.out.println("Enjoying hot Sauna session...");
    }
}

class SpaFactory {
    public static SpaService getService(String type) {
        switch (type.toLowerCase()) {
            case "massage": return new Massage();
            case "facial": return new Facial();
            case "sauna": return new Sauna();
            default: throw new IllegalArgumentException("Unknown service: " + type);
        }
    }
}

public class Factory_SpaApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Spa Service (massage/facial/sauna): ");
        String choice = sc.nextLine();

        SpaService service = SpaFactory.getService(choice);
        service.performService();
    }
}
