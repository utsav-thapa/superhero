/**
 * A capability interface for contestants that can move around the arena.
 */
public interface CanMove {
    default Movement move(MoveContext context) {
        return Movement.random(context);
    }
}
