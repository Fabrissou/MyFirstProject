package org.chesspieces;

//    Конь

public class Knight extends ChessPiece {
    public Knight(String color, String name) {
        super(color, name);
    }

    @Override
    public boolean killKing(int kingX, int kingY, int i, int j, String color) {
        return (((Math.abs(8 - kingX - i) == 2) && (Math.abs(kingY - 1 - j) == 1))
                || ((Math.abs(8 - kingX - i) == 1) && (Math.abs(kingY - 1 - j) == 2)))
                && (!color.equals(this.color));
    }
}