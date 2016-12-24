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

    public void playTurn(Board i_Board, GameInfo i_GameInfo , Player i_Player, int i_Move) {

        Square squareToChange;
        Square markerToChange;

        if(i_Player.getPlayerType() == Player.ePlayerType.COLUMN_PLAYER) {
            squareToChange = i_Board.getSquareInPos( i_GameInfo.getMarkerRow(), i_GameInfo.getMarkerCol());
            markerToChange = i_Board.getSquareInPos(i_Move, i_GameInfo.getMarkerCol());
        }
        else {
            squareToChange = i_Board.getSquareInPos( i_GameInfo.getMarkerRow(),i_GameInfo.getMarkerCol());
            markerToChange = i_Board.getSquareInPos( i_GameInfo.getMarkerRow(),i_Move);
        }

        addToPlayerScore(Integer.parseInt(markerToChange.getSquareSymbol()));

        i_Board.changeMarker(squareToChange, markerToChange);
    }

    enum ePlayerType {
        HUMAN, COMPUTER
    }

    enum ePlayerColor {
        RED, BLUE, YELLOW, GREEN, ORANGE, PURPLE
    }

    String getEPlayerTypeAsString(){
        if (m_PlayerType == ePlayerType.COLUMN_PLAYER){
            return ("column");
        }

        return ("row");
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
}
