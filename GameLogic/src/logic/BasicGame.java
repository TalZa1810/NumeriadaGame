package logic;

import shared.GameInfo;
import sharedStructures.PlayerData;
import sharedStructures.eColor;
import sharedStructures.ePlayerType;

import java.util.ArrayList;

public class BasicGame extends Game {

    enum ePlayerOrientation{
        COLUMN_PLAYER, ROW_PLAYER
    }

    @Override
    public boolean checkIfNotPossibleMove() {
        return true;
    }

    public BasicGame(GameInfo[] i_GameInfoWrapper) {
        super(i_GameInfoWrapper);
    }

    @Override
    void setPlayers() {
        m_Players = new ArrayList<Player>();
        String name = "Row player";
        for(int i = 0; i < m_GameInfo.getNumOfPlayers(); i++){
            m_Players.add(new HumanPlayer(i, name, ePlayerType.Human, eColor.BLACK));
            name = "Column player";
        }

        loadPlayersToGameInfo();
    }

    private void loadPlayersToGameInfo() {
        for(int i = 0; i < m_GameInfo.getNumOfPlayers(); i++){
            m_GameInfo.getPlayers().add(new PlayerData(m_Players.get(i).getName(), m_Players.get(i).getID(), m_Players.get(i).getColor(),m_Players.get(i).getPlayerType(), m_Players.get(i).getPlayerScore()));
        }
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

    @Override
    public void playerQuit() {
        //nothing special to update here. Abstract method is for advanced game
    }

}
