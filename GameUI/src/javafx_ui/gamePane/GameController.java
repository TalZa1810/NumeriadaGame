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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class GameController implements Initializable{

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


    private SimpleIntegerProperty[] m_PlayersScore = new SimpleIntegerProperty[6];

    private SimpleStringProperty m_FilePath = new SimpleStringProperty("");
    private SimpleStringProperty m_StatusBar = new SimpleStringProperty("");
    private SimpleBooleanProperty m_isFileSelected = new SimpleBooleanProperty();


    public GameController() {
        m_GameInfoWrapper[0] = m_GameInfo;
    }

    public void initializeGameController(BorderPane i_GameLayout){
        m_Board = new BoardController(m_GameInfoWrapper, boardGrid);
        m_Players = new PlayersController(m_GameInfoWrapper, this);
        getScoresfromGameInfo(m_PlayersScore);
    }

    private void getScoresfromGameInfo(SimpleIntegerProperty[] i_PlayerScore) {
        ArrayList<PlayerData> players = m_GameInfo.getPlayers();
        for(int i = 0; i < m_GameInfo.getNumOfPlayers(); i++){
            i_PlayerScore[i].setValue(players.get(i).getScore());
        }
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

        if(m_GameInfo.getCurrPlayer().getType().equals(ePlayerType.Computer)){
            makeMove();
        }
    }

    @FXML
    public void makeMoveClicked(){
        makeMove();
        if(m_GameInfo.getCurrPlayer().getType().equals(ePlayerType.Computer)){
            doIfNextPlayerIsComputerType();
        }
    }

    public void setPrimaryStage(Stage i_PrimaryStage) {
        m_PrimaryStage = i_PrimaryStage;
    }

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

        if(!m_GameInfo.getGameType().equals("Basic")) {
            getPlayersFromXML();
        }
        else{
            m_GameInfo.setNumOfPlayers(2);
        }
        m_GameInfo.initBoard();
        setBoardValuesFromXML();
    }

    private void getPlayersFromXML() throws Exception {
        List<GameDescriptor.Players.Player> players = m_GameDescriptor.getPlayers().getPlayer();
        m_Validator.checkValidPlayersID(players);

        for(GameDescriptor.Players.Player p: players){
            PlayerData player = new PlayerData();
            player.setName(p.getName());
            player.setID(p.getId().intValue());
            player.setColor(eColor.values()[p.getColor()]);
            player.setScore(0);

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
        boolean gameDone;

        m_Logic.getCurrMarkerPosition();
        if(!checkIfNoMorePossibleMoves()) {
            m_GameInfo.setMove(getDataFromChoseButton(m_Board.getChosenButton()));
            validInput = m_Logic.checkMove(m_GameInfo.getChosenRow(), m_GameInfo.getChosenCol());

            while (!validInput) {
                m_StatusBar.setValue(m_Notifier.notifyInvalidSquareChoice());
                return;
            }

            gameDone = m_Logic.makeMove();
            updateBoardAfterMove();
            if (gameDone) {
                gameDoneProcedure();
            }
        }
    }

    private void doIfNextPlayerIsComputerType() {
        if(!checkIfNoMorePossibleMoves()) {
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean gameDone = m_Logic.makeMove();
            updateBoardAfterMove();
            if (gameDone) {
                gameDoneProcedure();
            }
        }
    }

    private void updateBoardAfterMove() {
        Button marker = m_Board.getButtonInPos(m_GameInfo.getChosenRow(), m_GameInfo.getChosenCol());
        Button square = m_Board.getButtonInPos(m_GameInfo.getMarkerRow(), m_GameInfo.getMarkerCol());
        m_GameInfo.setMarkerRow(m_GameInfo.getChosenRow());
        m_GameInfo.setMarkerCol(m_GameInfo.getChosenCol());
        marker.setText(square.getText());
        square.setText("");
        marker.setTextFill(Paint.valueOf(eColor.BLACK.name()));
        square.setTextFill(Paint.valueOf(eColor.BLACK.name()));
        updateScoresAfterMove();
    }

    private void updateScoresAfterMove() {
        ArrayList<PlayerData> players = m_GameInfo.getPlayers();
        for(int i = 0; i < m_GameInfo.getNumOfPlayers(); i++){
            m_PlayersScore[i].setValue(players.get(i).getScore());
        }
    }

    private boolean checkIfNoMorePossibleMoves() {
        return m_Logic.checkIfPossibleMove();
    }

    private SquareData getDataFromChoseButton(Button chosenButton) {
        MoveData chosenButtonPos  = new MoveData();
        int row = m_Board.getButtonRowIndexInGrid((HBox)chosenButton.getParent());
        int col = m_Board.getButtonIndexInHBox(row, chosenButton);
        eColor color = m_Logic.getCurrentPlayerColor();
        String value = chosenButton.getText();

        return new SquareData(row, col, color, value);
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

    //Names Labels Getters
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

    //IDs Labels Getters
    public Label getPlayerID1() {
        return playerID1;
    }

    public Label getPlayerID2() {
        return playerID2;
    }

    public Label getPlayerID3() {
        return playerID3;
    }

    public Label getPlayerID4() {
        return playerID4;
    }

    public Label getPlayerID5() {
        return playerID5;
    }

    public Label getPlayerID6() {
        return playerID6;
    }

    //Score Labels Getters
    public Label getPlayerScore1() {
        return playerScore1;
    }

    public Label getPlayerScore2() {
        return playerScore2;
    }

    public Label getPlayerScore3() {
        return playerScore3;
    }

    public Label getPlayerScore4() {
        return playerScore4;
    }

    public Label getPlayerScore5() {
        return playerScore5;
    }

    public Label getPlayerScore6() {
        return playerScore6;
    }

    public Label getPlayerColor1() {
        return playerColor1;
    }

    public Label getPlayerColor2() {
        return playerColor2;
    }

    public Label getPlayerColor3() {
        return playerColor3;
    }

    public Label getPlayerColor4() {
        return playerColor4;
    }

    public Label getPlayerColor5() {
        return playerColor5;
    }

    public Label getPlayerColor6() {
        return playerColor6;
    }

    public SimpleIntegerProperty[] getPlayersScore() {
        return m_PlayersScore;
    }
}
