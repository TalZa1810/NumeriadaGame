package Logic;

class Player {

    private int m_Points = 0;
    private ePlayerType m_PlayerType;
    //// TODO: PLAYER ID

    enum ePlayerType {
        ROW_PLAYER, COLUMN_PLAYER
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
