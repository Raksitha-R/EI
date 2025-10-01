import java.util.Scanner;

interface IceCream {
    String getDescription();
    double getCost();
}

class BasicIceCream implements IceCream {
    public String getDescription() { return "Vanilla Ice Cream"; }
    public double getCost() { return 50.0; }
}

abstract class ToppingDecorator implements IceCream {
    protected IceCream iceCream;
    public ToppingDecorator(IceCream iceCream) { this.iceCream = iceCream; }
}

class Sprinkles extends ToppingDecorator {
    public Sprinkles(IceCream iceCream) { super(iceCream); }
    public String getDescription() { return iceCream.getDescription() + ", Sprinkles"; }
    public double getCost() { return iceCream.getCost() + 10.0; }
}

class ChocolateSauce extends ToppingDecorator {
    public ChocolateSauce(IceCream iceCream) { super(iceCream); }
    public String getDescription() { return iceCream.getDescription() + ", Chocolate Sauce"; }
    public double getCost() { return iceCream.getCost() + 15.0; }
}

class BerryCompote extends ToppingDecorator {
    public BerryCompote(IceCream iceCream) { super(iceCream); }
    public String getDescription() { return iceCream.getDescription() + ", Berry Compote"; }
    public double getCost() { return iceCream.getCost() + 20.0; }
}

public class Decorator_IcecreamApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IceCream order = new BasicIceCream();

        System.out.print("Add Sprinkles? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes")) order = new Sprinkles(order);

        System.out.print("Add Chocolate Sauce? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes")) order = new ChocolateSauce(order);

        System.out.print("Add Berry Compote? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes")) order = new BerryCompote(order);

        System.out.println("Order: " + order.getDescription());
        System.out.println("Total Cost: ₹" + order.getCost());
    }
}
