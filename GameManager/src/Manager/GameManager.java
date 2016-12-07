package Manager;

import Logic.BasicGame;
import Logic.Game;
import Shared.GameInfo;
import UI.GameUI;


/**
 * Created by Tal on 11/28/2016.
 */
public class GameManager {

    private GameUI m_GameUI;
    private Game m_GameLogic;

    public enum eMenuOptions {
        LOAD_FILE, SET_GAME, GAME_STATUS, MAKE_MOVE, GET_STATISTICS, END_GAME, EXIT_GAME
    }

    public GameManager() {
        GameInfo m_GameInfo = new GameInfo();
        m_GameUI = new GameUI(m_GameInfo);
        m_GameLogic = new BasicGame(m_GameInfo);
    }

    public void HandleMenuChoice() {
        boolean userWantsToPlay = true;
        boolean fileLoaded = false;

        while (userWantsToPlay) {

            GameUI.eMenuOptions menuSelection = GameUI.eMenuOptions.values()[m_GameUI.MainMenu()];
            switch (menuSelection) {
                case LOAD_FILE:
                    if (!fileLoaded) {
                        fileLoaded = m_GameUI.fromXmlFileToObject();
                        //check input in engine
                    }
                    break;
                case SET_GAME:
                    if (fileLoaded) {
                        //INITIATE GAME
                        getBoard();
                    } else {
                        System.out.println("First load file");
                    }
                    break;
                case GAME_STATUS:
                    if (fileLoaded) {
                        GetGameStatus();
                    } else {
                        System.out.println("First load file");
                    }
                    break;
                case MAKE_MOVE:
                    if (fileLoaded) {
                        makeMove();
                    } else {
                        System.out.println("First load file");
                    }
                    break;
                case GET_STATISTICS:
                    if (fileLoaded) {
                        GetGameStatistics();
                    } else {
                        System.out.println("First load file");
                    }
                    break;
                case END_GAME:
                    fileLoaded = false;
                    System.out.println("Load new file to begin new game");
                    break;
                case EXIT_GAME:
                    userWantsToPlay = false;
                    System.out.println("BYE");
                    break;
                default:
                    userWantsToPlay = false;
            }
        }
    }

    private void GetGameStatus() {
        m_GameLogic.GetGameStatus(); //will update game info object (a basic game data member) in game logic
        m_GameUI.ShowStatus(); //will use the game info object (a game ui data member) in the game ui
    }

    private void GetGameStatistics() {
        m_GameLogic.GetGameStatistics();
        m_GameUI.ShowStatistics();
    }

    private void makeMove() {
        m_GameLogic.getCurrMarkerPosition();
        int move = m_GameUI.GetMoveFromUser();
        m_GameLogic.MakeMove(move);
    }

    private void getBoard() {
        m_GameLogic.GetBoardToPrint(); //copy board to char[][] in game info object or do something so the ui knows the board and implement to string for square and to string for board
        m_GameUI.ShowBoard(); //will use the game info object (a game ui data member) in the game ui
    }
}
