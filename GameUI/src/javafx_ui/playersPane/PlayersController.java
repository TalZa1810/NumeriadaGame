package javafx_ui.playersPane;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx_ui.gamePane.GameController;
import shared.GameInfo;
import sharedStructures.PlayerData;

import java.util.ArrayList;

public class PlayersController {

    private GameInfo m_GameInfo;
    private GameController m_GameController;

    private ArrayList<PlayerData> m_Players;

    private Label[] m_PlayersNamesLabels = new Label[6];
    private Label[] m_PlayersIDsLabels = new Label[6];
    private Label[] m_PlayersScoreLables = new Label[6];

    public PlayersController(GameInfo[] i_GameInfoWrapper, GameController i_GameController){
        m_GameInfo = i_GameInfoWrapper[0];
        m_Players = m_GameInfo.getPlayers();
        m_GameController = i_GameController;

        collectingPlayersNamesLabels();
        collectingPlayersIDsLabels();
        collectingPlayersScoreLabels();
        setPlayers();
    }

    public void setPlayers(){

        int i = 0;

        for (PlayerData player : m_Players) {

            setPlayerName(m_PlayersNamesLabels[i] , player);
            setPlayerID(m_PlayersIDsLabels[i], player);
            setPlayersScoresData();
            i++;
        }
    }

    //TODO: SHOULD CREATE SIMILAR METHOD FOR COLOR

    private void collectingPlayersNamesLabels(){
        m_PlayersNamesLabels[0]= m_GameController.getPlayerName1();
        m_PlayersNamesLabels[1]= m_GameController.getPlayerName2();
        m_PlayersNamesLabels[2]= m_GameController.getPlayerName3();
        m_PlayersNamesLabels[3]= m_GameController.getPlayerName4();
        m_PlayersNamesLabels[4]= m_GameController.getPlayerName5();
        m_PlayersNamesLabels[5]= m_GameController.getPlayerName6();
    }

    private void collectingPlayersIDsLabels(){
        m_PlayersIDsLabels[0]= m_GameController.getPlayerID1();
        m_PlayersIDsLabels[1]= m_GameController.getPlayerID2();
        m_PlayersIDsLabels[2]= m_GameController.getPlayerID3();
        m_PlayersIDsLabels[3]= m_GameController.getPlayerID4();
        m_PlayersIDsLabels[4]= m_GameController.getPlayerID5();
        m_PlayersIDsLabels[5]= m_GameController.getPlayerID6();
    }

    private void collectingPlayersScoreLabels(){
        m_PlayersScoreLables[0]= m_GameController.getPlayerScore1();
        m_PlayersScoreLables[1]= m_GameController.getPlayerScore2();
        m_PlayersScoreLables[2]= m_GameController.getPlayerScore3();
        m_PlayersScoreLables[3]= m_GameController.getPlayerScore4();
        m_PlayersScoreLables[4]= m_GameController.getPlayerScore5();
        m_PlayersScoreLables[5]= m_GameController.getPlayerScore6();
    }

    //SCORE BINDING
    private void setPlayersScoresData(){

        SimpleIntegerProperty[] playerScore = m_GameController.getPlayersScore() ;

        int i = 0;
        for (PlayerData player : m_Players) {

            playerScore[i] = new SimpleIntegerProperty(player.getScore());
            m_PlayersScoreLables[i].textProperty().bind(Bindings.format( "%s", playerScore[i] ));
            i++;
        }
    }

    public void setPlayerName(Label i_PlayerNameLabel , PlayerData i_PlayerData ){
        i_PlayerNameLabel.setText(i_PlayerData.getName());
    }

    public  void setPlayerID(Label i_PlayerIDLabel , PlayerData i_PlayerData){
        Integer id = i_PlayerData.getID();
        i_PlayerIDLabel.setText(id.toString());
    }

    public void setPlayerColor(){

    }
}
