package upgrade;

public class UpgradeModel {

    protected enum Type{
        DOUBLE_POINTS, INVULNERABILITY, EXTRA_LIFE, GHOST_FREEZE, SPEED_UP
    }

    private long duration;
    private int x, y;
    private Type type;

    public UpgradeModel(int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.duration = 6000;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Type getType() {
        return type;
    }

    public long getDuration() {
        return duration;
    }

}
