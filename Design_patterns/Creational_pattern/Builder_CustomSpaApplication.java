import java.util.Scanner;

class SpaPackage {
    private String massage;
    private String facial;
    private String sauna;

    public void setMassage(String massage) { this.massage = massage; }
    public void setFacial(String facial) { this.facial = facial; }
    public void setSauna(String sauna) { this.sauna = sauna; }

    public void showPackage() {
        System.out.println("Your Spa Package includes:");
        if (massage != null) System.out.println("- " + massage);
        if (facial != null) System.out.println("- " + facial);
        if (sauna != null) System.out.println("- " + sauna);
    }
}

class SpaPackageBuilder {
    private SpaPackage pkg = new SpaPackage();

    public SpaPackageBuilder addMassage(String massage) {
        pkg.setMassage(massage);
        return this;
    }
    public SpaPackageBuilder addFacial(String facial) {
        pkg.setFacial(facial);
        return this;
    }
    public SpaPackageBuilder addSauna(String sauna) {
        pkg.setSauna(sauna);
        return this;
    }
    public SpaPackage build() {
        return pkg;
    }
}

public class Builder_CustomSpaApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SpaPackageBuilder builder = new SpaPackageBuilder();

        System.out.print("Add Massage? (enter type or 'no'): ");
        String m = sc.nextLine();
        if (!m.equalsIgnoreCase("no")) builder.addMassage(m);

        System.out.print("Add Facial? (enter type or 'no'): ");
        String f = sc.nextLine();
        if (!f.equalsIgnoreCase("no")) builder.addFacial(f);

        System.out.print("Add Sauna? (enter type or 'no'): ");
        String s = sc.nextLine();
        if (!s.equalsIgnoreCase("no")) builder.addSauna(s);

        SpaPackage pkg = builder.build();
        pkg.showPackage();
    }
}
