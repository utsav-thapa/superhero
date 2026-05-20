/**
 * Rename this file and class before submitting if your instructor asks for a unique class name.
 *
 * Your class must implement all four interfaces:
 * - ArenaHero
 * - CanMove
 * - HasPower
 * - HasMorality
 */
public class StarterHero implements ArenaHero, CanMove, HasPower, HasMorality {
    @Override
    public String getName() {
        return "Starter Hero";
    }

    @Override
    public String getCatchphrase() {
        return "Interfaces assemble!";
    }

    @Override
    public int getMaxHealth() {
        return 110;
    }

    @Override
    public Movement move(MoveContext context) {
        if (context.getMyHealth() < 40) {
            return Movement.awayFromOpponent(context, "skates backward to make space");
        }

        if (context.getDistanceToOpponent() > 5) {
            return Movement.towardOpponent(context, "skates closer on lightning boots");
        }

        return Movement.stay("plants their feet and watches for an opening");
    }

    @Override
    public PowerMove usePower() {
        return new PowerMove("Thunder Jab", 45, 70);
    }

    @Override
    public int getEvilness() {
        return 15;
    }

    @Override
    public String getMotto() {
        return "Win with style, lose with honor.";
    }
}
