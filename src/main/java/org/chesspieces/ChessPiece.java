package org.chesspieces;

//  абстрактный класс. У любой фигуры есть цвет и название. Конструктор контролирует,
//  чтобы не создавались фигуры всех цветов радуги. Метод minusNum по-факту реализует только пешка.
//  Метод killKing переопределен у всех фигур по-разному.

import java.util.Objects;

public abstract class ChessPiece {

    public ChessPiece(String color) {
        if ((("white".equals(color))) || ("black".equals(color))) {
            this.color = color.toLowerCase();
        } else throw new IllegalArgumentException("Something went wrong");
    }

    protected String color;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return color.equals(that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    public String getColor() { return color; }

    public boolean killKing(int kingX, int kingY, int i, int j, String color) {
        return false;
    }
}
