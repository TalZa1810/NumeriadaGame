package javafx_ui.gamePane;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx_ui.boardPane.BoardController;
import javafx_ui.loaderPane.LoaderController;
import javafx_ui.playersPane.PlayersController;
import javafx_ui.statusPane.StatusController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable{
    //TODO: add all panes to border pane

    private static final String PLAYERS_SCENE_FXML_PATH = "/javafx_ui/playersPane/PlayersPane.fxml";
    private static final String BOARD_SCENE_FXML_PATH = "/javafx_ui/boardPane/BoardPane.fxml";
    private static final int MAX_PLAYERS = 6;

    @FXML
    private BorderPane m_MainWindow;

    @FXML
    private TextField pathTextBox;

    @FXML
    private Button browseButton;

    @FXML
    private Button loadButton;

    @FXML
    private TextArea statusBarText;

    private BoardController m_Board;
    private PlayersController m_Players;
    private Stage m_PrimaryStage;

    private SimpleIntegerProperty[] m_PlayersID;
    private SimpleStringProperty[] m_PlayersNames;
    private SimpleIntegerProperty[] m_PlayersScore;

    public GameController(){
        m_MainWindow = new BorderPane();
        m_Board = new BoardController(m_MainWindow);
        m_Players = new PlayersController();
        createBoardPane();
        createPlayersPane();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void browseButtonClicked(){

    }

    @FXML
    public void loadButtonClicked(){

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
            m_MainWindow.getChildren().add(playersPane);
            m_MainWindow.setRight(playersPane);
        } catch(IOException e){}
    }

    private void createBoardPane(){} {
        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource(BOARD_SCENE_FXML_PATH);
        loader.setLocation(mainFXML);
        try {
            Node boardPane = loader.load();

            PlayersController boardController = loader.getController();
            m_MainWindow.getChildren().add(boardPane);
            m_MainWindow.setCenter(boardPane);
        } catch(IOException e){}
    }
}
