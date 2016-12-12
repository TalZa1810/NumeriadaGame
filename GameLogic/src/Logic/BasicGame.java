package Logic;

import Shared.GameInfo;

/**
 * Created by talza on 20/11/2016.
 */


public class BasicGame extends Game {

    public BasicGame(GameInfo[] i_GameInfoWrapper) {
        super(i_GameInfoWrapper);
    }

    @Override
    //used only in ex1, can be erased for ex2 and 3
    public void GetBoardToPrint() {
        String board[][] = new String[m_Board.getBoardSize()][m_Board.getBoardSize()];

        for (int i=0; i< m_Board.getBoardSize() - 1; i++) {
            for (int j = 0; j< m_Board.getBoardSize() - 1; j++) {
                board[i][j] = m_Board.getSquareInPos(i,j).toString();
            }
        }

        m_GameInfo.setBoard(board);
    }

}
