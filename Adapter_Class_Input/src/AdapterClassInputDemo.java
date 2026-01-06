//Target interface
interface GamepadInput {
    float getHorizontal();   // -1..+1  (Left stick X)
    float getVertical();     // -1..+1  (Left stick Y)
    boolean isJumpPressed(); // X button
}


//Adaptee class
class KeyboardInput {

    private boolean left, right, up, down, space;

    public void setKeyState(boolean left, boolean right, boolean up, boolean down, boolean space) {
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        this.space = space;
    }

    public boolean isKeyDown(String key) {
        return switch (key) {
            case "A" -> left;
            case "D" -> right;
            case "W" -> up;
            case "S" -> down;
            case "SPACE" -> space;
            default -> false;
        };
    }
}

//  A/D to Horizontal (-1/+1)
//  W/S to Vertical (+1/-1)
//  SPACE to Jump button
class KeyboardToGamepadAdapter extends KeyboardInput implements GamepadInput {

    @Override
    public float getHorizontal() {
        boolean a = isKeyDown("A");
        boolean d = isKeyDown("D");

        if (a && !d) return -1f;
        if (d && !a) return 1f;
        return 0f;
    }

    @Override
    public float getVertical() {
        boolean w = isKeyDown("W");
        boolean s = isKeyDown("S");

        if (w && !s) return 1f;
        if (s && !w) return -1f;
        return 0f;
    }

    @Override
    public boolean isJumpPressed() {
        return isKeyDown("SPACE");
    }
}

//Client class
class PlayerController {
    public void update(GamepadInput input) {
        float x = input.getHorizontal();
        float y = input.getVertical();
        boolean jump = input.isJumpPressed();

        System.out.println("Move: (" + x + ", " + y + ")");
        if (jump) System.out.println("Jump!");
    }
}


public class AdapterClassInputDemo {
    public static void main(String[] args) {


        KeyboardToGamepadAdapter input = new KeyboardToGamepadAdapter();

        PlayerController player = new PlayerController();


        input.setKeyState(false, true, true, false, false);
        player.update(input);

        input.setKeyState(true, false, false, false, false);
        player.update(input);

        input.setKeyState(false, false, true, false, true);
        player.update(input);

        input.setKeyState(false, false, false, false, false);
        player.update(input);
    }
}