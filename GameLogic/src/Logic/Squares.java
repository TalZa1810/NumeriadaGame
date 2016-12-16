package Logic;

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
    public String GetSquareSymbol() {
        return m_SquareSymbol;
    }

    //GET METHODS
    public void SetSquareSymbol(String i_SquareSymbol) {
        m_SquareSymbol = i_SquareSymbol;
    }

    //GET METHODS
    public int GetColumn() {
        return m_Column;
    }

    public int GetRow() {
        return m_Row;
    }


    public void SwapSquare(Squares i_markToChange) {
        String symbolTemp;

        symbolTemp = i_markToChange.GetSquareSymbol();

        i_markToChange.SetSquareSymbol(this.GetSquareSymbol());

        this.SetSquareSymbol(symbolTemp);
    }
}
