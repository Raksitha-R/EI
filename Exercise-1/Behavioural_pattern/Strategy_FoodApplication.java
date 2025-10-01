import java.util.Scanner;

interface DeliveryStrategy {
    double calculateCost(double distance);
}

class BikeDelivery implements DeliveryStrategy {
    public double calculateCost(double distance) { return distance * 5; }
}

class CarDelivery implements DeliveryStrategy {
    public double calculateCost(double distance) { return distance * 10; }
}

class DroneDelivery implements DeliveryStrategy {
    public double calculateCost(double distance) { return distance * 20; }
}

class FoodOrder {
    private DeliveryStrategy strategy;
    public void setStrategy(DeliveryStrategy strategy) { this.strategy = strategy; }
    public void checkout(double distance) {
        double cost = strategy.calculateCost(distance);
        System.out.println("Delivery cost for " + distance + " km = Rs" + cost);
    }
}

public class Strategy_FoodApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FoodOrder order = new FoodOrder();

        System.out.print("Enter delivery mode (bike/car/drone): ");
        String mode = sc.nextLine();

        switch (mode.toLowerCase()) {
            case "bike": order.setStrategy(new BikeDelivery()); break;
            case "car": order.setStrategy(new CarDelivery()); break;
            default: order.setStrategy(new DroneDelivery()); break;
        }

        System.out.print("Enter distance (km): ");
        double dist = sc.nextDouble();

        order.checkout(dist);
    }
}
