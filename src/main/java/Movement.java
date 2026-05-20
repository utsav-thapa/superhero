/**
 * A movement request returned by the CanMove interface.
 *
 * The hero asks to move by x/y amounts. The arena clamps the request to the
 * maximum move distance and keeps the hero inside the arena.
 */
public class Movement {
    private String description;
    private int xChange;
    private int yChange;

    public Movement(String description, int xChange, int yChange) {
        this.description = description;
        this.xChange = xChange;
        this.yChange = yChange;
    }

    public static Movement by(int xChange, int yChange, String description) {
        return new Movement(description, xChange, yChange);
    }

    public static Movement stay(String description) {
        return new Movement(description, 0, 0);
    }

    public static Movement towardOpponent(MoveContext context, String description) {
        return by(context.getDeltaXToOpponent(), context.getDeltaYToOpponent(), description);
    }

    public static Movement awayFromOpponent(MoveContext context, String description) {
        return by(-context.getDeltaXToOpponent(), -context.getDeltaYToOpponent(), description);
    }

    public static Movement random(MoveContext context) {
        return by(
                context.getRandomXChange(),
                context.getRandomYChange(),
                "looks for an opening");
    }

    public String getDescription() {
        return description;
    }

    public int getXChange() {
        return xChange;
    }

    public int getYChange() {
        return yChange;
    }

    @Override
    public String toString() {
        return description + " (x " + signed(xChange) + ", y " + signed(yChange) + ")";
    }

    private String signed(int value) {
        if (value >= 0) {
            return "+" + value;
        }
        return String.valueOf(value);
    }
}
