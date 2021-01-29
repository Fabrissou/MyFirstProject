package org;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {

    @org.junit.jupiter.api.Test
    void getChessPiece() {
        ChessBoard chessBoard = new ChessBoard(1, 1, 3, 3);
        chessBoard.addPiece(4, 1, "knight", "white");
        chessBoard.addPiece(4, 2, "rook", "black");

        assertEquals("knight white", chessBoard.getChessPiece(4, 1));
        assertEquals("rook black", chessBoard.getChessPiece(4, 2));
    }

    @org.junit.jupiter.api.Test
    void clearField() {
        ChessBoard chessBoard = new ChessBoard(1, 1, 3, 3);
        chessBoard.addPiece(4, 1, "knight", "white");
        chessBoard.addPiece(4, 2, "rook", "black");
        chessBoard.addPiece(4, 3, "queen", "white");
        chessBoard.addPiece(4, 4, "bishop", "white");

//      удаление короля:
        assertEquals("If the king is removed, the game is over", chessBoard.clearField(1, 1));
//      удаление любой фигуры, кроме короля:
        assertEquals("Field is empty", chessBoard.clearField(4, 1));
        assertEquals("Field is empty", chessBoard.clearField(4, 2));
        assertEquals("Field is empty", chessBoard.clearField(4, 3));
        assertEquals("Field is empty", chessBoard.clearField(4, 4));
    }

    @org.junit.jupiter.api.Test
    void addPiece() {
        ChessBoard chessBoard = new ChessBoard(1, 1, 1, 8);
        chessBoard.addPiece(8, 1, "rook", "white");
        chessBoard.addPiece(8, 2, "rook", "black");
        chessBoard.addPiece(8, 3, "pawn", "white");
        chessBoard.addPiece(8, 4, "knight", "black");
        chessBoard.addPiece(8, 5, "queen", "black");
        chessBoard.addPiece(8, 6, "bishop", "white");
        chessBoard.addPiece(8, 7, "knight", "white");
        chessBoard.addPiece(8, 8, "pawn", "white");

//        создание любой фигуры:
        assertEquals("rook white", chessBoard.getChessPiece(8, 1));
        assertEquals("rook black", chessBoard.getChessPiece(8, 2));
        assertEquals("pawn white", chessBoard.getChessPiece(8, 3));
        assertEquals("knight black", chessBoard.getChessPiece(8, 4));
        assertEquals("queen black", chessBoard.getChessPiece(8, 5));
        assertEquals("bishop white", chessBoard.getChessPiece(8, 6));
        assertEquals("knight white", chessBoard.getChessPiece(8, 7));
        assertEquals("pawn white", chessBoard.getChessPiece(8, 8));
//        создание короля:
        assertEquals("You can't add a king", chessBoard.addPiece(7, 1, "king", "white"));
        assertEquals("You can't add a king", chessBoard.addPiece(7, 4, "king", "black"));


//        тест на счетчик пешек:
        chessBoard.addPiece(1, 1, "pawn", "black");
        chessBoard.addPiece(1, 2, "pawn", "black");
        chessBoard.addPiece(1, 3, "pawn", "black");
        chessBoard.addPiece(1, 4, "pawn", "black");
        chessBoard.addPiece(1, 5, "pawn", "black");
        chessBoard.addPiece(1, 6, "pawn", "black");
        chessBoard.addPiece(1, 7, "pawn", "black");
        chessBoard.addPiece(1, 8, "pawn", "black");

//        добавляем девятую черную пешку:
        assertEquals("Please, read the rules of the game of chess...", chessBoard.addPiece(2, 1, "pawn", "black"));

//        удаление одной пешки и добавление новой:
        chessBoard.clearField(1, 8);
        assertEquals("Field is empty", chessBoard.getChessPiece(1, 8));
        chessBoard.addPiece(2, 2, "pawn", "black");
        assertEquals("pawn black", chessBoard.getChessPiece(2, 2));
    }


    @org.junit.jupiter.api.Test
    void moveTheChessPiece() {
        ChessBoard chessBoard = new ChessBoard(1, 1, 1, 8);

//        перемещение любой фигуры:
        chessBoard.addPiece(4, 1, "rook", "white");
        assertEquals("rook white", chessBoard.getChessPiece(4, 1));
        assertEquals("Field is empty", chessBoard.getChessPiece(4, 2));
        chessBoard.moveTheChessPiece(4,1, 4, 2);
        assertEquals("Field is empty", chessBoard.getChessPiece(4, 1));
        assertEquals("rook white", chessBoard.getChessPiece(4, 2));

//        перемещение любой фигуры:
        chessBoard.addPiece(8, 3, "pawn", "black");
        assertEquals("pawn black", chessBoard.getChessPiece(8, 3));
        assertEquals("Field is empty", chessBoard.getChessPiece(8, 8));
        chessBoard.moveTheChessPiece(8,3, 8, 8);
        assertEquals("Field is empty", chessBoard.getChessPiece(8, 3));
        assertEquals("pawn black", chessBoard.getChessPiece(8, 8));

//        перемещение короля:
        assertEquals("king white", chessBoard.getChessPiece(1, 1));
        assertEquals("Field is empty", chessBoard.getChessPiece(1, 4));
        chessBoard.moveTheChessPiece(1,1, 1, 4);
        assertEquals("Field is empty", chessBoard.getChessPiece(1, 1));
        assertEquals("king white", chessBoard.getChessPiece(1, 4));

//        перемещение короля на соседнюю клетку от другого короля:
        assertEquals("king white", chessBoard.getChessPiece(1, 4));
        assertEquals("Field is empty", chessBoard.getChessPiece(1, 7));
        chessBoard.moveTheChessPiece(1,4, 1, 7);
        assertEquals("Field is empty", chessBoard.getChessPiece(1, 7));
    }



    @org.junit.jupiter.api.Test
    void kingThreat() {
        ChessBoard chessBoard = new ChessBoard(4, 4, 1, 8);


//        королю никто не угражает:
        assertFalse(chessBoard.kingThreat(4, 4));

//        добавление фигуры ТОГО ЖЕ цвета на место потенциальной угрозы:
        chessBoard.addPiece(5, 6, "knight", "white");
        assertFalse(chessBoard.kingThreat(4, 4));

//        королю угрожает конь:
        chessBoard.addPiece(5, 2, "knight", "black");
        assertTrue(chessBoard.kingThreat(4, 4));
//        удаление коня, проверка угрозы:
        chessBoard.clearField(5, 2);
        assertFalse(chessBoard.kingThreat(4, 4));


//        угроза ферзем "в лоб"
        chessBoard.addPiece(1, 4, "queen", "black");
        assertTrue(chessBoard.kingThreat(4, 4));
        chessBoard.clearField(1, 4);

//        угроза ферзем по диагонали:
        chessBoard.addPiece(8, 8, "queen", "black");
        assertTrue(chessBoard.kingThreat(4, 4));
        chessBoard.clearField(8, 8);

//        угроза слоном:
        chessBoard.addPiece(1, 1, "bishop", "black");
        assertTrue(chessBoard.kingThreat(4, 4));
        chessBoard.clearField(1, 1);

//        угроза ладьей:
        chessBoard.addPiece(4, 8, "rook", "black");
        assertTrue(chessBoard.kingThreat(4, 4));
        chessBoard.clearField(4, 8);

        //        угроза пешкой:
        chessBoard.addPiece(3, 3, "pawn", "black");
        assertTrue(chessBoard.kingThreat(4, 4));
        chessBoard.clearField(3, 3);
    }








}