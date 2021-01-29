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
    private ChessPiece[][] chessBoard = new ChessPiece[8][8];

//        Нужно держать координаты королей в памяти, чтобы контролировать их постановку в соседние клетки
    private int whiteX, whiteY, blackX, blackY;

//        Конструктор, который при создании требует координаты черного и белого короля.
    public ChessBoard(int whiteKingX, int whiteKingY, int blackKingX, int blackKingY) {
        if ((Math.abs(whiteKingX - blackKingX) < 2) && (Math.abs(whiteKingY - blackKingY) < 2)) {
            System.out.println("Kings cannot be on adjacent cells");
        } else {
            chessBoard[8 - whiteKingX][whiteKingY - 1] = new King("white");
            whiteX = whiteKingX; whiteY = whiteKingY;
            chessBoard[8 - blackKingX][blackKingY - 1] = new King("black");
            blackX = blackKingX; blackY = blackKingY;
        }
    }

//    вспомогательный метод для тестов. Получает название и цвет фигуры по координатам
    public String getChessPiece(int x, int y) {
        try {
            if (chessBoard[8 - x][y - 1] != null) {
                System.out.println(chessBoard[8 - x][y - 1].getName() + " " + chessBoard[8 - x][y - 1].getColor());
                return chessBoard[8 - x][y - 1].getName() + " " + chessBoard[8 - x][y - 1].getColor();
            } else {
                System.out.println("Field is empty");
                return "Field is empty";
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Please, read the rules of the game of chess...");
            return "Please, read the rules of the game of chess...";
        }

    }
//    очистка поля. Сделана защита от удаления короля. 60 строчка нужна для контроля количества пешек данного цвета
    public String clearField(int x, int y) {
        try {
            if ((chessBoard[8 - x][y - 1] != null) && (!chessBoard[8 - x][y - 1].getName().equals("king"))) {
                chessBoard[8 - x][y - 1].minusNum(chessBoard[8 - x][y - 1].getColor());
                chessBoard[8 - x][y - 1] = null;
                System.out.println("Field cleared successfully");
                return "Field is empty";
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Please, read the rules of the game of chess...");
            return "Please, read the rules of the game of chess...";
        }
        return "If the king is removed, the game is over";
    }

//    добавление фигуры. При создании пешки в ее конструкторе работает счетчик.
    public String addPiece(int x, int y, String pieceName, String color) {
        try {
            switch (pieceName) {
                case "rook" -> chessBoard[8 - x][y - 1] = new Rook(color, pieceName);
                case "queen" -> chessBoard[8 - x][y - 1] = new Queen(color, pieceName);
                case "knight" -> chessBoard[8 - x][y - 1] = new Knight(color, pieceName);
                case "bishop" -> chessBoard[8 - x][y - 1] = new Bishop(color, pieceName);
                case "pawn" -> chessBoard[8 - x][y - 1] = new Pawn(color, pieceName);
                case "king" -> {
                    System.out.println("You can't add a king");
                    return "You can't add a king";
                }
                default -> {
                    System.out.println("Such a figure does not exist :(");
                    return "Such a figure does not exist :(";
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Please, read the rules of the game of chess...");
        }
        return "Please, read the rules of the game of chess...";
    }

//    самый громоздкий метод. Осуществляет перемещение фигуры путем удаления старой и
//    создания новой на указанных координатах. 109 строка уменьшает счетчик пешки на 1, так как при
//    создании новой пешки счетчик увеличится. Строки 113,114 контролируют "соседство" королей. Счетчик
//    для королей нам не нужен, так как создать нового или удалить старого мы не можем.


    public void moveTheChessPiece(int beforeX, int beforeY, int afterX, int afterY) {
        if ((chessBoard[8 - beforeX][beforeY - 1] != null) && (chessBoard[8 - afterX][afterY - 1] == null)) {
            try {
                String name = chessBoard[8 - beforeX][beforeY - 1].getName();
                String color = chessBoard[8 - beforeX][beforeY - 1].getColor();

                if (!name.equals("king")) {
                    if (name.equals("pawn")) {
                        chessBoard[8 - beforeX][beforeY - 1].minusNum(color);
                    }
                    this.addPiece(afterX, afterY, name, color);
                } else {
                    if (((Math.abs(whiteX - afterX) < 2) && (Math.abs(whiteY - afterY) < 2))
                    || ((Math.abs(blackX - afterX) < 2) && (Math.abs(blackY - afterY) < 2))) {
                        System.out.println("Kings cannot be on adjacent cells");
                    } else {
//                        chessBoard[8 - beforeX][beforeY - 1].minusNum(color);
                        chessBoard[8 - afterX][afterY - 1] = new King(color);
                        if (color.equals("white")){
                            whiteX = afterX;
                            whiteY = afterY;
                        } else {
                            blackX = afterX;
                            blackY = afterY;
                        }
                    }
                }
                chessBoard[8 - beforeX][beforeY - 1] = null;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please, read the rules of the game of chess...");
            }
        }

    }

//    бонусный метод. На вход принимает координаты короля, проходится по всей доске и
//    вызывает у (неNull и неKing)-полей метод killKing, который реализован у всех по-разному.

    public boolean kingThreat(int kingX, int kingY) {
        try {
            if (chessBoard[8 - kingX][kingY - 1].getName().equals("king")) {
                String color = chessBoard[8 - kingX][kingY - 1].getColor();
                for (int i = 0; i <= 7; i++) {
                    for (int j = 0; j <= 7; j++) {
                        if ((chessBoard[i][j] != null) && (!chessBoard[i][j].getName().equals("king"))) {
                            if (chessBoard[i][j].killKing(kingX, kingY, i, j, color)) return true;
                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Please, read the rules of the game of chess...");
        }

        return false;
    }
}
