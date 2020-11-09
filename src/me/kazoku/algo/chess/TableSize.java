package me.kazoku.algo.chess;

public class TableSize {
    private final long width;
    private final long height;

    public static final TableSize DEFAULT = new TableSize();

    public TableSize(long width, long height) {
        this.width = width;
        this.height = height;
    }

    public TableSize(long edge) {
        this(edge, edge);
    }

    private TableSize() {
        this(8);
    }

    public long getHeight() {
        return height;
    }

    public long getWidth() {
        return width;
    }
}
