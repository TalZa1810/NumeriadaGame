package Manager;

import Generated.GameDescriptor;
import Logic.BasicGame;
import Logic.Game;
import Shared.GameInfo;
import Shared.Validator;
import UI.GameUI;

import java.util.List;

public class GameManager {

    private GameDescriptor m_GameDescriptor = new GameDescriptor();
    private Validator m_Validator;
    private GameUI m_GameUI;
    private Game m_GameLogic;
    private GameInfo m_GameInfo = new GameInfo();
    private GameInfo[] m_GameInfoWrapper = new GameInfo[1];

    private enum eMenuOptions {
        LOAD_FILE, SET_GAME, GAME_STATUS, MAKE_MOVE, GET_STATISTICS, END_GAME, EXIT_GAME
    }

    public GameManager() {
        m_GameInfoWrapper[0] = m_GameInfo;
        m_GameUI = new GameUI(m_GameInfoWrapper);
    }

    public void HandleMenuChoice() {
        boolean userWantsToPlay = true;
        boolean fileLoaded = false;
        boolean isGameSet = false;

        while (userWantsToPlay) {

            GameUI.eMenuOptions menuSelection = GameUI.eMenuOptions.values()[m_GameUI.MainMenu()];
            switch (menuSelection) {
                case LOAD_FILE:
                    if (!fileLoaded) {
                        try {
                            m_GameDescriptor = m_GameUI.fromXmlFileToObject();
                            if(m_GameDescriptor != null) {
                                fileLoaded = true;
                                GetDataFromGeneratedXML();
                                m_GameUI. FileWasLoadedSuccessfully();
                            }
                        }
                        catch(Exception e){
                            m_GameUI.ShowExceptionThrown(e.getMessage());
                        }
                    }
                    else{
                        m_GameUI.notifyOngoingGame();
                    }
                    break;
                case SET_GAME:
                    if (fileLoaded) {
                        if(isGameSet){
                            m_GameUI.notifyOngoingGame();
                        }
                        else {
                            m_GameInfo.setGameMode(m_GameUI.getGameMode());
                            m_GameLogic = new BasicGame(m_GameInfoWrapper);
                            getBoard();
                            isGameSet = true;
                            m_GameLogic.start();
                        }
                    } else {
                        m_GameUI.notifyShouldLoadFile();
                    }
                    break;
                case GAME_STATUS:
                    if (fileLoaded) {
                        if(isGameSet){
                            GetGameStatus();
                        }
                        else{
                            m_GameUI.notifyShouldSetGame();
                        }
                    } else {
                        m_GameUI.notifyShouldLoadFile();
                    }
                    break;
                case MAKE_MOVE:
                    if (fileLoaded) {
                        if (isGameSet){
                            makeMove();
                        }
                        else{
                            m_GameUI.notifyShouldSetGame();
                        }
                    } else {
                        m_GameUI.notifyShouldLoadFile();
                    }
                    break;
                case GET_STATISTICS:
                    if (fileLoaded) {
                        if(isGameSet) {
                            GetGameStatistics();
                        }
                        else{
                            m_GameUI.notifyShouldSetGame();
                        }
                    } else {
                        m_GameUI.notifyShouldLoadFile();
                    }
                    break;
                case END_GAME:
                    fileLoaded = false;
                     if (isGameSet){
                         isGameSet = false;
                         GetGameStatistics();
                         m_GameUI.notifyGameEndedByUser();
                     } else{
                         m_GameUI.notifyGameWasNotSet();
                     }
                    m_GameUI.notifyLoadNewGame();

                    break;
                case EXIT_GAME:
                    userWantsToPlay = false;
                    m_GameUI.notifyPlayerExitGame();
                    //System.out.println("BYE");
                    break;
                default:
                    userWantsToPlay = false;
            }
        }
    }

    /*
        private void notifyGameEnded() {
        GetGameStatistics();
        m_GameUI.notifyGameEndedByUser();
    }
    * */

    private void GetGameStatus() {
        m_GameLogic.GetGameStatus(); //will update game info object (a basic game data member) in game logic
        m_GameUI.ShowStatus(); //will use the game info object (a game ui data member) in the game ui
    }

    private void GetGameStatistics() {
        m_GameLogic.GetGameStatistics();
        m_GameUI.ShowStatistics();
    }

    private void makeMove() {
        boolean validInput;

        m_GameLogic.getCurrMarkerPosition();
        m_GameLogic.LoadCurrPlayerToGameInfo();
        if(m_GameInfo.GetCurrPlayer().equals("row") || (!m_GameInfo.GetCurrPlayer().equals("row") && m_GameInfo.getGameMode() == 1)) {
            m_GameInfo.setMove(m_GameUI.GetMoveFromUser());
            validInput = m_GameLogic.checkIfLegalMove(m_GameInfo.getMove() - 1);

            while(!validInput){
                m_GameInfo.setMove(m_GameUI.GetMoveFromUser());
            }
        }
        boolean gameDone = m_GameLogic.MakeMove();
        getBoard();
        if(gameDone){
            m_GameUI.notifyGameDone();
            GetGameStatistics();
        }
    }

    private void getBoard() {
        m_GameLogic.GetBoardToPrint(); //copy board to char[][] in game info object or do something so the ui knows the board and implement to string for square and to string for board
        m_GameUI.ShowBoard(); //will use the game info object (a game ui data member) in the game ui
    }

    public void GetDataFromGeneratedXML() throws Exception {
        m_Validator = new Validator(m_GameInfo);

        m_GameInfo.setGameType(m_GameDescriptor.getGameType());
        m_Validator.checkBoardSize(m_GameDescriptor.getBoard().getSize().intValue());
        m_GameInfo.setBoardSize(m_GameDescriptor.getBoard().getSize().intValue());
        m_GameInfo.setBoardStructure(m_GameDescriptor.getBoard().getStructure().getType());

        if(m_GameInfo.getBoardStructure().toString().equals("Random")) {
            m_GameInfo.setRangeFrom(m_GameDescriptor.getBoard().getStructure().getRange().getFrom());
            m_GameInfo.setRangeTo(m_GameDescriptor.getBoard().getStructure().getRange().getTo());
        }

        m_GameInfo.initBoard();
        setBoardValuesFromXML();
    }

    private void setBoardValuesFromXML() throws Exception {
        String str = m_GameInfo.getBoardStructure();
        if(m_GameInfo.getBoardStructure().equals("Explicit")) {
            int row, col;

            List<GameDescriptor.Board.Structure.Squares.Square> squares = m_GameDescriptor.getBoard().getStructure().getSquares().getSquare();

            m_Validator.checkValidSquaresLocation(squares);

            for (GameDescriptor.Board.Structure.Squares.Square s : squares) {

                col = s.getColumn().intValue() - 1;
                row = s.getRow().intValue() - 1;
                m_GameInfo.setSquare(row, col, s.getValue().toString());

            }

            GameDescriptor.Board.Structure.Squares.Marker marker = m_GameDescriptor.getBoard().getStructure().getSquares().getMarker();
            m_GameInfo.setSquare(marker.getRow().intValue() - 1, marker.getColumn().intValue() - 1, "@");
        }
        else {
            m_Validator.checkRangeForRandomBoard();
        }
    }
}
