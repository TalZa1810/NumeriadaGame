package Logic;

/**
 * Created by talza on 20/11/2016.
 */
public abstract class Player {

    protected int m_Points = 0;
    protected ePlayerType m_PlayerType;

    //// TODO: PLAYER ID

    public enum ePlayerType {
        ROW_PLAYER, COLUMN_PLAYER
    }

    //METHODS
    public abstract void PickNumber(Square i_Square);
}
