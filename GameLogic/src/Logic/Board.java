package Logic;
import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Created by talza on 20/11/2016.
 */
public class Board {

    private int m_BoardSize;
    private ArrayList<ArrayList<Squares>> m_Board;
    private Marker m_Mark;
    private eBoardType m_BoardType;
    private Range m_Range;
    //start and end of iterator
    protected Squares m_Start;
    protected Squares m_End;

    public Marker getMark() {
        return m_Mark;
    }

    public Squares getSquareInPos(int i, int j) {

        return m_Board.get(i).get(j);
    }

    private enum eBoardType{
        EXPLICIT, RANDOM
    }

    //SHOULD BE STATIC?
    private static class Range{

        private int m_From;
        private int m_To;


        public Range (int i_From, int i_To) throws RangeException{
            m_From = i_From;
            m_To = i_To;
        }

        //TODO- check range is valid (from < to )
        private class RangeException extends Exception {

        }

        //GET METHODS
        public int GetFromRange(){
            return m_From;
        }

        public int GetToRange(){
            return m_To;
        }

    }

    //SET DATA MEMBERS WHEN USING THE CTOR.

    public int GetMarkerCol() {
        return m_Mark.GetColumn();
    }

    public int GetMarkerRow() {
        return m_Mark.GetRow();
    }


    //TODO: SHOULD CATCH EXCEPTION IN CASE WRONG BOARD SIZE
    public Board(int i_BoardSize) {

        m_BoardSize = i_BoardSize;
        m_Board = new ArrayList<ArrayList<Squares>>(m_BoardSize) ;

        for(ArrayList<Squares> s: m_Board ){
            s = new ArrayList<Squares> (m_BoardSize) ;
        }

        for (ArrayList<Squares> row : m_Board) {
            for (Squares square : row) {
                square = new Square(0, 0, " ");
            }
        }
    }


    public int getBoardSize() {
        return m_BoardSize;
    }

    //TODO: PRINTLN??
    private class BoardSizeException extends Exception {


    }


    public void BoardCheckings () throws FileNotFoundException{

    }


    private void checkBoardSize() throws BoardSizeException {

        if (m_BoardSize < 5 || m_BoardSize > 50) {
            throw new BoardSizeException();
        }
    }

    private void checkRandomBoard(){

        int bucketSize = m_Range.GetToRange() - m_Range.GetFromRange() + 1;

        //already initialized
        int [] boardBucketSort = new int [bucketSize];

        if (m_BoardType == eBoardType.RANDOM){


        }
    }


    public void SetMarker(Player i_Player, Squares i_MarkToChange) {
        i_MarkToChange.SetSquareSymbol("");
    }

    public void SetSquare(Player i_Player, Squares i_SquareToChange) {
        i_SquareToChange.SetSquareSymbol(m_Mark.GetSquareSymbol());
    }



}

    //==XML==
    //TODO:CREATING BOARD ACCORDING TO XML FILE
    //TODO: MAKE SURE THERE ARE NO TWO SQUARES ON THE XML FILE
    //TODO: MAKE SURE THAT ALL NUMBERS APPEAR THE SAME NUMBER OF APPEARANCES
