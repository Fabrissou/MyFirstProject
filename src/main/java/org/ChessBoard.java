package org;


import org.chesspieces.*;

//Вариант 20 -- поле для шахмат [Java]
//        Имеет размер 8х8, на каждой клетке может стоять белая или чёрная фигура.
//        Фигур шесть видов: пешка, конь, слон, ладья, ферзь, король.
//        Ограничения (инварианты), которые требуется соблюсти: ровно один белый король,
//        ровно один чёрный король, не более восьми белых пешек, не более восьми чёрных пешек,
//        короли не могут находиться на соседних клетках.
//        Операции: конструктор (сразу же указывает положение белого и чёрного короля,
//        очистить клетку, поставить новую фигуру (кроме короля),
//        переместить существующую фигуру на другую клетку (соблюдать правила ходов не надо).
//        Бонус: проверить, находится ли определённый король под шахом (можно поддержать все или часть фигур).


//          chessBoard[8 - x][y - 1] таким образом координаты введеные человеком преобразуются в
//          координаты массива.
//          Каждый метод реализован через try..catch. Таким образом контролируется выход за пределы доски

public class ChessBoard {
    private final ChessPiece[][] chessBoard = new ChessPiece[8][8];

    //        Нужно держать координаты королей в памяти, чтобы контролировать их постановку в соседние клетки
    private int whiteX, whiteY, blackX, blackY;

    private int numberOfWhitePawns = 0, numberOfBlackPawns = 0;

    private void minusPawn(String color) {
        if ("white".equals(color)) {
            numberOfWhitePawns--;
        } else numberOfBlackPawns--;
    }

    //        Конструктор, который при создании требует координаты черного и белого короля.
    public ChessBoard(int whiteKingX, int whiteKingY, int blackKingX, int blackKingY) {
        checkCoordinates(whiteKingX, whiteKingY);
        checkCoordinates(blackKingX, blackKingY);
        if ((Math.abs(whiteKingX - blackKingX) < 2) && (Math.abs(whiteKingY - blackKingY) < 2)) {
            throw new IllegalArgumentException("Something went wrong");
        } else {
            chessBoard[8 - whiteKingX][whiteKingY - 1] = new King("white");
            whiteX = whiteKingX;
            whiteY = whiteKingY;
            chessBoard[8 - blackKingX][blackKingY - 1] = new King("black");
            blackX = blackKingX;
            blackY = blackKingY;
        }
    }


    public void checkCoordinates(int x, int y) {
        if (x > 8 || x < 1 || y > 8 || y < 1) {
            throw new IllegalArgumentException("Something went wrong");
        }
    }

    //    вспомогательный метод для тестов. Получает название и цвет фигуры по координатам
    public ChessPiece getChessPiece(int x, int y) {
        checkCoordinates(x, y);
        if (chessBoard[8 - x][y - 1] != null) {
            return chessBoard[8 - x][y - 1];
        } else {
            return null;
        }
    }

    //    очистка поля. Сделана защита от удаления короля. 60 строчка нужна для контроля количества пешек данного цвета
    public void clearField(int x, int y) {
        checkCoordinates(x, y);
        ChessPiece piece = chessBoard[8 - x][y - 1];
        if (!(piece instanceof King)) {
            if (piece instanceof Pawn) {
                minusPawn(piece.getColor());
            }
            chessBoard[8 - x][y - 1] = null;
        } else throw new IllegalArgumentException("You cannot remove the king");
    }

    //    добавление фигуры. При создании пешки в ее конструкторе работает счетчик.
    public void addPiece(int x, int y, ChessPiece piece) {
        if (piece instanceof King) throw new IllegalArgumentException("You cannot add the king");
        if (chessBoard[8 - x][y - 1] != null) throw new IllegalArgumentException("Field is not empty");
        if (piece instanceof Pawn) {
            String color = piece.getColor();
            if ("white".equals(color)) {
                if (numberOfWhitePawns == 8) {
                    throw new IllegalArgumentException("Too many pawns");
                } else {
                    numberOfWhitePawns++;
                }
            } else {
                if (numberOfBlackPawns == 8) {
                    throw new IllegalArgumentException("Too many pawns");
                } else {
                    numberOfBlackPawns++;
                }
            }
        }
        chessBoard[8 - x][y - 1] = piece;
    }

//    самый громоздкий метод. Осуществляет перемещение фигуры путем удаления старой и
//    создания новой на указанных координатах. 110 строка уменьшает счетчик пешки на 1, так как при
//    создании новой пешки счетчик увеличится. Строки 114,115 контролируют "соседство" королей. Счетчик
//    для королей нам не нужен, так как создать нового или удалить старого мы не можем.


    public void moveTheChessPiece(int beforeX, int beforeY, int afterX, int afterY) {
        checkCoordinates(beforeX, beforeY);
        checkCoordinates(afterX, afterY);
        ChessPiece previousPiece = chessBoard[8 - beforeX][beforeY - 1];

        if ((previousPiece != null) && (chessBoard[8 - afterX][afterY - 1] == null)) {
            String color = previousPiece.getColor();

            if (previousPiece instanceof King) {
                if (((Math.abs(whiteX - afterX) < 2) && (Math.abs(whiteY - afterY) < 2))
                        || ((Math.abs(blackX - afterX) < 2) && (Math.abs(blackY - afterY) < 2))) {
                    throw new IllegalArgumentException("Kings cannot be on adjacent cells");
                } else {
                    chessBoard[8 - afterX][afterY - 1] = previousPiece;
                    if ("white".equals(color)) {
                        whiteX = afterX;
                        whiteY = afterY;
                    } else {
                        blackX = afterX;
                        blackY = afterY;
                    }
                }
            } else {
                if (previousPiece instanceof Pawn) {
                    minusPawn(color);
                }
                this.addPiece(afterX, afterY, previousPiece);
            }
            chessBoard[8 - beforeX][beforeY - 1] = null;
        } else {
            throw new IllegalArgumentException("Something went wrong");
        }
    }

//    бонусный метод. На вход принимает координаты короля, проходится по всей доске и
//    вызывает у (неNull и неKing)-полей метод killKing, который реализован у всех по-разному.

    public boolean kingThreat(int kingX, int kingY) {
        checkCoordinates(kingX, kingY);
        if (chessBoard[8 - kingX][kingY - 1] instanceof King) {
            String color = chessBoard[8 - kingX][kingY - 1].getColor();
            for (int i = 0; i <= 7; i++) {
                for (int j = 0; j <= 7; j++) {
                    if ((chessBoard[i][j] != null) && (!(chessBoard[i][j] instanceof King))) {
                        if (chessBoard[i][j].killKing(kingX, kingY, i, j, color)) return true;
                    }
                }
            }
        }
        return false;
    }



}
