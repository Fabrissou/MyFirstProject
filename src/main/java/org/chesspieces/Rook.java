package org.chesspieces;

//    Ладья

public class Rook extends ChessPiece {
    public Rook(String color) {
        super(color);
    }


    @Override
    public boolean killKing(int kingX, int kingY, int i, int j) {
        return ((8 - kingX == i) || (kingY - 1 == j));
    }

}
