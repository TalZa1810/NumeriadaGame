package logic;

import shared.GameInfo;
import sharedStructures.eColor;

public class AdvancedGame extends Game {



    public AdvancedGame(GameInfo[] i_GameInfoWrapper) {

        super(i_GameInfoWrapper);
    }

    @Override
    protected void initRandomBoardSquare(int randomRow, int randomCol, int colorIndex, int data) {
        m_Board.getSquareInPos(randomRow,randomCol).setSquareSymbol(Integer.toString(data));
        m_Board.getSquareInPos(randomRow,randomCol).setColor(eColor.values()[(colorIndex + 1) % m_GameInfo.getNumOfPlayers()]);
    }

    @Override
    boolean checkIfLegalMove(Player i_Player, Square i_Move) {
        boolean res = true;
        if(i_Move.getColumn() == m_Board.getMark().getColumn()) {
            if (i_Move.getSquareSymbol().equals(m_Board.getMark().getSquareSymbol())
                    || i_Move.getSquareSymbol().equals("")
                    || i_Move.getColor() != i_Player.getPlayerColor()) {
                res = false;
            }
        }
        else if(i_Move.getRow() == m_Board.getMark().getRow()){
            if (i_Move.getSquareSymbol().equals(m_Board.getMark().getSquareSymbol())
                    || i_Move.getSquareSymbol().equals("")) {
                res = false;
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
        Square squareToCheck;

        for(int i = 0; i < m_Board.getBoardSize(); i++) {
            //check if row is empty of numbers
            squareToCheck = m_Board.getSquareInPos(m_Board.getMark().getRow(), i);
            if (!squareToCheck.getSquareSymbol().equals(m_Board.getMark().getSquareSymbol()) &&
                    !m_Board.getSquareInPos(m_Board.getMark().getRow(), i).getSquareSymbol().equals("")) {
                gameDone = false;
                break;
            }
        }

        for(int i = 0; i < m_Board.getBoardSize(); i++){
            //check if column is empty of numbers
            squareToCheck = m_Board.getSquareInPos(i,m_Board.getMark().getColumn());
            if(!squareToCheck.getSquareSymbol().equals(m_Board.getMark().getSquareSymbol()) &&
                    !m_Board.getSquareInPos(i, m_Board.getMark().getColumn()).getSquareSymbol().equals("")){
                gameDone = false;
                break;
            }
        }

        return gameDone;
    }
}
