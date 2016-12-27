package logic;

import shared.GameInfo;

public class BasicGame extends Game {

    enum ePlayerOrientation{
        COLUMN_PLAYER, ROW_PLAYER
    }

    public BasicGame(GameInfo[] i_GameInfoWrapper) {

        super(i_GameInfoWrapper);
    }

    /*@Override
    //used only in ex1, can be erased for ex2 and 3
    public void getBoardToPrint() {
        String board[][] = new String[m_Board.getBoardSize()][m_Board.getBoardSize()];

        for (int i=0; i< m_Board.getBoardSize(); i++) {
            for (int j = 0; j< m_Board.getBoardSize(); j++) {
                board[i][j] = m_Board.getSquareInPos(i,j).getSquareSymbol();
            }
        }

        m_GameInfo.setBoard(board);
    }*/
}
