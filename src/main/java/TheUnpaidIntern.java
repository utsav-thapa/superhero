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

        // unpaid interns trapped near walls immediately panic
        if (context.isNearWall()) {
            return Movement.towardOpponent(
                    context,
                    "🧱😨 escapes the corner after realizing dodge bonuses do not apply to emotional damage");
        }

        // tactical retreat when health gets dangerously low
        if (context.getMyHealth() < 25) {
            return Movement.awayFromOpponent(
                    context,
                    "🏃‍♂️💻 retreats to pretend this battle is 'still in testing'");
        }

        // if too far away, move closer for better accuracy
        if (context.getDistanceToOpponent() > 5) {
            return Movement.towardOpponent(
                    context,
                    "🎯☕ rushes closer to improve hit accuracy and job security");
        }

        // close-range side strafing for dodge bonus
        if (context.getDistanceToOpponent() <= 3) {

            // sideways/circling movement
            int sideX = -context.getDeltaYToOpponent();
            int sideY = context.getDeltaXToOpponent();

            return Movement.by(
                    sideX,
                    sideY,
                    "🌀🏃 circles the opponent like a confused unpaid Roomba intern");
        }

        // mid-range unpredictable movement
        int zigzagX = context.getRandomXChange();
        int zigzagY = context.getRandomYChange();

        return Movement.by(
                zigzagX,
                zigzagY,
                "🎲💨 moves unpredictably like code written at 2 AM before a deadline");
    }
}
