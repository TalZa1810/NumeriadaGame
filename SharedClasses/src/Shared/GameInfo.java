package Shared;

import java.sql.Time;

/**
 * Created by Tal on 11/28/2016.
 */
public class GameInfo {
    private int m_MaxRange;
    private int m_BoardSize;
    private String[][] m_Board;
    private String m_CurrPlayer;
    private int m_NumOfMoves;
    private Time m_ElapsedTime;
    private int m_RowPlayerScore;
    private int m_ColPlayerScore;
    private int m_MarkerRow;
    private int m_MarkerCol;

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

    public int GetMaxRange() {
        return m_MaxRange;
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

    public void setMaxRange(int i_MaxRange) {
        this.m_MaxRange = i_MaxRange;
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
}
