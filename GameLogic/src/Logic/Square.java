package Logic;

/**
 * Created by talza on 20/11/2016.
 */
public class Square {
    int m_Row;
    int m_Column;
    char m_SquareSymbol;
    // TODO: Color field


    public Square(int i_Row, int i_Column, char i_SquareSymbol ){
        m_Row = i_Row;
        m_Column = i_Column;
        m_SquareSymbol =  i_SquareSymbol;
    }

    //GET METHODS
    public int GetColumn() {
        return m_Column;
    }

    public int GetSquareSymbol() {
        return m_SquareSymbol;
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

    public void SetSquareSymbol(char i_SquareSymbol) {
        m_SquareSymbol = i_SquareSymbol;
    }

    public void SetSquare(char i_Symbol) {
        m_SquareSymbol = i_Symbol;
    }


}
