/**
 * The most basic contract for every hero or villain in the arena.
 *
 * This interface tells the arena the few facts that every contestant must provide.
 * It does NOT let a contestant directly attack or damage another contestant.
 */
public interface ArenaHero {
    String getName();
    String getCatchphrase();
    int getMaxHealth();
}
