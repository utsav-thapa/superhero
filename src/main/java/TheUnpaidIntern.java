public class TheUnpaidIntern implements ArenaHero,CanMove,HasPower,HasMorality{

//P.S. Here are some hints about superhero movement 🤫
//closer distance improves accuracy
//farther distance reduces accuracy
//moving sideways relative to the opponent gives a dodge bonus
//moving directly away gives safety but may reduce attack accuracy
//being near a wall/corner reduces dodge
//standing still is predictable and easier to hit
//try advanced movements like circling your opponent ↺↻ 🏃🏽‍♂️💨 (also try adding emojis to your arena messages)

    @Override
    public String getName() {
        return "The Unpaid Intern 👨‍💻";
    }

    @Override
    public String getCatchphrase() {
        return "You can’t put a price on justice… apparently.";
    }

    @Override
    public int getMaxHealth() {
        return 95;
    }

    @Override
    public int getEvilness() {
        return 0;
    }

    @Override
    public String getMotto() {
        return "This better look good on my resume.";
    }

    @Override
    public PowerMove usePower() {
        return new PowerMove("Pushes to main at 4:59 PM on a Friday",70,95);

    }

    @Override
    public Movement move(MoveContext context) {

        // walls are where unpaid interns go to question life choices
        if (context.isNearWall()) {
            return Movement.towardOpponent(
                    context,
                    "🧱😨 backs away from the wall like it just assigned unpaid overtime");
        }

        // emotional support retreat
        if (context.getMyHealth() < 25) {
            return Movement.awayFromOpponent(
                    context,
                    "💻😭 retreats to update LinkedIn and pretend this was a learning experience");
        }

        // sprint toward chaos
        if (context.getDistanceToOpponent() > 6) {
            return Movement.towardOpponent(
                    context,
                    "🏃‍♂️ charges forward fueled entirely by caffeine ☕");
        }

        // awkward side-step panic movement
        if (context.getDistanceToOpponent() <= 2) {

            int sideX = -context.getDeltaYToOpponent();
            int sideY = context.getDeltaXToOpponent();

            return Movement.by(
                    sideX,
                    sideY,
                    " slides sideways with the confidence of copied Stack Overflow code");
        }

        // default behavior
        return Movement.stay(
                "🧍‍♂️ stands completely still waiting for the build to finish");
    }
}
