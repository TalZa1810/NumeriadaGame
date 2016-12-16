package Logic;
import java.util.ArrayList;


/**
 * Created by talza on 20/11/2016.
 */
public class Board {

    private int m_BoardSize;
    private ArrayList<ArrayList<Squares>> m_Board;
    private Squares m_Mark;
    private eBoardType m_BoardType;
    private Range m_Range;

    public Board(int i_BoardSize) {

        m_BoardSize = i_BoardSize;
        m_Board = new ArrayList<ArrayList<Squares>>() ;

        for(int i = 0; i < m_BoardSize; i++){
            m_Board.add(new ArrayList<Squares>()) ;
        }

        int i = 0;
        for (ArrayList<Squares> row : m_Board) {
            for(int j = 0; j < m_BoardSize; j++) {
                row.add(new Square(i, j, ""));
            }
            i++;
        }


    }



    public Squares getMark() {
        return m_Mark;
    }



    public Squares getSquareInPos(int i, int j) {

        return m_Board.get(i).get(j);
    }

    public void setBoardSize(int i_BoardSize) {
        this.m_BoardSize = i_BoardSize;
    }

    public void CreateRange(int i_From, int i_To) {
        m_Range = new Range(i_From,i_To);
    }

    public void CreateMarker(int i_MarkerRow, int i_MarkerCol) {
        m_Mark = new Square(i_MarkerRow, i_MarkerCol, "@");
    }

    public boolean checkIfGameDone(String i_PlayerType) {
        boolean gameDone = true;

        if(i_PlayerType.equals("ROW_PLAYER")){
            for(int i = 0; i < m_BoardSize; i++) {
                //check if row is empty of numbers
                if (!getSquareInPos(m_Mark.GetRow(), i).GetSquareSymbol().equals(m_Mark.GetSquareSymbol()) &&
                        !getSquareInPos(m_Mark.GetRow(), i).GetSquareSymbol().equals("")) {
                    gameDone = false;
                    break;
                }
            }
        }
        else{
            for(int i = 0; i < getBoardSize(); i++){
                //check if column is empty of numbers
                if(!getSquareInPos(i,m_Mark.GetColumn()).GetSquareSymbol().equals(m_Mark.GetSquareSymbol()) &&
                        !getSquareInPos(i, m_Mark.GetColumn()).GetSquareSymbol().equals("")){
                    gameDone = false;
                    break;
                }
            }
        }
        return gameDone;
    }

    private enum eBoardType{
        EXPLICIT, RANDOM
    }

     class Range{

        private int m_From;
        private int m_To;


        public Range (int i_From, int i_To){
            m_From = i_From;
            m_To = i_To;
        }

        public void setFrom(int i_From) {
            this.m_From = i_From;
        }

        public void setTo(int i_To) {
            this.m_To = i_To;
        }

        public int getTo() {
            return m_To;
        }

        public int getFrom() {
            return m_From;
        }
    }

    public int GetToRange(){
        return m_Range.m_To;
    }

    public int GetFromRange(){
        return m_Range.m_From;
    }

    public void SetToRange(int i_Range){
        m_Range.setTo(i_Range);
    }

    public void SetFromRange(int i_Range){

        m_Range.setFrom(i_Range);
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

    public void changeMarker(Squares i_SquareToChange, Squares i_MarkToChange) {
        i_SquareToChange.SwapSquare(i_MarkToChange);
        ChangeMark(i_MarkToChange);
        i_SquareToChange.SetSquareSymbol("");

    }

    private void ChangeMark(Squares i_squareToChange) {
        m_Mark = i_squareToChange;
    }

    public void SetSquare(Player i_Player, Squares i_SquareToChange) {
        i_SquareToChange.SetSquareSymbol("");
    }

    public void setMark(Square m_Mark) {
        this.m_Mark = m_Mark;
    }
}

    //==XML==
    //TODO: MAKE SURE THERE ARE NO TWO SQUARES ON THE XML FILE
