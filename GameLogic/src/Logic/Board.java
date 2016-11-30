package Logic;
import java.io.FileNotFoundException;


/**
 * Created by talza on 20/11/2016.
 */
public class Board {

    private int m_BoardSize;
    private Square m_Board[][];
    private Marker m_Mark;
    private eBoardType m_BoardType;
    private Range m_Range;

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

        //SET DATA MEMBERS WHEN USING THE CTOR.
    }


    //TODO: SHOULD CATCH EXCEPTION IN CASE WRONG BOARD SIZE
    public Board(int i_BoardSize) {

        m_BoardSize = i_BoardSize;
        m_Board = new Square[m_BoardSize][m_BoardSize];

        for (Squares[] row : m_Board) {
            for (Squares square : row) {
                square = new Square(0, 0, " ");
            }
        }
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

    private void bucketBoard( int[] i_BucketBoard){

        for (int i=0; i < m_BoardSize; i++){

            for (int j=0; j < m_BoardSize; j++){

               //TODO

            }
        }
    }




}

    //==XML==
    //TODO:CREATING BOARD ACCORDING TO XML FILE
    //TODO: MAKE SURE THERE ARE NO TWO SQUARES ON THE XML FILE
    //TODO: MAKE SURE THAT ALL NUMBERS APPEAR THE SAME NUMBER OF APPEARANCES
