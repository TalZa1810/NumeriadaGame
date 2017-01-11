package javafx_ui.playersPane;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import shared.GameInfo;

public class PlayersController extends Node{

    private GameInfo m_GameInfo;

    @FXML
    private ScrollPane playerPane;

    @FXML
    private GridPane playrersGrid;

    @FXML
    private Label idLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label player1NameLabel;

    @FXML
    private Label player2NameLabel;

    @FXML
    private Label player3NameLabel;

    @FXML
    private Label player4NameLabel;

    @FXML
    private Label player5NameLabel;

    @FXML
    private Label player6NameLabel;

    @FXML
    private Label player1ScoreLabel;

    @FXML
    private Label player2ScoreLabel;

    @FXML
    private Label player3ScoreLabel;

    @FXML
    private Label player4ScoreLabel;

    @FXML
    private Label player5ScoreLabel;

    @FXML
    private Label player6ScoreLabel;

    @FXML
    private RadioButton player1IDRadioButton;

    @FXML
    private RadioButton player2IDRadioButton;

    @FXML
    private RadioButton player3IDRadioButton;

    @FXML
    private RadioButton player4IDRadioButton;

    @FXML
    private RadioButton player5IDRadioButton;

    @FXML
    private RadioButton player6IDRadioButton;

    @Override
    protected NGNode impl_createPeer() {
        return null;
    }

    @Override
    public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
        return null;
    }

    @Override
    protected boolean impl_computeContains(double localX, double localY) {
        return false;
    }

    @Override
    public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
        return null;
    }

    public PlayersController() {}

    public void initializeController(GameInfo[] i_GameInfoWrapper){
        m_GameInfo = i_GameInfoWrapper[0];
    }
}
