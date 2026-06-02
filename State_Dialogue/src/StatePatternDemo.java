import java.util.Scanner;

interface DialogueState {
    void enter();
    void handleInput(String input);
    void exit();
}

class DialogueManager {
    private DialogueState currentState;

    public void changeState(DialogueState newState) {
        if (currentState != null) {
            currentState.exit();
        }

        currentState = newState;
        currentState.enter();
    }

    public void handleInput(String input) {
        if (currentState != null) {
            currentState.handleInput(input);
        }
    }
}

class IntroState implements DialogueState {
    private DialogueManager manager;

    public IntroState(DialogueManager manager) {
        this.manager = manager;
    }

    public void enter() {
        System.out.println("Elder: Stop, traveler. Our village is in danger.");
        System.out.println("Press ENTER to START");
    }

    public void handleInput(String input) {
        manager.changeState(new FirstChoiceState(manager));
    }

    public void exit() {
        System.out.println();
    }
}

class FirstChoiceState implements DialogueState {
    private DialogueManager manager;

    public FirstChoiceState(DialogueManager manager) {
        this.manager = manager;
    }

    public void enter() {
        System.out.println("Elder : A magical artifact has been stolen from the village.");
        System.out.println("Elder: Without it, the village will be attacked tonight.");
        System.out.println();
        System.out.println("Choose an option:");
        System.out.println("1 - I will help you.");
        System.out.println("2 - This is not my problem.");
        System.out.println("3 - Tell me more about the artifact.");
    }

    public void handleInput(String input) {
        switch (input) {
            case "1":
                manager.changeState(new LibraryChoiceState(manager));
                break;
            case "2":
                manager.changeState(new BadEndingState(manager));
                break;
            case "3":
                manager.changeState(new MoreInfoState(manager));
                break;
            default:
                System.out.println("Invalid choice. Choose 1, 2, or 3.");
        }
    }

    public void exit() {
        System.out.println();
    }
}

class MoreInfoState implements DialogueState {
    private DialogueManager manager;

    public MoreInfoState(DialogueManager manager) {
        this.manager = manager;
    }

    public void enter() {
        System.out.println("Elder: The artifact protects us from monsters in the forest.");
        System.out.println("Elder: It was last seen inside the old library.");
        System.out.println();
        System.out.println("Choose an option:");
        System.out.println("1 - I will go to the old library.");
        System.out.println("2 - I still do not want to help.");
        System.out.println("3 - Ask who stole the artifact.");
    }

    public void handleInput(String input) {
        switch (input) {
            case "1":
                manager.changeState(new LibraryChoiceState(manager));
                break;
            case "2":
                manager.changeState(new BadEndingState(manager));
                break;
            case "3":
                System.out.println("Elder: A masked thief was seen running toward the library.");
                System.out.println("Elder: Be careful. This may be a trap.");
                System.out.println();
                System.out.println("Choose again:");
                System.out.println("1 - I will go to the old library.");
                System.out.println("2 - I still do not want to help.");
                break;
            default:
                System.out.println("Invalid choice. Choose 1, 2, or 3.");
        }
    }

    public void exit() {
        System.out.println();
    }
}

class LibraryChoiceState implements DialogueState {
    private DialogueManager manager;

    public LibraryChoiceState(DialogueManager manager) {
        this.manager = manager;
    }

    public void enter() {
        System.out.println("You enter the old library.");
        System.out.println("The room is dark, dusty, and silent.");
        System.out.println();
        System.out.println("Choose an action:");
        System.out.println("1 - Search the broken bookshelf.");
        System.out.println("2 - Open the locked chest.");
        System.out.println("3 - Follow the strange footprints.");
    }

    public void handleInput(String input) {
        switch (input) {
            case "1":
                manager.changeState(new QuestCompletedState(manager));
                break;
            case "2":
                manager.changeState(new KeyNeededState(manager));
                break;
            case "3":
                manager.changeState(new TrapState(manager));
                break;
            default:
                System.out.println("Invalid choice. Choose 1, 2, or 3.");
        }
    }

    public void exit() {
        System.out.println();
    }
}

class KeyNeededState implements DialogueState {
    private DialogueManager manager;

    public KeyNeededState(DialogueManager manager) {
        this.manager = manager;
    }

