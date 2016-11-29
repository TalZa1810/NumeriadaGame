package Manager;

import Logic.BasicGame;
import Shared.GameInfo;
import UI.GameUI;

import java.sql.Time;


/**
 * Created by Tal on 11/28/2016.
 */
public class GameManager {

    public GameUI m_GameUI;
    public BasicGame m_GameLogic;


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
                    if (fileLoaded == false) {
                        fileLoaded = true;
                        //LOAD FILE
                    }
                    break;
                case SET_GAME:
                    if (fileLoaded != false) {
                        //SET GAME AND SHOW BOARD
                    } else {
                        System.out.println("First load file");
                    }
                    break;
                case GAME_STATUS:
                    if (fileLoaded != false) {
                        GetGameStatus();
                    } else {
                        System.out.println("First load file");
                    }
                    break;
                case MAKE_MOVE:
                    if (fileLoaded != false) {
                        makeMove();
                    } else {
                        System.out.println("First load file");
                    }
                    break;
                case GET_STATISTICS:
                    if (fileLoaded != false) {
                        GetGameStatistics();
                    } else {
                        System.out.println("First load file");
                    }
                    break;
                case END_GAME:
                    fileLoaded = false;
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
}
