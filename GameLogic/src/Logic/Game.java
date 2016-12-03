package Logic;

import Shared.GameInfo;

import java.sql.Time;

/**
 * Created by talza on 20/11/2016.
 */
abstract class Game extends GameTypeFactory{

    private Player [] m_Players;
    Board m_Board;
    private eBoardStructure m_BoardStructure;
    GameInfo m_GameInfo;
    protected  Player m_CurrentPlayer;
    private int m_NumOfMoves;
    private Time m_Timer;

    private enum eBoardStructure {
        EXPLICIT, RANDOM
    }

    public Game(){

    }

    public Game(int i_NumOfPlayers, int i_BoardSize, eBoardStructure i_BoardStructure, GameInfo i_GameInfo){
        m_Players = new Player[i_NumOfPlayers];
        m_Board = new Board(i_BoardSize);
        m_BoardStructure = i_BoardStructure;
        m_GameInfo = i_GameInfo;

        if (i_BoardStructure == eBoardStructure.EXPLICIT)
        {
            //USE
        }
        else
        {

        }
    }

    //TODO
    public void MakeMove(int move) {
        if(m_NumOfMoves % 2 == 0) {

            playTurn(Player.ePlayerType.ROW_PLAYER, move);
            m_NumOfMoves++;
            m_CurrentPlayer = m_Players[m_NumOfMoves % m_Players.length];

        }
        else{
            playTurn(Player.ePlayerType.COLUMN_PLAYER, move);
            m_NumOfMoves++;
            m_CurrentPlayer = m_Players[m_NumOfMoves % m_Players.length];
        }

    }


    abstract void GetBoardToPrint();


    public void GetGameStatus() {
        GetBoardToPrint();
        m_GameInfo.setCurrPlayer(m_CurrentPlayer.GetEPlayerTypeAsString());
    }

    //TODO: CHANGE: PLAYER NEEDS TO HOLD SCORE AND PLAYRT TYPE(COL / ROW)
    public void GetGameStatistics() {

        m_GameInfo.setNumOfMoves(m_NumOfMoves);
        m_GameInfo.setElapsedTime(m_Timer);

        for (Player p: m_Players){
            m_GameInfo.setRowPlayerScore(p.getPlayerScore());
        }
    }


    public void getCurrMarkerPosition() {
        m_GameInfo.setMarkerCol(m_Board.GetMarkerCol());
        m_GameInfo.setMarkerRow(m_Board.GetMarkerRow());
    }

    public void playTurn(Player.ePlayerType i_Player, int i_Move){
        m_Board.SetSquare(i_Player, i_Move);
        m_Board.SetMarker(i_Player, i_Move);
    }
}

/*
* play turn
* change square
* move mark
* update points
* timer
* move random
* */