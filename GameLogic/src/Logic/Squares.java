package Logic;

/**
 * Created by talza on 27/11/2016.
 */
public class Squares {

    private int m_Row;
    private int m_Column;
    private String m_SquareSymbol;

    public Squares(int i_Row, int i_Column ){
        m_Row = i_Row;
        m_Column = i_Column;
    }

    public Squares() {

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

    //SET METHODS
    public void SetRow(int i_Row){
        m_Row = i_Row;
    }

    public void SetColumn(int i_Column){
        m_Column = i_Column;
    }

}
