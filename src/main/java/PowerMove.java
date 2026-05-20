/**
 * A simple value object returned by the HasPower interface.
 *
 * Damage and accuracy are requests. The arena may cap or adjust them.
 */
public class PowerMove {
    private String name;
    private int damage;
    private int accuracy;

    public PowerMove(String name, int damage, int accuracy) {
        this.name = name;
        this.damage = damage;
        this.accuracy = accuracy;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getAccuracy() {
        return accuracy;
    }

    @Override
    public String toString() {
        return name + " (damage " + damage + ", accuracy " + accuracy + ")";
    }
}
