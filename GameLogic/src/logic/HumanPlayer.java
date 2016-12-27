package logic;

import shared.GameInfo;
import sharedStructures.eColor;
import sharedStructures.ePlayerType;


public class HumanPlayer extends  Player {

    public HumanPlayer(int i_ID, String i_Name, ePlayerType i_Type, eColor i_Color){
        super(i_ID, i_Name, i_Type, i_Color);
    }

    @Override
    public void playTurn(Board i_Board, GameInfo i_GameInfo , Square i_ChosenSquare) {

        //TODO: make polymorphism work
        super.playTurn(i_Board, i_GameInfo , i_ChosenSquare);
    }
}