    public void enter() {
        System.out.println("The chest is locked.");
        System.out.println("You notice a small golden key under a carpet.");
        System.out.println();
        System.out.println("Choose an action:");
        System.out.println("1 - Take the key and open the chest.");
        System.out.println("2 - Ignore the chest and search the bookshelf.");
        System.out.println("3 - Leave the library.");
    }

    public void handleInput(String input) {
        switch (input) {
            case "1":
                manager.changeState(new ChestRewardState(manager));
                break;
            case "2":
                manager.changeState(new QuestCompletedState(manager));
                break;
            case "3":
                manager.changeState(new BadEndingState(manager));
                break;
            default:
                System.out.println("Invalid choice. Choose 1, 2, or 3.");
        }
    }

    public void exit() {
        System.out.println();
    }
}

class ChestRewardState implements DialogueState {
    private DialogueManager manager;

    public ChestRewardState(DialogueManager manager) {
        this.manager = manager;
    }

    public void enter() {
        System.out.println("You open the chest.");
        System.out.println("Inside, you find a silver sword and a note.");
        System.out.println("The note says: 'The artifact is hidden behind the broken bookshelf.'");
        System.out.println();
        System.out.println("You use the clue and find the artifact.");
        System.out.println("Quest Completed!");
        System.out.println("Reward: +700 Gold");
        System.out.println("Reward: Silver Sword");
        System.out.println();
        System.out.println("Choose an option:");
        System.out.println("1 - Play Again");
        System.out.println("2 - Exit");
    }

    public void handleInput(String input) {
        switch (input) {
            case "1":
                manager.changeState(new IntroState(manager));
                break;
            case "2":
                System.out.println("Thanks for playing!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Choose 1 or 2.");
        }
    }

    public void exit() {
        System.out.println();
    }
}

class QuestCompletedState implements DialogueState {
    private DialogueManager manager;

    public QuestCompletedState(DialogueManager manager) {
        this.manager = manager;
    }

    public void enter() {
        System.out.println("You search the broken bookshelf.");
        System.out.println("Behind the books, you find the stolen artifact.");
        System.out.println();
        System.out.println("Elder: You saved the village!");
        System.out.println("Quest Complleted!");
        System.out.println("Reward: +500 Gold");
        System.out.println("Reward: +100 XP");
        System.out.println();
        System.out.println("Choose an option:");
        System.out.println("1 - Play Again");
        System.out.println("2 - Exit");
    }

    public void handleInput(String input) {
        switch (input) {
            case "1":
                manager.changeState(new IntroState(manager));
                break;
            case "2":
                System.out.println("Thanks for playing!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Choose 1 or 2.");
        }
    }

    public void exit() {
        System.out.println();
    }
}

class TrapState implements DialogueState {
    private DialogueManager manager;

    public TrapState(DialogueManager manager) {
        this.manager = manager;
    }

    public void enter() {
        System.out.println("You follow the strange footprints.");
        System.out.println("Suddenly, the floor collapses under you.");
        System.out.println("The masked thief escapes with the artifact.");
        System.out.println();
        System.out.println("Quest Failed.");
        System.out.println("Game Over.");
        System.out.println();
        System.out.println("Choose an option:");
        System.out.println("1 - Restart the game");
        System.out.println("2 - Exit");
    }

    public void handleInput(String input) {
        switch (input) {
            case "1":
                manager.changeState(new IntroState(manager));
                break;
            case "2":
                System.out.println("Thanks for playing!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Choose 1 or 2.");
        }
    }

    public void exit() {
        System.out.println();
    }
}

class BadEndingState implements DialogueState {
    private DialogueManager manager;

    public BadEndingState(DialogueManager manager) {
        this.manager = manager;
    }

    public void enter() {
        System.out.println("You decide not to help the village.");
        System.out.println("That night, monsters attack the village.");
        System.out.println();
        System.out.println("Bad Ending.");
        System.out.println("Game Over.");
        System.out.println();
        System.out.println("Choose an option:");
        System.out.println("1 - Restart the game");
        System.out.println("2 - Exit");
    }

    public void handleInput(String input) {
        switch (input) {
            case "1":
                manager.changeState(new IntroState(manager));
                break;
            case "2":
                System.out.println("Thanks for playing!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Choose 1 or 2.");
        }
    }

    public void exit() {
        System.out.println();
    }
}

public class StatePatternDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DialogueManager manager = new DialogueManager();
        manager.changeState(new IntroState(manager));

        while (true) {
            String input = scanner.nextLine();
            manager.handleInput(input);
        }
    }
}