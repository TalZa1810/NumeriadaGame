package logic;

import shared.GameInfo;
import sharedStructures.eColor;
import sharedStructures.ePlayerType;

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

    public void playTurn(Board i_Board, GameInfo i_GameInfo , Square i_ChosenSquare) {

        if ( i_ChosenSquare != null ){
            addToPlayerScore(Integer.parseInt(i_ChosenSquare.getSquareSymbol()));
            i_Board.changeMarker(i_Board.getMark(), i_ChosenSquare);
        }
    }



   /* String getEPlayerTypeAsString(){
        if (m_PlayerType == BasicGame.ePlayerType.COLUMN_PLAYER){
            return ("column");
        }

        return ("row");
    }*/

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


    public static Player CreatePlayer(int i_ID, String i_Name, eColor i_Color, ePlayerType i_Type) {
        Player res;
        if(i_Type.name().equals(ePlayerType.HUMAN.name())){
            res = new HumanPlayer(i_ID, i_Name, i_Type, i_Color);
        }
        else{
            res = new ComputerPlayer(i_ID, i_Name, i_Type, i_Color);
        }

        return res;
    }
}
