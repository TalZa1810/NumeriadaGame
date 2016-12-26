package logic;

import shared.GameInfo;

abstract class Player {

    private int m_Points = 0;
    private int m_ID;
    private String m_Name;
    private ePlayerType m_PlayerType;
    private ePlayerColor m_PlayerColor;


    public Player(){

    }

    enum ePlayerColor {
        RED, BLUE, YELLOW, GREEN, ORANGE, PURPLE, BLACK
    }

    public void playTurn(Board i_Board, GameInfo i_GameInfo , Square i_ChosenSquare) {

        if ( i_ChosenSquare != null ){
            addToPlayerScore(Integer.parseInt(i_ChosenSquare.getSquareSymbol()));
            i_Board.changeMarker(i_Board.getMark(), i_ChosenSquare);
        }
    }

    //TODO: create specific types
    enum ePlayerType {
        HUMAN, COMPUTER
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

    public ePlayerColor getPlayerColor() {
        return m_PlayerColor;
    }
}
