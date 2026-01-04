// Client class
class Player {
    private String name;
    private int level;
    private int energy;

    public Player(String name, int level, int energy) {
        this.name = name;
        this.level = level;
        this.energy = energy;
    }

    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getEnergy() { return energy; }

    public void consumeEnergy(int amount) {
        energy -= amount;
    }
}

// Subsystem 1: Level
class LevelSystem {
    public boolean hasRequiredLevel(Player p, int requiredLevel) {
        System.out.println("Check level of " + p.getName() + " (needs " + requiredLevel + ")");
        return p.getLevel() >= requiredLevel;
    }
}

// Subsystem 2: Inventory
class InventorySystem {
    public boolean hasRequiredItem(Player p, String itemName) {
        System.out.println("Check inventory for " + p.getName() + " (needs: " + itemName + ")");

        return true;
    }
}

// Subsystem 3: Energy
class EnergySystem {
    public boolean hasEnoughEnergy(Player p, int neededEnergy) {
        System.out.println("Check energy of " + p.getName() + " (needs " + neededEnergy + ")");
        return p.getEnergy() >= neededEnergy;
    }
}

// Subsystem 4: Quest
class QuestSystem {
    public boolean isBossUnlocked(Player p, String bossName) {
        System.out.println("Check if boss is unlocked for " + p.getName() + " (boss: " + bossName + ")");

        return true;
    }
}

// Facade
class BossFightFacade {
    private LevelSystem levelSystem;
    private InventorySystem inventorySystem;
    private EnergySystem energySystem;
    private QuestSystem questSystem;

    public BossFightFacade() {
        levelSystem = new LevelSystem();
        inventorySystem = new InventorySystem();
        energySystem = new EnergySystem();
        questSystem = new QuestSystem();
    }

    public boolean canStartBossFight(Player player, String bossName, int requiredLevel, String requiredItem, int energyCost) {
        System.out.println(player.getName() + " tries to start boss fight: " + bossName);

        if (!questSystem.isBossUnlocked(player, bossName)) {
            return false;
        }
        if (!levelSystem.hasRequiredLevel(player, requiredLevel)) {
            return false;
        }
        if (!inventorySystem.hasRequiredItem(player, requiredItem)) {
            return false;
        }
        if (!energySystem.hasEnoughEnergy(player, energyCost)) {
            return false;
        }

        return true;
    }

    public void startBossFight(Player player, String bossName, int energyCost) {
        player.consumeEnergy(energyCost);
        System.out.println("Boss fight started! " + player.getName() + " enters the arena against " + bossName);
        System.out.println("Energy consumed: " + energyCost + " | Remaining energy: " + player.getEnergy());
    }
}

// Main class
public class FacadeBossFightDemo {
    public static void main(String[] args) {

        Player player = new Player("Gizem", 12, 50);

        BossFightFacade bossFight = new BossFightFacade();

        String bossName = "Cat Woman";
        int requiredLevel = 10;
        String requiredItem = "Boss Key";
        int energyCost = 30;

        boolean eligible = bossFight.canStartBossFight(
                player,
                bossName,
                requiredLevel,
                requiredItem,
                energyCost
        );

        System.out.println(player.getName() + " has been " +
                (eligible ? "approved to fight!" : "rejected."));

        if (eligible) {
            bossFight.startBossFight(player, bossName, energyCost);
        }
    }
}