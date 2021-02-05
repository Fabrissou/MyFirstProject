package org;

import org.chesspieces.*;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {

    @org.junit.jupiter.api.Test
    void getChessPiece() {
        ChessBoard chessBoard = new ChessBoard(1, 1, 8, 8);
        chessBoard.addPiece(2, 1, new Rook("white"));
        chessBoard.addPiece(2, 2, new Knight("black"));
        chessBoard.addPiece(2, 3, new Queen("white"));
        chessBoard.addPiece(2, 4, new Pawn("black"));
        chessBoard.addPiece(2, 5, new Bishop("white"));

        assertEquals(new Rook("white"), chessBoard.getChessPiece(2, 1));
        assertEquals(new Knight("black"), chessBoard.getChessPiece(2, 2));
        assertEquals(new Queen("white"), chessBoard.getChessPiece(2, 3));
        assertEquals(new Pawn("black"), chessBoard.getChessPiece(2, 4));
        assertEquals(new Bishop("white"), chessBoard.getChessPiece(2, 5));
    }

    @org.junit.jupiter.api.Test
    void clearField() {
        ChessBoard chessBoard = new ChessBoard(1, 1, 8, 8);
        chessBoard.addPiece(2, 1, new Rook("white"));
        chessBoard.addPiece(2, 2, new Knight("black"));
        chessBoard.addPiece(2, 3, new Queen("white"));
        chessBoard.addPiece(2, 4, new Pawn("black"));
        chessBoard.addPiece(2, 5, new Bishop("white"));

        chessBoard.clearField(2, 1);
        chessBoard.clearField(2, 2);
        chessBoard.clearField(2, 3);
        chessBoard.clearField(2, 4);
        chessBoard.clearField(2, 5);

        assertNull(chessBoard.getChessPiece(2, 1));
        assertNull(chessBoard.getChessPiece(2, 2));
        assertNull(chessBoard.getChessPiece(2, 3));
        assertNull(chessBoard.getChessPiece(2, 4));
        assertNull(chessBoard.getChessPiece(2, 5));

        assertThrows(IllegalArgumentException.class, ()->{ chessBoard.clearField(1, 1);});
        assertThrows(IllegalArgumentException.class, ()->{ chessBoard.clearField(8, 8);});

        assertThrows(IllegalArgumentException.class, ()->{ chessBoard.clearField(8, 8);});
        assertThrows(IllegalArgumentException.class, ()->{ chessBoard.clearField(8, 8);});
}

    @org.junit.jupiter.api.Test
    void addPiece() {
        ChessBoard chessBoard = new ChessBoard(1, 1, 8, 8);

        chessBoard.addPiece(3, 1, new Rook("white"));
        chessBoard.addPiece(3, 2, new Knight("black"));
        chessBoard.addPiece(3, 3, new Bishop("white"));
        chessBoard.addPiece(3, 4, new Queen("white"));

        assertEquals(new Rook("white"), chessBoard.getChessPiece(3, 1));
        assertEquals(new Knight("black"), chessBoard.getChessPiece(3, 2));
        assertEquals(new Bishop("white"), chessBoard.getChessPiece(3, 3));
        assertEquals(new Queen("white"), chessBoard.getChessPiece(3, 4));

        assertThrows(IllegalArgumentException.class, ()->{ chessBoard.addPiece(1, 8, new King("black"));});
        assertThrows(IllegalArgumentException.class, ()->{ chessBoard.addPiece(5, 8, new King("white"));});

        assertThrows(IllegalArgumentException.class, ()->{ chessBoard.addPiece(3, 1, new Pawn("white"));});
        assertThrows(IllegalArgumentException.class, ()->{ chessBoard.addPiece(3, 2, new Bishop("white"));});

        chessBoard.addPiece(4, 1, new Pawn("white"));
        chessBoard.addPiece(4, 2, new Pawn("white"));
        chessBoard.addPiece(4, 3, new Pawn("white"));
        chessBoard.addPiece(4, 4, new Pawn("white"));
        chessBoard.addPiece(4, 5, new Pawn("white"));
        chessBoard.addPiece(4, 6, new Pawn("white"));
        chessBoard.addPiece(4, 7, new Pawn("white"));
        chessBoard.addPiece(4, 8, new Pawn("white"));

        assertThrows(IllegalArgumentException.class, ()->{ chessBoard.addPiece(5, 1, new Pawn("white"));});

        chessBoard.clearField(4, 1);
        chessBoard.addPiece(5, 1, new Pawn("white"));
    }


    @org.junit.jupiter.api.Test
    void moveTheChessPiece() {
        ChessBoard chessBoard = new ChessBoard(2, 2, 2, 8);

        chessBoard.addPiece(1, 1, new Pawn("black"));
        assertEquals(new Pawn("black"), chessBoard.getChessPiece(1, 1));
        assertNull(chessBoard.getChessPiece(1, 6));
        chessBoard.moveTheChessPiece(1, 1, 1, 6);
        assertEquals(new Pawn("black"), chessBoard.getChessPiece(1, 6));
        assertNull(chessBoard.getChessPiece(1, 1));

        chessBoard.moveTheChessPiece(2, 2, 5, 5);
        assertEquals(new King("white"), chessBoard.getChessPiece(5, 5));
        assertNull(chessBoard.getChessPiece(2, 2));


        assertThrows(IllegalArgumentException.class, ()->{ chessBoard.moveTheChessPiece(5, 5, 2, 7); });

    }



    @org.junit.jupiter.api.Test
    void kingThreat() {
        ChessBoard chessBoard = new ChessBoard(4, 4, 1, 8);

        //        королю никто не угражает:
        assertFalse(chessBoard.kingThreat(4, 4));

//        добавление фигуры ТОГО ЖЕ цвета на место потенциальной угрозы:
        chessBoard.addPiece(5, 6, new Knight("white"));
        assertFalse(chessBoard.kingThreat(4, 4));

//        королю угрожает конь:
        chessBoard.addPiece(5, 2, new Knight("black"));
        assertTrue(chessBoard.kingThreat(4, 4));
//        удаление коня, проверка угрозы:
        chessBoard.clearField(5, 2);
        assertFalse(chessBoard.kingThreat(4, 4));


//        угроза ферзем "в лоб"
        chessBoard.addPiece(1, 4, new Queen("black"));
        assertTrue(chessBoard.kingThreat(4, 4));
        chessBoard.clearField(1, 4);

//        угроза ферзем по диагонали:
        chessBoard.addPiece(8, 8, new Queen("black"));
        assertTrue(chessBoard.kingThreat(4, 4));
        chessBoard.clearField(8, 8);

//        угроза слоном:
        chessBoard.addPiece(1, 1, new Bishop("black"));
        assertTrue(chessBoard.kingThreat(4, 4));
        chessBoard.clearField(1, 1);

//        угроза ладьей:
        chessBoard.addPiece(4, 8, new Rook("black"));
        assertTrue(chessBoard.kingThreat(4, 4));
        chessBoard.clearField(4, 8);

        //        угроза пешкой:
        chessBoard.addPiece(3, 3, new Pawn("black"));
        assertTrue(chessBoard.kingThreat(4, 4));
        chessBoard.clearField(3, 3);
    }






}
