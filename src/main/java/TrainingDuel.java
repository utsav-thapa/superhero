import java.util.Random;

/**
 * A small practice engine so students can test their characters locally.
 *
 * Students do not need to understand every line of this file yet. The important
 * idea is that the engine talks to contestants through interfaces.
 */
public class TrainingDuel {
    private static final int MAX_ROUNDS = 6;

    private Random random;

    public TrainingDuel(Random random) {
        this.random = random;
    }

    public void run(ArenaHero heroA, ArenaHero heroB) {
        if (!isBattleReady(heroA) || !isBattleReady(heroB)) {
            System.out.println("Both contestants must implement ArenaHero, CanMove, HasPower, and HasMorality.");
            return;
        }

        int healthA = HeroRules.legalHealth(heroA.getMaxHealth());
        int healthB = HeroRules.legalHealth(heroB.getMaxHealth());
        int xA = 2;
        int yA = HeroRules.ARENA_HEIGHT / 2;
        int xB = HeroRules.ARENA_WIDTH - 3;
        int yB = HeroRules.ARENA_HEIGHT / 2;

        System.out.println();
        System.out.println("=== TRAINING DUEL ===");
        System.out.println(heroA.getName() + " vs. " + heroB.getName());
        System.out.println(heroA.getName() + ": \"" + heroA.getCatchphrase() + "\"");
        System.out.println(heroB.getName() + ": \"" + heroB.getCatchphrase() + "\"");
        System.out.println(heroA.getName() + " starts at (" + xA + ", " + yA + ").");
        System.out.println(heroB.getName() + " starts at (" + xB + ", " + yB + ").");

        for (int round = 1; round <= MAX_ROUNDS && healthA > 0 && healthB > 0; round++) {
            System.out.println();
            System.out.println("Round " + round);

            int oldXA = xA;
            int oldYA = yA;
            int oldXB = xB;
            int oldYB = yB;

            Movement moveA = chooseMove(heroA, xA, yA, xB, yB, healthA, healthB, round);
            Movement moveB = chooseMove(heroB, xB, yB, xA, yA, healthB, healthA, round);

            moveA = legalArenaMovement(moveA, xA, yA);
            moveB = legalArenaMovement(moveB, xB, yB);

            xA += moveA.getXChange();
            yA += moveA.getYChange();
            xB += moveB.getXChange();
            yB += moveB.getYChange();

            System.out.println(heroA.getName() + " moves to (" + xA + ", " + yA + "): "
                    + moveA.getDescription() + ".");
            System.out.println(heroB.getName() + " moves to (" + xB + ", " + yB + "): "
                    + moveB.getDescription() + ".");

            int dodgeA = movementDodgeBonus(moveA, oldXA, oldYA, oldXB, oldYB, xA, yA);
            int dodgeB = movementDodgeBonus(moveB, oldXB, oldYB, oldXA, oldYA, xB, yB);
            int initiativeA = movementDistance(moveA) + random.nextInt(21);
            int initiativeB = movementDistance(moveB) + random.nextInt(21);

            if (initiativeA >= initiativeB) {
                healthB = attack(heroA, heroB, moveB, dodgeB, healthB, xA, yA, xB, yB);
                if (healthB > 0) {
                    healthA = attack(heroB, heroA, moveA, dodgeA, healthA, xB, yB, xA, yA);
                }
            } else {
                healthA = attack(heroB, heroA, moveA, dodgeA, healthA, xB, yB, xA, yA);
                if (healthA > 0) {
                    healthB = attack(heroA, heroB, moveB, dodgeB, healthB, xA, yA, xB, yB);
                }
            }

            System.out.println(heroA.getName() + " health: " + healthA);
            System.out.println(heroB.getName() + " health: " + healthB);
        }

        System.out.println();
        if (healthA > healthB) {
            System.out.println("Training winner: " + heroA.getName());
        } else if (healthB > healthA) {
            System.out.println("Training winner: " + heroB.getName());
        } else {
            System.out.println("Training result: draw");
        }
    }

    private boolean isBattleReady(ArenaHero hero) {
        return hero instanceof CanMove
                && hero instanceof HasPower
                && hero instanceof HasMorality;
    }

    private Movement chooseMove(ArenaHero hero, int myX, int myY, int opponentX, int opponentY,
                                int myHealth, int opponentHealth, int round) {
        try {
            CanMove mover = (CanMove) hero;
            MoveContext context = new MoveContext(
                    myX,
                    myY,
                    opponentX,
                    opponentY,
                    myHealth,
                    opponentHealth,
                    HeroRules.ARENA_WIDTH,
                    HeroRules.ARENA_HEIGHT,
                    HeroRules.MAX_MOVE_DISTANCE,
                    round,
                    randomMoveComponent(),
                    randomMoveComponent());

            return HeroRules.legalMovement(mover.move(context));
        } catch (Exception ex) {
            return Movement.stay("freezes after a movement error");
        }
    }

    private Movement legalArenaMovement(Movement requested, int x, int y) {
        Movement limited = limitToMaxDistance(requested);
        int newX = HeroRules.clamp(x + limited.getXChange(), 0, HeroRules.ARENA_WIDTH - 1);
        int newY = HeroRules.clamp(y + limited.getYChange(), 0, HeroRules.ARENA_HEIGHT - 1);
        return new Movement(limited.getDescription(), newX - x, newY - y);
    }

