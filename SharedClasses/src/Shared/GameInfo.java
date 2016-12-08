package Shared;


import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Time;

@XmlRootElement
public class GameInfo {
    private int m_RangeFrom;
    private int m_RangeTo;
    private int m_BoardSize;
    private String[][] m_Board;
    private String m_CurrPlayer;
    private int m_NumOfMoves;
    private Time m_ElapsedTime;
    private int m_RowPlayerScore;
    private int m_ColPlayerScore;
    private int m_MarkerRow;
    private int m_MarkerCol;
    private String m_GameType;
    private String m_BoardStructure;
    private  int m_NumOfPlayers = 2;

    public int getNumOfPlayers() {
        return m_NumOfPlayers;
    }

    public String getGameType() {
        return m_GameType;
    }

    public String getBoardStructure() {
        return m_BoardStructure;
    }

    public int getMarkerCol() {
        return m_MarkerCol;
    }

    public int getMarkerRow() {
        return m_MarkerRow;
    }

    public String[][] GetBoard() {
        return m_Board;
    }

    public int GetBoardSize() {
        return m_BoardSize;
    }

    public int GetRangeFrom() {
        return m_RangeFrom;
    }

    public int GetRangeTo() {
        return m_RangeTo;
    }

    public String GetCurrPlayer() {
        return m_CurrPlayer;
    }

    public int GetNumOfMoves() {
        return m_NumOfMoves;
    }

    public int GetColPlayerScore() {
        return m_ColPlayerScore;
    }

    public int GetRowPlayerScore() {
        return m_RowPlayerScore;
    }

    public Time GetElapsedTime() {
        return m_ElapsedTime;
    }

    public void setI_ColPlayerScore(int i_ColPlayerScore) {
        this.m_ColPlayerScore = i_ColPlayerScore;
    }

    public void setElapsedTime(Time i_ElapsedTime) {
        this.m_ElapsedTime = i_ElapsedTime;
    }

    public void setNumOfMoves(int i_NumOfMoves) {
        this.m_NumOfMoves = i_NumOfMoves;
    }

    public void setRowPlayerScore(int i_RowPlayerScore) {
        this.m_RowPlayerScore = i_RowPlayerScore;
    }

    public void setBoard(String[][] i_Board) {
        this.m_Board = i_Board;
    }

    public void setBoardSize(int i_BoardSize) {
        this.m_BoardSize = i_BoardSize;
    }

    public void setRangeFrom(int i_RangeFrom) {
        this.m_RangeFrom = i_RangeFrom;
    }

    public void setRangeTo(int i_setRangeTo) {
        this.m_RangeTo = i_setRangeTo;
    }

    public void setCurrPlayer(String i_CurrPlayer) {
        this.m_CurrPlayer = i_CurrPlayer;
    }

    public void setMarkerCol(int i_MarkerCol) {
        this.m_MarkerCol = i_MarkerCol;
    }

    public void setMarkerRow(int i_MarkerRow) {
        this.m_MarkerRow = i_MarkerRow;
    }

    public void setGameType(String i_GameType) {
        this.m_GameType = i_GameType;
    }

    public void setBoardStructure(String i_BoardStructure) {
        this.m_BoardStructure = i_BoardStructure;
    }

    public void setSquare(int i_Row, int i_Col, String i_Str) {
        m_Board[i_Row][i_Col] = i_Str;

        if(i_Str.equals("@")) {
            m_MarkerRow = i_Row;
            m_MarkerCol = i_Col;
        }
    }
}
