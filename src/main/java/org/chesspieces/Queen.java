package org.chesspieces;

//    Ферзь

public class Queen extends ChessPiece {
    public Queen(String color, String name) {
        super(color, name);
    }

    @Override
    public boolean killKing(int kingX, int kingY, int i, int j, String color) {
        return ((!color.equals(this.color))
                && (((8 - kingX == i) || (kingY - 1 == j))
                || (Math.abs(8 - kingX - i) == Math.abs(kingY - 1 - j))));
    }
}
