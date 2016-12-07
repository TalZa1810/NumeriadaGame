package Logic;

/**
 * Created by talza on 22/11/2016.
 */
public class GameTypeFactory {

    private Game m_Game;


    public enum eGameType {
        BASIC, ADVANCED
    }

    public enum eBoardStructure {
        EXPLICIT, RANDOM
    }

    public GameTypeFactory(){

    }


    public GameTypeFactory(int i_NumberOfPlayers, int i_BoardSize ){

        if ( i_NumberOfPlayers == 2)
        {
            //m_Game = new BasicGame(2, i_BoardSize  );
            //    public BasicGame(int i_NumberOfPlayers,int i_BoardSize, eBoardStructure i_BoardStructure ){
        }

        //TODO: IN THE FUTURE MORE PLAYERS. CURRENTLY- EXCEPTION?

    }


}
