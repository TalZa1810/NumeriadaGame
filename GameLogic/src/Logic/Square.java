package Logic;

/**
 * Created by talza on 20/11/2016.
 */
public class Square extends Squares {

    private String m_SquareSymbol;
    // TODO: Color field

    public Square(int i_Row, int i_Column, String i_SquareSymbol ){
        super(i_Row,i_Column);
        m_SquareSymbol =  i_SquareSymbol;
    }

    //GET METHODS
    public String GetSquareSymbol() {
        return m_SquareSymbol;
    }

    //GET METHODS
    public void SetSquareSymbol(String i_SquareSymbol) {
        m_SquareSymbol = i_SquareSymbol;
    }

    //METHODS
    public void ChangeSquareSymbole(String i_Symbol) {
        m_SquareSymbol = i_Symbol;
    }

    public  boolean IsNumeric() {
        return m_SquareSymbol.matches("-?\\d+");  //match a number with optional '-' and decimal.
    }

    public  int SquareNumber() {

        return Integer.parseInt(m_SquareSymbol);

    }
}
