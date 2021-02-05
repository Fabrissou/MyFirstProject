package org.chesspieces;

//    Пешка.
//    В конструкторе происходит увеличение счетчика. Метод minusNum уменьшает счетчик.

public class Pawn extends ChessPiece {
    public Pawn(String color) {
        super(color);
    }

    @Override
    public boolean killKing(int kingX, int kingY, int i, int j, String color) {
        return ((!color.equalsIgnoreCase(this.color)) && ((Math.abs(8 - kingX - i) == 1) && (Math.abs(kingY - 1 - j) == 1)));
    }
}
