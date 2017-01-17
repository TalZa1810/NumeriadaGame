package manager;

import Generated.GameDescriptor;
import logic.AdvancedGame;
import logic.BasicGame;
import logic.Game;
import shared.GameInfo;
import shared.Validator;
import ui.GameUI;

public class GameManager {

    private GameDescriptor m_GameDescriptor = new GameDescriptor();
    private Validator m_Validator;
    private GameUI m_GameUI;
    private Game m_GameLogic;
    private GameInfo m_GameInfo = new GameInfo();
    private GameInfo[] m_GameInfoWrapper = new GameInfo[1];

    /*private enum eMenuOptions {
        LOAD_FILE, SET_GAME, GAME_STATUS, MAKE_MOVE, GET_STATISTICS, END_GAME, EXIT_GAME
    }*/

    public GameManager() {
        m_GameInfoWrapper[0] = m_GameInfo;
        m_GameUI = new GameUI(m_GameInfoWrapper);
    }

    public void run() {
        handleMenuChoice();
    }

    private void handleMenuChoice() {
        boolean userWantsToPlay = true;
        boolean fileLoaded = false;
        boolean isGameSet = false;

        while (userWantsToPlay) {

            GameUI.eMenuOptions menuSelection = GameUI.eMenuOptions.values()[m_GameUI.mainMenu()];
            switch (menuSelection) {
                case LOAD_FILE:
                    if (!fileLoaded) {
                        try {
                            m_GameDescriptor = m_GameUI.fromXmlFileToObject();
                            if(m_GameDescriptor != null) {
                                fileLoaded = true;
                                //getDataFromGeneratedXML();
                                m_GameUI.fileWasLoadedSuccessfully();
                            }
                        }
                        catch(Exception e){
                            m_GameUI.showExceptionThrown(e.getMessage());
                            fileLoaded = false;
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
                            //if(!m_GameInfo.getPath().equals("")){
                            //    m_GameUI.unmarshalFile(m_GameInfo.getPath());
                            //}
                            m_GameInfo.setGameMode(m_GameUI.getGameMode());

                            //advanced or basic
                            if (m_GameInfo.getGameType().equals("Basic") ) {
                                m_GameLogic = new BasicGame(m_GameInfoWrapper);
                            }
                            else{
                                m_GameLogic = new AdvancedGame(m_GameInfoWrapper);
                            }

                            getBoard();
                            isGameSet = true;
                            m_GameLogic.start();
                            boolean gameDone = m_GameLogic.checkIfGameDone();
                            if(gameDone){
                                gameDoneProcedure();
                            }
                        }
                    } else {
                        m_GameUI.notifyShouldLoadFile();
                    }
                    break;
                case GAME_STATUS:
                    if (fileLoaded) {
                        if(isGameSet){
                            getGameStatus();
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
                            getGameStatistics();
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
                         getGameStatistics();
                         m_GameUI.notifyGameEndedByUser();
                     } else{
                         m_GameUI.notifyGameWasNotSet();
                     }
                    m_GameUI.notifyLoadNewGame();

                    break;
                case EXIT_GAME:
                    userWantsToPlay = false;
                    m_GameUI.notifyPlayerExitGame();
                   break;
                default:
                    userWantsToPlay = false;
            }
        }
    }


    private void getGameStatus() {
        m_GameLogic.getGameStatus(); //will update game info object (a basic game data member) in game logic
        m_GameUI.showStatus(); //will use the game info object (a game ui data member) in the game ui
    }

    private void getGameStatistics() {
        m_GameLogic.getGameStatistics();
        m_GameUI.showStatistics();
    }

    private void makeMove() {
        boolean validInput;

        m_GameLogic.getCurrMarkerPosition();
        m_GameLogic.loadCurrPlayerAndNumOfMovesToGameInfo();
        //if(m_GameInfo.getCurrPlayer().equals("row") || (!m_GameInfo.getCurrPlayer().equals("row") && m_GameInfo.getGameMode() == 1)) {
            m_GameInfo.setMove(m_GameUI.getMoveFromUser());
            validInput = m_GameLogic.checkMove(m_GameInfo.getChosenRow(), m_GameInfo.getChosenCol());

            while(!validInput){
                m_GameUI.notifyInvalidSquareChoice();
                m_GameInfo.setMove(m_GameUI.getMoveFromUser());
                validInput = m_GameLogic.checkMove(m_GameInfo.getChosenRow(), m_GameInfo.getChosenCol());
            }
        //}
        boolean gameDone = m_GameLogic.makeMove();
        //getBoard();
        if(gameDone){
            gameDoneProcedure();
        }
    }

    private void gameDoneProcedure(){
        m_GameUI.notifyGameDone();
        getGameStatistics();
        if(m_GameInfo.getRowPlayerScore() != m_GameInfo.getColPlayerScore()) {
            m_GameUI.announceWinner(Math.max(m_GameInfo.getRowPlayerScore(), m_GameInfo.getColPlayerScore()), m_GameInfo.getCurrPlayer());
        }
        else{
            m_GameUI.announceTie(m_GameInfo.getRowPlayerScore());
        }
    }

    private void getBoard() {
        //m_GameLogic.getBoardToPrint(); //copy board to char[][] in game info object or do something so the ui knows the board and implement to string for square and to string for board
        m_GameUI.showBoard(); //will use the game info object (a game ui data member) in the game ui
    }
/*
    private void getDataFromGeneratedXML() throws Exception {
        m_Validator = new Validator(m_GameInfo);

        m_GameInfo.setGameType(m_GameDescriptor.getGameType());
        m_Validator.checkBoardSize(m_GameDescriptor.getBoard().getSize().intValue());
        m_GameInfo.setBoardSize(m_GameDescriptor.getBoard().getSize().intValue());
        m_GameInfo.setBoardStructure(m_GameDescriptor.getBoard().getStructure().getType());

        if(m_GameInfo.getBoardStructure().equals("Random")) {
            m_GameInfo.setRangeFrom(m_GameDescriptor.getBoard().getStructure().getRange().getFrom());
            m_GameInfo.setRangeTo(m_GameDescriptor.getBoard().getStructure().getRange().getTo());
        }

        getPlayersFromXML();
        m_GameInfo.initBoard();
        setBoardValuesFromXML();
    }

    private void getPlayersFromXML() throws Exception {
        PlayerData player = new PlayerData();
        List<GameDescriptor.Players.Player> players = m_GameDescriptor.getPlayers().getPlayer();
        m_Validator.checkValidPlayersID(players);

        for(GameDescriptor.Players.Player p: players){
            player.setName(p.getName());
            player.setID(p.getId().intValue());
            player.setColor(eColor.values()[p.getColor() - 1]);

            if(p.getType().equals(ePlayerType.Human.name())){
                player.setType(ePlayerType.Human);
            }
            else{
                player.setType(ePlayerType.Computer);
            }

            m_GameInfo.addPlayerData(player);
        }
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

            m_Validator.checkValidMarkerLocation(m_GameDescriptor.getBoard().getStructure().getSquares().getMarker());
            GameDescriptor.Board.Structure.Squares.Marker marker = m_GameDescriptor.getBoard().getStructure().getSquares().getMarker();
            m_GameInfo.setSquare(marker.getRow().intValue() - 1, marker.getColumn().intValue() - 1, "@");
        }
        else {
            m_Validator.checkRangeForRandomBoard(m_GameInfo.getGameType());
        }
    }*/
}
