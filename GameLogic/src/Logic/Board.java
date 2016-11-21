package Logic;


/**
 * Created by talza on 20/11/2016.
 */
public class Board {

    int m_BoardSize;
    Square m_Board[][];
    Square m_BoardSign;

    public Board( int i_BoardSize) {
        m_BoardSize = i_BoardSize;

        m_Board =  new Square[m_BoardSize][m_BoardSize];
        for(Square[] row: m_Board) {
            for(Square square: row){
                square = new Square(0,0,' ');
            }
        }
    }



}
