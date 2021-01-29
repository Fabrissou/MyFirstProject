package org.chesspieces;

//    Ладья

public class Rook extends ChessPiece {
    public Rook(String color, String name) {
        super(color, name);
    }


    @Override
    public boolean killKing(int kingX, int kingY, int i, int j, String color) {
        return ((8 - kingX == i) || (kingY - 1 == j)) && (!color.equals(this.color));
    }

}
