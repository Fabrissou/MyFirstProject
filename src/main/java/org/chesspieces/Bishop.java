package org.chesspieces;

//    Слон.

public class Bishop extends ChessPiece {
    public Bishop(String color) {
        super(color);
    }

    @Override
    public boolean killKing(int kingX, int kingY, int i, int j, String color) {
        return (Math.abs(8 - kingX - i) == Math.abs(kingY - 1 - j)) && (!color.equals(this.color));
    }
}
