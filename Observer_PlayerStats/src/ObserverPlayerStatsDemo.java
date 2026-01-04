import java.util.ArrayList;

//SUBJECT
abstract class PlayerStats {
    protected String _playerName;
    protected int _health;
    protected int _coins;

    protected ArrayList<PlayerObserver> observers = new ArrayList<>();

    public PlayerStats(String playerName, int health, int coins) {
        _playerName = playerName;
        _health = health;
        _coins = coins;
    }

    public void Attach(PlayerObserver observer) {
        observers.add(observer);
    }

    public void Detach(PlayerObserver observer) {
        for (int i = 0; i < observers.size(); i++) {
            if (observers.get(i).getId().equals(observer.getId())) {
                observers.remove(i);
                return;
            }
        }
    }

    public void Notify() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).Update(this);
        }
    }

    public String getPlayerName() { return _playerName; }
    public int getHealth() { return _health; }
    public int getCoins() { return _coins; }

    abstract public void setHealth(int value);
    abstract public void setCoins(int value);
}


// CONCRETE SUBJECT
class Hero extends PlayerStats {
    public Hero(String playerName, int health, int coins) {
        super(playerName, health, coins);
    }

    @Override
    public void setHealth(int value) {

        if (value < 0) value = 0;
        if (value > 100) value = 100;

        _health = value;
        Notify();
    }

    @Override
    public void setCoins(int value) {
        if (value < 0) value = 0;

        _coins = value;
        Notify();
    }

    public void TakeDamage(int damage) {
        setHealth(_health - damage);
    }

    public void CollectCoin(int amount) {
        setCoins(_coins + amount);
    }
}


// OBSERVER
interface PlayerObserver {
    void Update(PlayerStats stats);
    String getId();
}


// CONCRETE OBSERVERS
class HUD implements PlayerObserver {
    private final String _id;

    public HUD(String id) {
        _id = id;
    }

    @Override
    public void Update(PlayerStats stats) {
        System.out.println("[HUD] " + stats.getPlayerName() + " | HP: " + stats.getHealth() + " | Coins: " + stats.getCoins());
    }

    @Override
    public String getId() { return _id; }
}

class LowHealthAudio implements PlayerObserver {
    private final String _id;

    public LowHealthAudio(String id) {
        _id = id;
    }

    @Override
    public void Update(PlayerStats stats) {
        if (stats.getHealth() <= 25) {
            System.out.println("[AUDIO] Low health warning! (HP <= 25)");
        }
    }

    @Override
    public String getId() { return _id; }
}

class QuestTracker implements PlayerObserver {
    private final String _id;
    private final int targetCoins;

    public QuestTracker(String id, int targetCoins) {
        _id = id;
        this.targetCoins = targetCoins;
    }

    @Override
    public void Update(PlayerStats stats) {
        int coins = stats.getCoins();
        if (coins >= targetCoins) {
            System.out.println("[QUEST] Completed: Collect " + targetCoins + " coins!");
        } else {
            System.out.println("[QUEST] Progress: " + coins + "/" + targetCoins + " coins");
        }
    }

    @Override
    public String getId() { return _id; }
}

public class ObserverPlayerStatsDemo {
    public static void main(String[] args) {

        HUD hud = new HUD("hud_main");
        LowHealthAudio lowHpAudio = new LowHealthAudio("audio_lowhp");
        QuestTracker quest = new QuestTracker("quest_collect10", 10);

        Hero hero = new Hero("GizemHero", 100, 0);


        hero.Attach(hud);
        hero.Attach(lowHpAudio);
        hero.Attach(quest);

        hero.CollectCoin(3);
        hero.TakeDamage(30);
        hero.CollectCoin(4);
        hero.TakeDamage(50);
        hero.CollectCoin(3);

        System.out.println("Unregister the LowHealthAudio observer when audio is muted.");
        hero.Detach(lowHpAudio);

        hero.TakeDamage(5);
        hero.CollectCoin(1);

        hero.Detach(hud);
        hero.Detach(lowHpAudio);
        hero.Detach(quest);

        hero = null;
    }
}
