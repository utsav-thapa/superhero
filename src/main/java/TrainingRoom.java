import java.util.Random;

/**
 * Run this file to test your hero before submitting.
 */
public class TrainingRoom {
    public static void main(String[] args) {
        // Change this line if you rename StarterHero to your own class name.
        ArenaHero myHero = new TheUnpaidIntern();

        if (!(myHero instanceof CanMove)
                || !(myHero instanceof HasPower)
                || !(myHero instanceof HasMorality)) {
            System.out.println("Your hero must implement CanMove, HasPower, and HasMorality.");
            return;
        }

        CanMove mover = (CanMove) myHero;
        HasPower powered = (HasPower) myHero;
        HasMorality morality = (HasMorality) myHero;

        HeroRules.printBuildReport(myHero, mover, powered, morality);

        PracticeVillain villain = new PracticeVillain();
        TrainingDuel duel = new TrainingDuel(new Random());
        duel.run(myHero, villain);
    }
}
