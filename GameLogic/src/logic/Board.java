package logic;
import java.util.ArrayList;

public class Board {

    private int m_BoardSize;
    private ArrayList<ArrayList<Squares>> m_Board;
    private Squares m_Mark;
    private eBoardType m_BoardType;
    private Range m_Range;

    private enum eBoardType{
        EXPLICIT, RANDOM
    }

    private class Range{

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

    public void createRange(int i_From, int i_To) {
        m_Range = new Range(i_From,i_To);
    }

    public void createMarker(int i_MarkerRow, int i_MarkerCol) {
        m_Mark = new Square(i_MarkerRow, i_MarkerCol, "@");
    }

    public boolean checkIfGameDone(String i_PlayerType) {
        boolean gameDone = true;

        if(i_PlayerType.equals("ROW_PLAYER")){
            for(int i = 0; i < m_BoardSize; i++) {
                //check if row is empty of numbers
                if (!getSquareInPos(m_Mark.getRow(), i).getSquareSymbol().equals(m_Mark.getSquareSymbol()) &&
                        !getSquareInPos(m_Mark.getRow(), i).getSquareSymbol().equals("")) {
                    gameDone = false;
                    break;
                }
            }
        }
        else{
            for(int i = 0; i < getBoardSize(); i++){
                //check if column is empty of numbers
                if(!getSquareInPos(i,m_Mark.getColumn()).getSquareSymbol().equals(m_Mark.getSquareSymbol()) &&
                        !getSquareInPos(i, m_Mark.getColumn()).getSquareSymbol().equals("")){
                    gameDone = false;
                    break;
                }
            }
        }
        return gameDone;
    }

    public int getToRange(){
        return m_Range.m_To;
    }

    public int getFromRange(){
        return m_Range.m_From;
    }

    public void setToRange(int i_Range){
        m_Range.setTo(i_Range);
    }

    public void setFromRange(int i_Range){

        m_Range.setFrom(i_Range);
    }

    public int getMarkerCol() {
        return m_Mark.getColumn();
    }

    public int getMarkerRow() {
        return m_Mark.getRow();
    }

    public int getBoardSize() {
        return m_BoardSize;
    }

    public void changeMarker(Squares i_SquareToChange, Squares i_MarkToChange) {
        i_SquareToChange.swapSquare(i_MarkToChange);
        setMark(i_MarkToChange);
        i_SquareToChange.setSquareSymbol("");

    }

    public void setMark(Squares i_Mark) {
        this.m_Mark = i_Mark;
    }
}
