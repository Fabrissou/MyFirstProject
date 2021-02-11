package org.chesspieces;

//    Конь

public class Knight extends ChessPiece {
    public Knight(String color) {
        super(color);
    }

    @Override
    public boolean killKing(int kingX, int kingY, int i, int j) {
        return (((Math.abs(8 - kingX - i) == 2) && (Math.abs(kingY - 1 - j) == 1))
                || ((Math.abs(8 - kingX - i) == 1) && (Math.abs(kingY - 1 - j) == 2)));
    }
}
