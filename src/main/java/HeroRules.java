/**
 * Shared arena rules.
 *
 * Students may read this file, but should not modify it for their submitted hero.
 */
public final class HeroRules {
    public static final int MIN_HEALTH = 80;
    public static final int MAX_HEALTH = 140;

    public static final int MIN_DAMAGE = 10;
    public static final int MAX_DAMAGE = 80;

    public static final int MIN_ACCURACY = 40;
    public static final int MAX_ACCURACY = 95;

    public static final int MIN_EVILNESS = 0;
    public static final int MAX_EVILNESS = 100;

    public static final int ARENA_WIDTH = 20;
    public static final int ARENA_HEIGHT = 20;
    public static final int MAX_MOVE_DISTANCE = 4;

    /**
     * Build budget formula:
     * health + damage + accuracy must be <= this value.
     *
     * Movement is no longer bought with points. It is decided with code each turn.
     */
    public static final int BUILD_BUDGET = 260;

    private HeroRules() {
        // This class only contains static helper methods.
    }

    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static int legalHealth(int health) {
        return clamp(health, MIN_HEALTH, MAX_HEALTH);
    }

    public static int legalDamage(int damage) {
        return clamp(damage, MIN_DAMAGE, MAX_DAMAGE);
    }

    public static int legalAccuracy(int accuracy) {
        return clamp(accuracy, MIN_ACCURACY, MAX_ACCURACY);
    }

    public static int legalEvilness(int evilness) {
        return clamp(evilness, MIN_EVILNESS, MAX_EVILNESS);
    }

    public static Movement legalMovement(Movement movement) {
        if (movement == null) {
            return Movement.stay("stands completely still");
        }

        String description = safeText(movement.getDescription(), "moves mysteriously");
        return new Movement(description, movement.getXChange(), movement.getYChange());
    }

    public static PowerMove legalPower(PowerMove power) {
        if (power == null) {
            return new PowerMove("confused sparkle", MIN_DAMAGE, MIN_ACCURACY);
        }

        String name = safeText(power.getName(), "unnamed power");
        int damage = legalDamage(power.getDamage());
        int accuracy = legalAccuracy(power.getAccuracy());
        return new PowerMove(name, damage, accuracy);
    }

    public static int buildCost(ArenaHero hero, HasPower powered) {
        int health = legalHealth(hero.getMaxHealth());
        PowerMove power = legalPower(powered.usePower());

        return health
                + power.getDamage()
                + power.getAccuracy();
    }

    public static boolean isWithinBudget(ArenaHero hero, HasPower powered) {
        return buildCost(hero, powered) <= BUILD_BUDGET;
    }

    public static void printBuildReport(ArenaHero hero, CanMove mover, HasPower powered, HasMorality morality) {
        int health = legalHealth(hero.getMaxHealth());
        PowerMove power = legalPower(powered.usePower());
        Movement movement = previewMovement(mover);
        int evilness = legalEvilness(morality.getEvilness());
        int cost = buildCost(hero, powered);

        System.out.println("=== HERO BUILD REPORT ===");
        System.out.println("Name: " + safeText(hero.getName(), "Unnamed Hero"));
        System.out.println("Catchphrase: " + safeText(hero.getCatchphrase(), "No catchphrase"));
        System.out.println("Motto: " + safeText(morality.getMotto(), "No motto"));
        System.out.println("Health: " + health);
        System.out.println("Power: " + power);
        System.out.println("Opening movement: " + movement);
        System.out.println("Arena size: " + ARENA_WIDTH + " x " + ARENA_HEIGHT);
        System.out.println("Max move distance: " + MAX_MOVE_DISTANCE);
        System.out.println("Evilness: " + evilness + " / 100");
        System.out.println("Build cost: " + cost + " / " + BUILD_BUDGET);

        if (cost <= BUILD_BUDGET) {
            System.out.println("Status: LEGAL BUILD");
        } else {
            System.out.println("Status: OVER BUDGET - the tournament arena will apply a penalty");
        }
    }

    public static Movement previewMovement(CanMove mover) {
        try {
            return limitMovementDistance(legalMovement(mover.move(MoveContext.practice())));
        } catch (Exception ex) {
            return Movement.stay("has a movement error during the build preview");
        }
    }

    public static Movement limitMovementDistance(Movement movement) {
        int xChange = movement.getXChange();
        int yChange = movement.getYChange();
        double distance = Math.sqrt(xChange * xChange + yChange * yChange);

        if (distance > MAX_MOVE_DISTANCE) {
            double scale = MAX_MOVE_DISTANCE / distance;
            xChange = (int) Math.round(xChange * scale);
            yChange = (int) Math.round(yChange * scale);
        }

        while (Math.sqrt(xChange * xChange + yChange * yChange) > MAX_MOVE_DISTANCE) {
            if (Math.abs(xChange) >= Math.abs(yChange)) {
                xChange -= signOf(xChange);
            } else {
                yChange -= signOf(yChange);
            }
        }

        return new Movement(movement.getDescription(), xChange, yChange);
    }

    private static int signOf(int value) {
        if (value > 0) {
            return 1;
        }
        if (value < 0) {
            return -1;
        }
        return 0;
    }

    public static String safeText(String text, String fallback) {
        if (text == null) {
            return fallback;
        }

        String trimmed = text.trim();
        if (trimmed.length() == 0) {
            return fallback;
        }

        if (trimmed.length() > 60) {
            return trimmed.substring(0, 60);
        }

        return trimmed;
    }
}
