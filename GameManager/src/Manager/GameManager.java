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
    GameInfo m_GameInfo = new GameInfo();


    private enum eMenuOptions {
        LOAD_FILE, SET_GAME, GAME_STATUS, MAKE_MOVE, GET_STATISTICS, END_GAME, EXIT_GAME
    }



    public GameManager() {
        m_GameUI = new GameUI(m_GameInfo);
    }

    public void HandleMenuChoice() {
        boolean userWantsToPlay = true;
        boolean fileLoaded = false;

        while (userWantsToPlay) {

            GameUI.eMenuOptions menuSelection = GameUI.eMenuOptions.values()[m_GameUI.MainMenu()];
            switch (menuSelection) {
                case LOAD_FILE:
                    if (!fileLoaded) {
                        try {
                            fileLoaded = m_GameUI.fromXmlFileToObject();
                            fileLoaded = true;
                        }
                        catch(Exception e){
                            m_GameUI.ShowExceptionThrown(e.getMessage());
                            fileLoaded = false;
                        }
                    }
                    break;
                case SET_GAME:
                    if (fileLoaded) {
                        m_GameInfo.setGameMode(m_GameUI.getGameMode());
                        m_GameLogic = new BasicGame(m_GameInfo);
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
        m_GameInfo.setMove(m_GameUI.GetMoveFromUser());
        m_GameLogic.MakeMove();
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
        m_GameInfo.setRangeFrom(m_GameDescriptor.getBoard().getStructure().getRange().getFrom());
        m_GameInfo.setRangeTo(m_GameDescriptor.getBoard().getStructure().getRange().getTo());
        initBoard();
        setBoardValuesFromXML();


    }

    private void setBoardValuesFromXML() throws Exception {
        if(m_GameInfo.getBoardStructure().equals("Explicit")) {
            int row, col;

            List<GameDescriptor.Board.Structure.Squares.Square> squares = m_GameDescriptor.getBoard().getStructure().getSquares().getSquare();

            m_Validator.checkValidSquaresLocation(squares);

            for (GameDescriptor.Board.Structure.Squares.Square s : squares) {

                col = s.getColumn().intValue();
                row = s.getRow().intValue();
                m_GameInfo.setSquare(row, col, s.getValue().toString());

            }

            GameDescriptor.Board.Structure.Squares.Marker marker = m_GameDescriptor.getBoard().getStructure().getSquares().getMarker();
            m_GameInfo.setSquare(marker.getRow().intValue(), marker.getColumn().intValue(), "@");


        }
        else {
            m_Validator.checkRangeForRandomBoard();
        }
    }

    private  void initBoard (){
        for(String [] row: m_GameInfo.GetBoard()){
            for( String square: row){
                square = "";
            }
        }
    }

}
