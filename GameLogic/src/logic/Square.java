package logic;

import sharedStructures.eColor;

public class Square {
    private final int m_Row;
    private final int m_Column;
    private String m_SquareSymbol;
    private eColor m_Color;

    public Square(int i_Row, int i_Column, String i_SquareSymbol, eColor i_Color ){
        m_Row = i_Row;
        m_Column = i_Column;
        m_SquareSymbol = i_SquareSymbol;
        m_Color = i_Color;
    }

    public Square() {
        m_Column = 0;
        m_Row = 0;
        m_SquareSymbol = "";
        m_Color = eColor.DEFAULT;
    }

    //GET METHODS
    public String getSquareSymbol() {
        return m_SquareSymbol;
    }

    //GET METHODS
    public void setSquareSymbol(String i_SquareSymbol) {
        m_SquareSymbol = i_SquareSymbol;
    }

    //GET METHODS
    public int getColumn() {
        return m_Column;
    }

    public int getRow() {
        return m_Row;
    }


    public void swapSquare(Square i_markToChange) {
        String symbolTemp;

        symbolTemp = i_markToChange.getSquareSymbol();

        i_markToChange.setSquareSymbol(this.getSquareSymbol());

        this.setSquareSymbol(symbolTemp);
    }

    public eColor getColor() {
        return m_Color;
    }
}
