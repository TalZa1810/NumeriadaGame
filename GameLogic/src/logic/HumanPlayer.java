package logic;

/**
 * Created by talza on 20/11/2016.
 */
public class HumanPlayer extends  Player {

    @Override
    public void playTurn(Player i_Player) {
        //TODO: mske polymorphism work
        Square squareToChange;
        Square markerToChange;

        if(i_Player.getPlayerType() == Player.ePlayerType.COLUMN_PLAYER) {
            squareToChange = (Square)m_Board.getSquareInPos( m_GameInfo.getMarkerRow(), m_GameInfo.getMarkerCol());
            markerToChange = (Square)m_Board.getSquareInPos(i_Move, m_GameInfo.getMarkerCol());
        }
        else {
            squareToChange = (Square)m_Board.getSquareInPos( m_GameInfo.getMarkerRow(),m_GameInfo.getMarkerCol());
            markerToChange = (Square)m_Board.getSquareInPos( m_GameInfo.getMarkerRow(),i_Move);
        }

        m_CurrentPlayer.addToPlayerScore(Integer.parseInt(markerToChange.getSquareSymbol()));

        m_Board.changeMarker(squareToChange, markerToChange);
    }
}
