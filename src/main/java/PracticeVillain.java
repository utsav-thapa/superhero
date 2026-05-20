/**
 * A simple opponent for local testing.
 */
public class PracticeVillain implements ArenaHero, CanMove, HasPower, HasMorality {
    @Override
    public String getName() {
        return "Practice Villain";
    }

    @Override
    public String getCatchphrase() {
        return "I am only here for testing purposes!";
    }

    @Override
    public int getMaxHealth() {
        return 105;
    }

    @Override
    public Movement move(MoveContext context) {
        if (context.getDistanceToOpponent() > 4) {
            return Movement.towardOpponent(context, "lurks closer behind a cloud of purple smoke");
        }

        return Movement.by(0, 2, "slides sideways through purple smoke");
    }

    @Override
    public PowerMove usePower() {
        return new PowerMove("Dramatic Doom Beam", 42, 72);
    }

    @Override
    public int getEvilness() {
        return 75;
    }

    @Override
    public String getMotto() {
        return "Every hero needs a test case.";
    }
}
