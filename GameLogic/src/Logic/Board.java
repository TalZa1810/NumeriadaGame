package Logic;

/**
 * Created by talza on 20/11/2016.
 */
public class Board {

    private int m_BoardSize;
    private Square m_Board[][];
    private Square m_Mark;
    private BoardStructure m_BoardStructure;

    public enum BoardStructure {
        EXPLICIT, RANDOM
    }

    //TODO: SHOULD CATCH EXCEPTION IN CASE WRONG BOARD SIZE
    public Board( int i_BoardSize) throws BoardSizeNotSupported {

        if ( i_BoardSize < 5 || i_BoardSize > 50)
        {
            m_BoardSize = i_BoardSize;

            m_Board =  new Square[m_BoardSize][m_BoardSize];
            for(Square[] row: m_Board) {
                for(Square square: row){
                    square = new Square(0,0,' ');
                }
            }
        }
        else
        {
            //TODO: WRONG BOARD SIZE
            throw new BoardSizeNotSupported( );
        }
    }

    //TODO: WRONG BOARD SIZE
    public class BoardSizeNotSupported extends Exception {


    }



    //==XML==
    //TODO:CREATING BOARD ACCORDING TO XML FILE
    //TODO: MAKE SURE THERE ARE NO TWO SQUARES ON THE XML FILE
    //TODO: MAKE SURE THAT ALL NUMBERS APPEAR THE SAME NUMBER OF APPEARANCES





}
