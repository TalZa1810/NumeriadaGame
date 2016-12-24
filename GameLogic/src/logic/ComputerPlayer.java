package logic;

import shared.GameInfo;

import java.util.Random;

public class ComputerPlayer extends Player {


    @Override
    public void playTurn(Board i_Board, GameInfo i_GameInfo , Player i_Player, int i_Move) {
        //TODO: mske polymorphism work

        Random r = new Random();
        int randomMove = r.nextInt(i_Board.getBoardSize());

        if(i_Player.getPlayerType() == Player.ePlayerType.COLUMN_PLAYER) {
            while(i_Board.getSquareInPos(randomMove, i_GameInfo.getMarkerCol()).getSquareSymbol().equals("") ||
                    i_Board.getSquareInPos(randomMove, i_GameInfo.getMarkerCol()).getSquareSymbol().equals("@")){
                randomMove = r.nextInt(i_Board.getBoardSize());
            }
        }
        else {
            while(i_Board.getSquareInPos(i_GameInfo.getMarkerRow(), randomMove).getSquareSymbol().equals("") ||
                    i_Board.getSquareInPos(i_GameInfo.getMarkerRow(), randomMove).getSquareSymbol().equals("@")){
                randomMove = r.nextInt(i_Board.getBoardSize());
            }
        }

        super.playTurn(i_Board, i_GameInfo , i_Player, randomMove);
    }
}


