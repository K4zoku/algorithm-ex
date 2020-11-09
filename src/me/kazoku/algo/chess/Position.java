package me.kazoku.algo.chess;

public class Position {
    private final long x;
    private final long y;

    public Position(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Position(long xy) {
        this(xy, xy);
    }

    public long getX() {
        return x;
    }

    public long getY () {
        return y;
    }

    public boolean equals(Position p) {
        return getX() == p.getX() && getY() == p.getY();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            return equals((Position) obj);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
