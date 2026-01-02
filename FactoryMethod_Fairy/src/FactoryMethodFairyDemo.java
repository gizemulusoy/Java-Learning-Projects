// Product
abstract class Fairy {
    protected String name;
    protected int magicPower;

    public String getName() {
        return name;
    }

    public int getMagicPower() {
        return magicPower;
    }

    public void display() {
        System.out.println(name + " | Magic Power: " + magicPower);
    }
}

// Concrete Products
class FireFairy extends Fairy {
    public FireFairy() {
        this.name = "Fire Fairy";
        this.magicPower = 80;
        System.out.println("Fire Fairy is created...");
    }
}

class IceFairy extends Fairy {
    public IceFairy() {
        this.name = "Ice Fairy";
        this.magicPower = 65;
        System.out.println("Ice Fairy is created...");
    }
}

// Factory
abstract class FairyFactory {

    public abstract Fairy createFairy();

    public void createAndDisplayFairy() {
        Fairy fairy = createFairy();
        System.out.println("Created:");
        fairy.display();
    }
}

// Concrete Factories
class FireFairyFactory extends FairyFactory {
    @Override
    public Fairy createFairy() {
        return new FireFairy();
    }
}

class IceFairyFactory extends FairyFactory {
    @Override
    public Fairy createFairy() {
        return new IceFairy();
    }
}

public class FactoryMethodFairyDemo {

    public static void main(String[] args) {

        FairyFactory fireFactory = new FireFairyFactory();
        FairyFactory iceFactory  = new IceFairyFactory();

        System.out.println("Creating Fire Fairy");
        fireFactory.createAndDisplayFairy();

        System.out.println("\nCreating Ice Fairy");
        iceFactory.createAndDisplayFairy();
    }
}
