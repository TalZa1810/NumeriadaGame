package Logic;

/**
 * Created by talza on 20/11/2016.
 */
public class Player {

    private int m_Points = 0;
    private ePlayerType m_PlayerType;

    //// TODO: PLAYER ID

    public enum ePlayerType {
        ROW_PLAYER, COLUMN_PLAYER
    }

    public String GetEPlayerTypeAsString(){
        if (m_PlayerType == ePlayerType.COLUMN_PLAYER){
            return ("column");
        }

        return ("row");
    }

    public ePlayerType getPlayerType() {
        return m_PlayerType;
    }

    public void setPlayerType(ePlayerType i_PlayerType) {
        this.m_PlayerType = i_PlayerType;
    }

    public int getPlayerScore() {
        return m_Points;
    }

    public void addToPlayerScore(int i_PointsToAdd){
        m_Points += i_PointsToAdd;
    }



}
