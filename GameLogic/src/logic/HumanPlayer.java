package logic;

import shared.GameInfo;


public class HumanPlayer extends  Player {

    @Override
    public void playTurn(Board i_Board, GameInfo i_GameInfo , Player i_Player, int i_Move) {
        //TODO: make polymorphism work
        super.playTurn(i_Board, i_GameInfo , i_Player, i_Move);
    }
}
