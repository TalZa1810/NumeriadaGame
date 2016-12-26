package logic;


import shared.GameInfo;

public class AdvancedGame extends Game {



    public AdvancedGame(GameInfo[] i_GameInfoWrapper) {

        super(i_GameInfoWrapper);
    }

    @Override
    void setPlayers(int i_NumOfPlayers) {

        Player players[] = super.getPlayers();

    }


    @Override
    public void getBoardToPrint() {

    }
}
