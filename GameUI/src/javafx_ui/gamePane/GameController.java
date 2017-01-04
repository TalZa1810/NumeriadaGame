package javafx_ui.gamePane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Tal on 1/3/2017.
 */
public class GameController implements Initializable{
    //TODO: add all panes to border pane
    @FXML
    private BorderPane m_MainWindow;

    public GameController(){
        m_MainWindow = new BorderPane();
        m_MainWindow.setTop();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
