package logic;

abstract class Player {

    private int m_Points = 0;
    private int m_ID;
    private String m_Name;
    private ePlayerType m_PlayerType;
    private ePlayerColor m_PlayerColor;

    public abstract void playTurn() {}

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
