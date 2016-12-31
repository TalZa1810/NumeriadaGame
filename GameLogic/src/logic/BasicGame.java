package logic;

import shared.GameInfo;

public class BasicGame extends Game {

    enum ePlayerOrientation{
        COLUMN_PLAYER, ROW_PLAYER
    }

    public BasicGame(GameInfo[] i_GameInfoWrapper) {

        super(i_GameInfoWrapper);
    }

    @Override
    protected void initRandomBoardSquare(int randomRow, int randomCol, int colorIndex, int data) {
        m_Board.getSquareInPos(randomRow,randomCol).setSquareSymbol(Integer.toString(data));
    }

    @Override
    boolean checkIfLegalMove(Player i_Player, Square i_Move) {
        boolean res = true;
        if(i_Move.getColumn() == m_Board.getMark().getColumn()) {
            if (i_Move.getSquareSymbol().equals(m_Board.getMark().getSquareSymbol())
                    || i_Move.getSquareSymbol().equals("")) {
                res = false;
            }
        }
        else if((i_Move.getRow() == m_Board.getMark().getRow())){
            if (i_Move.getSquareSymbol().equals(m_Board.getMark().getSquareSymbol())
                    || i_Move.getSquareSymbol().equals("")) {
                res =  false;
            }
        }
        else if(i_Move.getColumn() != m_Board.getMark().getColumn() && i_Move.getRow() != m_Board.getMark().getRow()){
            res = false;
        }

        return res;
    }

    @Override
    public boolean checkIfGameDone() {
        boolean gameDone = true;

        if(m_CurrentPlayer.equals(m_Players.get(0))){
            for(int i = 0; i < m_Board.getBoardSize(); i++) {
                //check if row is empty of numbers
                if (!m_Board.getSquareInPos(m_Board.getMark().getRow(), i).getSquareSymbol().equals(m_Board.getMark().getSquareSymbol()) &&
                        !m_Board.getSquareInPos(m_Board.getMark().getRow(), i).getSquareSymbol().equals("")) {
                    gameDone = false;
                    break;
                }
            }
        }
        else{
            for(int i = 0; i < m_Board.getBoardSize(); i++){
                //check if column is empty of numbers
                if(!m_Board.getSquareInPos(i,m_Board.getMark().getColumn()).getSquareSymbol().equals(m_Board.getMark().getSquareSymbol()) &&
                        !m_Board.getSquareInPos(i, m_Board.getMark().getColumn()).getSquareSymbol().equals("")){
                    gameDone = false;
                    break;
                }
            }
        }
        return gameDone;
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
