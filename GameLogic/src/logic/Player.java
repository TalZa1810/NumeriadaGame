package logic;

import shared.GameInfo;
import sharedStructures.MoveData;
import sharedStructures.PlayerData;
import sharedStructures.eColor;
import sharedStructures.ePlayerType;

import java.util.List;

abstract class Player {

    private int m_Points = 0;
    private int m_ID;
    private String m_Name;
    private ePlayerType m_PlayerType;
    private eColor m_PlayerColor;

    public Player(){}

    public Player(int i_ID, String i_Name, ePlayerType i_Type, eColor i_Color){
        m_ID = i_ID;
        m_Name = i_Name;
        m_PlayerColor = i_Color;
        m_PlayerType = i_Type;
    }

    public void setID(int i_ID) {
        this.m_ID = i_ID;
    }

    public int getID() {
        return m_ID;
    }

    public String getName() {
        return m_Name;
    }

    public void setName(String i_Name) {
        this.m_Name = i_Name;
    }

    public void playTurn(Board i_Board, GameInfo i_GameInfo , Square i_ChosenSquare, List<MoveData> i_MarkMoves, List<MoveData> i_PlayersMoves) {
        if ( i_ChosenSquare != null ){
            addToPlayerScore(Integer.parseInt(i_ChosenSquare.getSquareSymbol()));
            updatePlayerScoreInGameInfo(this, i_GameInfo);
            i_Board.changeMarker(i_Board.getMark(), i_ChosenSquare);
        }
    }

    private void updatePlayerScoreInGameInfo(Player i_Player, GameInfo i_gameInfo) {
        for(PlayerData player: i_gameInfo.getPlayers()){
            if(player.getID() == i_Player.getID()){
                player.setScore(i_Player.getPlayerScore());
            }
        }
    }


    ePlayerType getPlayerType() {
        return m_PlayerType;
    }

    void setPlayerType(ePlayerType i_PlayerType) {
        this.m_PlayerType = i_PlayerType;
    }

    public int getPlayerScore() {
        return m_Points;
    }

    public void addToPlayerScore(int i_PointsToAdd){
        m_Points += i_PointsToAdd;
    }

    public eColor getPlayerColor() {
        return m_PlayerColor;
    }

    public static Player CreatePlayer(PlayerData i_Player) {
        Player res;
        if(i_Player.getType().name().equals(ePlayerType.Human.name())){
            res = new HumanPlayer(i_Player.getID(), i_Player.getName(), i_Player.getType(), i_Player.getColor());
        }
        else{
            res = new ComputerPlayer(i_Player.getID(), i_Player.getName(), i_Player.getType(), i_Player.getColor());
        }

        return res;
    }

    public eColor getColor() {
        return m_PlayerColor;
    }
}
