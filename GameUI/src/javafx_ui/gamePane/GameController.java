package javafx_ui.gamePane;

import Generated.GameDescriptor;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx_ui.boardPane.BoardController;
import javafx_ui.playersPane.PlayersController;
import logic.AdvancedGame;
import logic.BasicGame;
import logic.Game;
import shared.GameInfo;
import shared.Validator;
import sharedStructures.PlayerData;
import sharedStructures.eColor;
import sharedStructures.ePlayerType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable{
    //TODO: add all panes to border pane

    private static final String PLAYERS_SCENE_FXML_PATH = "/javafx_ui/playersPane/PlayersPane.fxml";
    private static final String BOARD_SCENE_FXML_PATH = "/javafx_ui/boardPane/BoardPane.fxml";
    private static final int MAX_PLAYERS = 6;
    private Validator m_Validator = new Validator();
    private GameDescriptor m_GameDescriptor = new GameDescriptor();
    private Notifier m_Notifier = new Notifier();
    private GameInfo m_GameInfo = new GameInfo();
    private GameInfo[] m_GameInfoWrapper = new GameInfo[1];
    private Game m_Logic;

    @FXML private BorderPane m_MainWindow;
    @FXML private TextField pathTextBox;
    @FXML private Button browseButton;
    @FXML private Button loadButton;
    @FXML private TextArea statusBarText;

    private BoardController m_Board;
    private PlayersController m_Players;
    private Stage m_PrimaryStage;


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
        m_MainWindow = i_GameLayout;
        m_Board = new BoardController(m_GameInfoWrapper);
        m_Players = new PlayersController(m_GameInfoWrapper);
        createBoardPane();
        createPlayersPane();
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
                initializeGameController(m_MainWindow);
                createGame();
                m_Board.setBoardData();
            }
        }
        catch(Exception e){
            m_StatusBar.set(m_Notifier.showExceptionThrown(e.getMessage()));
        }
    }

    public void setPrimaryStage(Stage i_PrimaryStage) {
        m_PrimaryStage = i_PrimaryStage;
    }

    private void createPlayersPane(){
        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource(PLAYERS_SCENE_FXML_PATH);
        loader.setLocation(mainFXML);
        try{
            Node playersPane = loader.load();

            PlayersController playersController = loader.getController();
            m_MainWindow.setRight(playersPane);
        } catch(IOException e){}
    }

    private void createBoardPane() {
        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource(BOARD_SCENE_FXML_PATH);
        loader.setLocation(mainFXML);
        try {
            Node boardPane = loader.load();

            BoardController boardController = loader.getController();
            m_MainWindow.setCenter(boardPane);
        } catch(IOException e){
        }
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
            m_GameInfo.setSquare(marker.getRow().intValue() - 1, marker.getColumn().intValue() - 1, "@", eColor.DEFAULT.ordinal());
        }
        else {
            m_Validator.checkRangeForRandomBoard(m_GameInfo.getGameType());
        }
    }
}
