package javafx_ui.gamePane;

import Generated.GameDescriptor;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx_ui.boardPane.BoardController;
import javafx_ui.playersPane.PlayersController;
import logic.AdvancedGame;
import logic.BasicGame;
import logic.Game;
import shared.GameInfo;
import shared.Validator;
import sharedStructures.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable{

    private static final String PLAYERS_SCENE_FXML_PATH = "/javafx_ui/playersPane/PlayersPane.fxml";
    private static final String BOARD_SCENE_FXML_PATH = "/javafx_ui/boardPane/BoardPane3.fxml";
    private static final int MAX_PLAYERS = 6;
    private Validator m_Validator = new Validator();
    private GameDescriptor m_GameDescriptor = new GameDescriptor();
    private Notifier m_Notifier = new Notifier();
    private GameInfo m_GameInfo = new GameInfo();
    private GameInfo[] m_GameInfoWrapper = new GameInfo[1];
    private Game m_Logic;
    private BoardController m_Board;
    private PlayersController m_Players;
    private Stage m_PrimaryStage;


    @FXML    private BorderPane m_MainWindow;
    @FXML    private TextField pathTextBox;
    @FXML    private javafx.scene.control.Button browseButton;
    @FXML    private javafx.scene.control.Button loadButton;
    @FXML    private TextArea statusBarText;
    @FXML    private javafx.scene.control.Button makeMoveButton;
    @FXML    private javafx.scene.control.Button prevMoveButton;
    @FXML    private javafx.scene.control.Button nextMoveButton;
    @FXML    private GridPane boardGrid;
    @FXML    private HBox player1;
    @FXML    private Label playerColor1;
    @FXML    private Label playerID1;
    @FXML    private Label playerName1;
    @FXML    private Label playerScore1;
    @FXML    private HBox player2;
    @FXML    private Label playerColor2;
    @FXML    private Label playerID2;
    @FXML    private Label playerName2;
    @FXML    private Label playerScore2;
    @FXML    private HBox player3;
    @FXML    private Label playerColor3;
    @FXML    private Label playerID3;
    @FXML    private Label playerName3;
    @FXML    private Label playerScore3;
    @FXML    private HBox player4;
    @FXML    private Label playerColor4;
    @FXML    private Label playerID4;
    @FXML    private Label playerName4;
    @FXML    private Label playerScore4;
    @FXML    private HBox player5;
    @FXML    private Label playerColor5;
    @FXML    private Label playerID5;
    @FXML    private Label playerName5;
    @FXML    private Label playerScore5;
    @FXML    private HBox player6;
    @FXML    private Label playerColor6;
    @FXML    private Label playerID6;
    @FXML    private Label playerName6;
    @FXML    private Label playerScore6;

    private SimpleIntegerProperty[] m_PlayersID;
    private SimpleStringProperty[] m_PlayersNames;
    private SimpleIntegerProperty[] m_PlayersScore;

    private SimpleStringProperty m_FilePath = new SimpleStringProperty("");
    private SimpleStringProperty m_StatusBar = new SimpleStringProperty("");
    private SimpleBooleanProperty m_isFileSelected = new SimpleBooleanProperty();




    public GameController() {
        m_GameInfoWrapper[0] = m_GameInfo;
    }

    public void initializeGameController(BorderPane i_GameLayout){
        m_Board = new BoardController(m_GameInfoWrapper, boardGrid);
        m_Players = new PlayersController(m_GameInfoWrapper);
    }

    private void createGame() {
        if(m_GameInfo.getGameType().equals("Basic")){
            m_Logic = new BasicGame(m_GameInfoWrapper);
        }
        else{
            m_Logic = new AdvancedGame(m_GameInfoWrapper);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pathTextBox.textProperty().bind(Bindings.format("%s", m_FilePath ));
        loadButton.disableProperty().bind(m_isFileSelected.not());
        statusBarText.textProperty().bind(Bindings.format("%s", m_StatusBar));

    }


    @FXML
    public void browseButtonClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select XML File");
        fileChooser.setInitialDirectory(new File("C:\\"));
        File selectedFile = fileChooser.showOpenDialog(m_PrimaryStage);
        if (selectedFile ==  null){
            return;
        }
        m_StatusBar.set("");
        String path = selectedFile.getAbsolutePath();
        if(m_Validator.checkValidPath(path)) {
            m_FilePath.set(path);
            m_isFileSelected.set(true); //toggle
        }
        else {
          m_StatusBar.set("Invalid file. File suffix must be .xml");
        }
    }

    @FXML
    public void loadButtonClicked(){
        try {
            m_GameDescriptor = fromXmlFileToObject();
            if(m_GameDescriptor != null) {
                getDataFromGeneratedXML();
                m_StatusBar.set(m_Notifier.fileWasLoadedSuccessfully());
                createGame();
                initializeGameController(m_MainWindow);
            }
        }
        catch(Exception e){
            m_StatusBar.set(m_Notifier.showExceptionThrown(e.getMessage()));
        }
    }

    public void setPrimaryStage(Stage i_PrimaryStage) {
        m_PrimaryStage = i_PrimaryStage;
    }




    /*
    private void createPlayersPane(){
        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource(PLAYERS_SCENE_FXML_PATH);
        loader.setLocation(mainFXML);
        try{
            Node playersPane = loader.load();

            PlayersController playersController = loader.getController();
            m_MainWindow.setRight(playersPane);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void createBoardPane() {
        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource(BOARD_SCENE_FXML_PATH);
        loader.setLocation(mainFXML);
        try {
            //TODO: SHOULD CHECK FXML FILE
            Node boardPane = loader.load();

            BoardController boardController = loader.getController();
            m_Board.initializeBoard();
            m_MainWindow.setCenter(boardPane);
        } catch(IOException e){
            e.printStackTrace();
        }
    }*/


    public GameDescriptor fromXmlFileToObject() {
        return unmarshalFile(m_FilePath.getValue());
    }

    private GameDescriptor unmarshalFile(String i_Path){
        GameDescriptor descriptor = null;

        try {

            File file = new File(i_Path);
            JAXBContext jaxbContext = JAXBContext.newInstance(GameDescriptor.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            descriptor = (GameDescriptor) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            e.printStackTrace();
            m_StatusBar.set("Failed to load file");
        }

        return descriptor;
    }

    private void getDataFromGeneratedXML() throws Exception {
        m_Validator = new Validator(m_GameInfo);

        m_GameInfo.setGameType(m_GameDescriptor.getGameType());
        //TODO: RESULT IS UNDEFINED
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
        //TODO: RETURNS NULL
        List<GameDescriptor.Players.Player> players = m_GameDescriptor.getPlayers().getPlayer();
        m_Validator.checkValidPlayersID(players);

        for(GameDescriptor.Players.Player p: players){
            PlayerData player = new PlayerData();
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
                m_GameInfo.setSquare(row, col, s.getValue().toString(), s.getColor());
            }

            m_Validator.checkValidMarkerLocation(m_GameDescriptor.getBoard().getStructure().getSquares().getMarker());
            GameDescriptor.Board.Structure.Squares.Marker marker = m_GameDescriptor.getBoard().getStructure().getSquares().getMarker();
            //m_GameInfo.setSquare(marker.getRow().intValue() - 1, marker.getColumn().intValue() - 1, "@", eColor.DEFAULT.ordinal());
            m_GameInfo.setSquare(marker.getRow().intValue() - 1, marker.getColumn().intValue() - 1, "@", eColor.BLACK.ordinal());
        }
        else {
            m_Validator.checkRangeForRandomBoard(m_GameInfo.getGameType());
        }
    }

    private void makeMove() {
        boolean validInput;

        m_Logic.getCurrMarkerPosition();
        //m_Logic.loadCurrPlayerToGameInfo();
        m_GameInfo.setMove(getDataFromChoseButton(m_Board.getChosenButton(), m_Board.getChosenButtonPos()));
        validInput = m_Logic.checkMove(m_GameInfo.getChosenRow(), m_GameInfo.getChosenCol());

        while(!validInput){
            m_Notifier.notifyInvalidSquareChoice();
            m_GameInfo.setMove(getDataFromChoseButton(m_Board.getChosenButton(), m_Board.getChosenButtonPos()));
            validInput = m_Logic.checkMove(m_GameInfo.getChosenRow(), m_GameInfo.getChosenCol());
        }

        boolean gameDone = m_Logic.makeMove();
        //getBoard();
        if(gameDone){
            gameDoneProcedure();
        }
    }

    private SquareData getDataFromChoseButton(Button chosenButton, MoveData chosenButtonPos) {
        int row = chosenButtonPos.getRow();
        int col = chosenButtonPos.getCol();
        eColor color = toEColor(chosenButton.getTextFill());
        String value =  chosenButton.getText();

        return new SquareData(row, col, color, value);
    }

    private eColor toEColor(Paint textFill) {
        return eColor.valueOf(textFill.toString());
    }

    private void gameDoneProcedure(){
        m_Notifier.notifyGameDone();
        if(m_GameInfo.getRowPlayerScore() != m_GameInfo.getColPlayerScore()) {
            m_Notifier.announceWinner(Math.max(m_GameInfo.getRowPlayerScore(), m_GameInfo.getColPlayerScore()), m_GameInfo.getCurrPlayer());
        }
        else{
            m_Notifier.announceTie(m_GameInfo.getRowPlayerScore());
        }
    }


    public Label getPlayerName1() {
        return playerName1;
    }

    public Label getPlayerName2() {
        return playerName2;
    }
    public Label getPlayerName3() {
        return playerName3;
    }

    public Label getPlayerName4() {
        return playerName4;
    }

    public Label getPlayerName5() {
        return playerName5;
    }

    public Label getPlayerName6() {
        return playerName6;
    }

    public void setPlayerName1(Label playerName1) {
        this.playerName1 = playerName1;
    }

    public void setPlayerName2(Label playerName2) {
        this.playerName2 = playerName2;
    }

    public void setPlayerName3(Label playerName3) {
        this.playerName3 = playerName3;
    }

    public void setPlayerName4(Label playerName4) {
        this.playerName4 = playerName4;
    }

    public void setPlayerName5(Label playerName5) {
        this.playerName5 = playerName5;
    }

    public void setPlayerName6(Label playerName6) {
        this.playerName6 = playerName6;
    }
}
