package org.chesspieces;

//  абстрактный класс. У любой фигуры есть цвет и название. Конструктор контролирует,
//  чтобы не создавались фигуры всех цветов радуги. Метод minusNum по-факту реализует только пешка.
//  Метод killKing переопределен у всех фигур по-разному.

public abstract class ChessPiece {
    public ChessPiece(String color, String name) {
        if ((color.equals("white")) || (color.equals("black"))) {
            this.color = color;
            this.name = name;
        } else System.out.println("There are two genders in chess");
    }

    protected String color, name;

    public String getColor() { return color; }
    public String getName() { return name; }

    public void minusNum(String color) {
        System.out.println("");
    }

    public boolean killKing(int kingX, int kingY, int i, int j, String color) {
        return false;
    }
}