    private Movement limitToMaxDistance(Movement requested) {
        int xChange = requested.getXChange();
        int yChange = requested.getYChange();
        double distance = distance(0, 0, xChange, yChange);

        if (distance > HeroRules.MAX_MOVE_DISTANCE) {
            double scale = HeroRules.MAX_MOVE_DISTANCE / distance;
            xChange = (int) Math.round(xChange * scale);
            yChange = (int) Math.round(yChange * scale);
        }

        while (distance(0, 0, xChange, yChange) > HeroRules.MAX_MOVE_DISTANCE) {
            if (Math.abs(xChange) >= Math.abs(yChange)) {
                xChange -= signOf(xChange);
            } else {
                yChange -= signOf(yChange);
            }
        }

        return new Movement(requested.getDescription(), xChange, yChange);
    }

    private int movementDodgeBonus(Movement movement, int oldX, int oldY,
                                   int oldOpponentX, int oldOpponentY, int newX, int newY) {
        double movedDistance = distance(0, 0, movement.getXChange(), movement.getYChange());
        int dodgeBonus = 0;

        if (movedDistance > 0) {
            dodgeBonus = Math.min(6, (int) Math.round(movedDistance * 1.5));

            int xToOpponent = oldOpponentX - oldX;
            int yToOpponent = oldOpponentY - oldY;
            double opponentDistance = distance(0, 0, xToOpponent, yToOpponent);
            double product = movedDistance * opponentDistance;

            if (product > 0) {
                double alignment = (
                        movement.getXChange() * xToOpponent
                                + movement.getYChange() * yToOpponent)
                        / product;

                if (Math.abs(alignment) <= 0.35 && movedDistance >= 2) {
                    dodgeBonus += 7;
                } else if (alignment < -0.55) {
                    dodgeBonus += 3;
                }
            }
        }

        if (nearWall(newX, newY)) {
            dodgeBonus -= 4;
        }

        return HeroRules.clamp(dodgeBonus, 0, 15);
    }

    private int attack(ArenaHero attacker, ArenaHero defender, Movement defenderMove,
                       int defenderDodge, int defenderHealth,
                       int attackerX, int attackerY, int defenderX, int defenderY) {
        HasPower powered = (HasPower) attacker;
        HasMorality morality = (HasMorality) attacker;

        PowerMove power = HeroRules.legalPower(powered.usePower());
        int evilness = HeroRules.legalEvilness(morality.getEvilness());
        double distance = distance(attackerX, attackerY, defenderX, defenderY);
        int distancePenalty = 0;

        if (distance > 3) {
            distancePenalty = (int) Math.round((distance - 3) * 3);
        }

        int hitChance = power.getAccuracy()
                - defenderDodge
                - distancePenalty
                + random.nextInt(21)
                - 10;

        if (distance <= 2) {
            hitChance += 6;
        } else if (distance <= 5) {
            hitChance += 2;
        }

        hitChance = HeroRules.clamp(hitChance, 10, 90);

        System.out.println(attacker.getName() + " uses " + power.getName()
                + " from " + String.format("%.1f", distance) + " squares away.");

        if (random.nextInt(100) >= hitChance) {
            System.out.println("  Miss! " + defender.getName() + " " + defenderMove.getDescription() + ".");
            return defenderHealth;
        }

        int damage = power.getDamage() + random.nextInt(11) - 5;

        if (distance > 8) {
            damage -= (int) Math.round((distance - 8) * 2);
            System.out.println("  Long range weakens the hit.");
        }

        if (evilness >= 75) {
            damage += 4;
            System.out.println("  Villain mode adds reckless damage.");
        } else if (evilness <= 25 && defenderHealth < 30) {
            damage -= 4;
            System.out.println("  Heroic mercy reduces the damage a little.");
        }

        if (damage < 1) {
            damage = 1;
        }

        int newHealth = defenderHealth - damage;
        if (newHealth < 0) {
            newHealth = 0;
        }

        System.out.println("  Hit for " + damage + " damage.");
        return newHealth;
    }

    private int randomMoveComponent() {
        return random.nextInt(HeroRules.MAX_MOVE_DISTANCE * 2 + 1)
                - HeroRules.MAX_MOVE_DISTANCE;
    }

    private int movementDistance(Movement movement) {
        return (int) Math.round(distance(0, 0, movement.getXChange(), movement.getYChange()));
    }

    private boolean nearWall(int x, int y) {
        return x <= 1 || y <= 1 || x >= HeroRules.ARENA_WIDTH - 2 || y >= HeroRules.ARENA_HEIGHT - 2;
    }

    private double distance(int x1, int y1, int x2, int y2) {
        int xDifference = x2 - x1;
        int yDifference = y2 - y1;
        return Math.sqrt(xDifference * xDifference + yDifference * yDifference);
    }

    private int signOf(int value) {
        if (value > 0) {
            return 1;
        }
        if (value < 0) {
            return -1;
        }
        return 0;
    }
}
