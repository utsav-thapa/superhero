/**
 * A capability interface for describing whether a contestant behaves more like
 * a hero, antihero, or villain.
 */
public interface HasMorality {
    /**
     * Return a value from 0 to 100.
     * 0 = very heroic, 50 = neutral/antihero, 100 = very villainous.
     */
    int getEvilness();

    String getMotto();
}
