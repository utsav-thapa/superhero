/**
 * A read-only snapshot of the arena when a hero chooses a move.
 *
 * Coordinates start at 0. X increases to the right, and Y increases downward.
 */
public class MoveContext {
    private int myX;
    private int myY;
    private int opponentX;
    private int opponentY;
    private int myHealth;
    private int opponentHealth;
    private int arenaWidth;
    private int arenaHeight;
    private int maxMoveDistance;
    private int roundNumber;
    private int randomXChange;
    private int randomYChange;

    public MoveContext(int myX, int myY, int opponentX, int opponentY,
                       int myHealth, int opponentHealth, int arenaWidth,
                       int arenaHeight, int maxMoveDistance, int roundNumber,
                       int randomXChange, int randomYChange) {
        this.myX = myX;
        this.myY = myY;
        this.opponentX = opponentX;
        this.opponentY = opponentY;
        this.myHealth = myHealth;
        this.opponentHealth = opponentHealth;
        this.arenaWidth = arenaWidth;
        this.arenaHeight = arenaHeight;
        this.maxMoveDistance = maxMoveDistance;
        this.roundNumber = roundNumber;
        this.randomXChange = randomXChange;
        this.randomYChange = randomYChange;
    }

    public static MoveContext practice() {
        return new MoveContext(4, 10, 15, 10, 110, 105,
                HeroRules.ARENA_WIDTH, HeroRules.ARENA_HEIGHT,
                HeroRules.MAX_MOVE_DISTANCE, 1, 2, -1);
    }

    public int getMyX() {
        return myX;
    }

    public int getMyY() {
        return myY;
    }

    public int getOpponentX() {
        return opponentX;
    }

    public int getOpponentY() {
        return opponentY;
    }

    public int getDeltaXToOpponent() {
        return opponentX - myX;
    }

    public int getDeltaYToOpponent() {
        return opponentY - myY;
    }

    public double getDistanceToOpponent() {
        return Math.sqrt(
                getDeltaXToOpponent() * getDeltaXToOpponent()
                        + getDeltaYToOpponent() * getDeltaYToOpponent());
    }

    public int getMyHealth() {
        return myHealth;
    }

    public int getOpponentHealth() {
        return opponentHealth;
    }

    public int getArenaWidth() {
        return arenaWidth;
    }

    public int getArenaHeight() {
        return arenaHeight;
    }

    public int getMaxMoveDistance() {
        return maxMoveDistance;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public boolean isNearWall() {
        return myX <= 1 || myY <= 1 || myX >= arenaWidth - 2 || myY >= arenaHeight - 2;
    }

    public int getRandomXChange() {
        return randomXChange;
    }

    public int getRandomYChange() {
        return randomYChange;
    }
}
