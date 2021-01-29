package org.chesspieces;

//    Пешка.
//    В конструкторе происходит увеличение счетчика. Метод minusNum уменьшает счетчик.

public class Pawn extends ChessPiece {
    public Pawn(String color, String name) {
        super(color, name);
        if (color.equals("white")) {
            if (whiteNumOfPawnsOnTheBoard == 8) {
                System.out.println("The maximum number of pawns of this color on the board");
            } else whiteNumOfPawnsOnTheBoard++;
        }
        if (color.equals("black")) {
            if (blackNumOfPawnsOnTheBoard == 8) {
                System.out.println("The maximum number of pawns of this color on the board");
            } else blackNumOfPawnsOnTheBoard++;
        }
    }

    static int whiteNumOfPawnsOnTheBoard = 0;
    static int blackNumOfPawnsOnTheBoard = 0;

    public void minusNum(String color) {
        if (color.equals("white")) {
            whiteNumOfPawnsOnTheBoard--;
        } else if (color.equals("black")) blackNumOfPawnsOnTheBoard--;
    }

    @Override
    public boolean killKing(int kingX, int kingY, int i, int j, String color) {
        return ((!color.equals(this.color)) && ((Math.abs(8 - kingX - i) == 1) && (Math.abs(kingY - 1 - j) == 1)));
    }
}
