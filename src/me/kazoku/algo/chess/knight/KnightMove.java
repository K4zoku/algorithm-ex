package me.kazoku.algo.chess.knight;

import me.kazoku.algo.chess.Move;
import me.kazoku.algo.chess.Position;

public enum KnightMove implements Move {
                            V_LEFT_U(-1, +2),   V_RIGHT_U(+1, +2),
    H_LEFT_U(-2, +1),                                                   H_RIGHT_U(+2, +1),
    /*                                              K                                               */
    H_LEFT_B(-2, -1),                                                   H_RIGHT_B(+2, -1),
                            V_LEFT_B(-1, -2),   V_RIGHT_B(+1, -2);

    private final long nextX;
    private final long nextY;
    KnightMove(long x, long y) {
        this.nextX = x;
        this.nextY = y;
    }

    @Override
    public Position move(Position current) {
        return new Position(current.getX() + nextX, current.getY() + nextY);
    }
}
