package logic;

/**
 * Created by talza on 27/11/2016.
 */
public class Squares {

    private final int m_Row;
    private final int m_Column;
    private String m_SquareSymbol;

    public Squares(int i_Row, int i_Column, String i_SquareSymbol){
        m_Row = i_Row;
        m_Column = i_Column;
        m_SquareSymbol = i_SquareSymbol;
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


    public void swapSquare(Squares i_markToChange) {
        String symbolTemp;

        symbolTemp = i_markToChange.getSquareSymbol();

        i_markToChange.setSquareSymbol(this.getSquareSymbol());

        this.setSquareSymbol(symbolTemp);
    }
}
