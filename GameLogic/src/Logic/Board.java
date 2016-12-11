package Logic;
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

    public Board(int i_BoardSize) {

        m_BoardSize = i_BoardSize;
        m_Board = new ArrayList<ArrayList<Squares>>(m_BoardSize) ;

        for(ArrayList<Squares> s: m_Board ){
            s = new ArrayList<Squares> (m_BoardSize) ;
        }

        for (ArrayList<Squares> row : m_Board) {
            for (Squares square : row) {
                square = new Squares(0, 0, "");
            }
        }
    }



    public Marker getMark() {
        return m_Mark;
    }

    public Squares getSquareInPos(int i, int j) {

        return m_Board.get(i).get(j);
    }

    public void setBoardSize(int i_BoardSize) {
        this.m_BoardSize = i_BoardSize;
    }

    private enum eBoardType{
        EXPLICIT, RANDOM
    }

    //SHOULD BE STATIC?
    private static class Range{

        private int m_From;
        private int m_To;


        public Range (int i_From, int i_To){
            m_From = i_From;
            m_To = i_To;
        }


        //GET METHODS
         /*public int GetFromRange(){
            return m_From;
        }

       public int GetToRange(){
            return m_To;
        }*/

    }

    public int GetToRange(){
        return m_Range.m_To;
    }

    public int GetFromRange(){
        return m_Range.m_From;
    }






    public int GetMarkerCol() {
        return m_Mark.GetColumn();
    }

    public int GetMarkerRow() {
        return m_Mark.GetRow();
    }

    public int getBoardSize() {
        return m_BoardSize;
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
