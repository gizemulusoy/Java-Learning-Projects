import java.util.ArrayList;

// Abstract Product
abstract class Unit {
    abstract String displayName();
    abstract double getPower();
}

// Abstract Products
abstract class Enemy extends Unit {
    protected String name;
    protected double power;

    @Override
    public String displayName() {
        return name;
    }

    @Override
    public double getPower() {
        return power;
    }
}

abstract class Weapon extends Unit {
    protected String name;
    protected double power;

    @Override
    public String displayName() {
        return name;
    }

    @Override
    public double getPower() {
        return power;
    }
}

// Concrete Products (Zombie)
class ZombieEnemy extends Enemy {
    public ZombieEnemy(double power) {
        this.power = power;
        this.name = "Zombie Enemy";
        System.out.println("Zombie Enemy is created...");
    }
}

class ZombieWeapon extends Weapon {
    public ZombieWeapon(double power) {
        this.power = power;
        this.name = "Axe (Zombie Weapon)";
        System.out.println("Zombie Weapon is created...");
    }
}

// Concrete Products (Robot)
class RobotEnemy extends Enemy {
    public RobotEnemy(double power) {
        this.power = power;
        this.name = "Robot Enemy";
        System.out.println("Robot Enemy is created...");
    }
}

class RobotWeapon extends Weapon {
    public RobotWeapon(double power) {
        this.power = power;
        this.name = "Laser Gun (Robot Weapon)";
        System.out.println("Robot Weapon is created...");
    }
}

// Abstract Factory
abstract class EnemyFactory {
    abstract Enemy createEnemy();
    abstract Weapon createWeapon();
}

// Concrete Factories
class ZombieFactory extends EnemyFactory {
    @Override
    public Enemy createEnemy() {
        return new ZombieEnemy(80.0);
    }

    @Override
    public Weapon createWeapon() {
        return new ZombieWeapon(25.0);
    }
}

class RobotFactory extends EnemyFactory {
    @Override
    public Enemy createEnemy() {
        return new RobotEnemy(95.0);
    }

    @Override
    public Weapon createWeapon() {
        return new RobotWeapon(40.0);
    }
}

// Client
class SpawnWave {
    private ArrayList<Unit> units;

    public void spawn(EnemyFactory factory) {
        units = new ArrayList<>();
        units.add(factory.createEnemy());
        units.add(factory.createWeapon());
    }

    public void displaySpawned() {
        System.out.println("\tListing Spawned Units");
        System.out.println("\t");
        units.forEach(u ->
                System.out.println("\t" + u.displayName() + " | Power: " + u.getPower())
        );
    }
}

// Entry Point
public class AbstractFactoryEnemyDemo {

    public static void main(String[] args) {

        EnemyFactory zombieFactory = new ZombieFactory();
        EnemyFactory robotFactory  = new RobotFactory();

        SpawnWave wave = new SpawnWave();

        System.out.println("Spawning Zombie");
        wave.spawn(zombieFactory);
        wave.displaySpawned();

        System.out.println("\nSpawning Robot");
        wave.spawn(robotFactory);
        wave.displaySpawned();
    }
}
