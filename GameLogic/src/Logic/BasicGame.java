package Logic;
import Shared.GameInfo;

import java.util.ArrayList;

/**
 * Created by talza on 20/11/2016.
 */


public class BasicGame extends Game {

    @Override
    public void GetBoardToPrint() {
        //TODO: CHECK ITERATOR

        String board[][] = new String[m_Board.getBoardSize()][m_Board.getBoardSize()];

        for (int i=0; i< m_Board.getBoardSize(); i++) {
            for (int j=0; j< m_Board.getBoardSize(); j++) {
                board[i][j] = m_Board.getSquareInPos(i,j);
            }
        }

        m_GameInfo.setBoard(board);
    }


}
