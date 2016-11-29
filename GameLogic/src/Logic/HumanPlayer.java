package Logic;

/**
 * Created by talza on 20/11/2016.
 */
public class HumanPlayer extends  Player {

    public HumanPlayer(ePlayerType i_PlayerType) {
        m_PlayerType = i_PlayerType;
    }

    @Override
    public void PickNumber(Square i_Square){

        if (i_Square.IsNumeric() == true) {
            super.m_Points+= i_Square.SquareNumber();
        }
        else{ //maybe using exception??

        }
    }
}
