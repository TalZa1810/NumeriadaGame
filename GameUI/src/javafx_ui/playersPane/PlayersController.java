package javafx_ui.playersPane;

import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
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
    private Label[] m_PlayersScoreLabels = new Label[6];
    private Label[] m_PlayersColorLabels = new Label[6];

    public PlayersController(GameInfo[] i_GameInfoWrapper, GameController i_GameController){
        m_GameInfo = i_GameInfoWrapper[0];
        m_Players = m_GameInfo.getPlayers();
        m_GameController = i_GameController;

        collectingPlayersNamesLabels();
        collectingPlayersIDsLabels();
        collectingPlayersScoreLabels();
        collectingPlayersColorLabels();
        setPlayers();
    }

    public void setPlayers(){

        int i = 0;

        /*if (m_Players != null){
            cleanPlayers();
        }*/

        for (PlayerData player : m_Players) {

            setPlayerName(m_PlayersNamesLabels[i] , player);
            setPlayerID(m_PlayersIDsLabels[i], player);
            setPlayerScoresData(m_PlayersScoreLabels[i], player);
            setPlayerColor(m_PlayersColorLabels[i], player);
            i++;
        }
    }

    private void collectingPlayersColorLabels() {
        m_PlayersColorLabels[0]= m_GameController.getPlayerColor1();
        m_PlayersColorLabels[1]= m_GameController.getPlayerColor2();
        m_PlayersColorLabels[2]= m_GameController.getPlayerColor3();
        m_PlayersColorLabels[3]= m_GameController.getPlayerColor4();
        m_PlayersColorLabels[4]= m_GameController.getPlayerColor5();
        m_PlayersColorLabels[5]= m_GameController.getPlayerColor6();
    }

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
        m_PlayersScoreLabels[0]= m_GameController.getPlayerScore1();
        m_PlayersScoreLabels[1]= m_GameController.getPlayerScore2();
        m_PlayersScoreLabels[2]= m_GameController.getPlayerScore3();
        m_PlayersScoreLabels[3]= m_GameController.getPlayerScore4();
        m_PlayersScoreLabels[4]= m_GameController.getPlayerScore5();
        m_PlayersScoreLabels[5]= m_GameController.getPlayerScore6();
    }

    private void setPlayerScoresData(Label i_playerScoreLabel, PlayerData i_PlayerData) {
        i_playerScoreLabel.setText(String.valueOf(i_PlayerData.getScore()));
    }


    public void setPlayerName(Label i_PlayerNameLabel , PlayerData i_PlayerData ){
        i_PlayerNameLabel.setText(i_PlayerData.getName());
    }

    public  void setPlayerID(Label i_PlayerIDLabel , PlayerData i_PlayerData){
        i_PlayerIDLabel.setText(String.valueOf(i_PlayerData.getID()));
    }

    public void setPlayerColor(Label i_PlayerColorLabel , PlayerData i_PlayerData){
        i_PlayerColorLabel.setText("###");
        i_PlayerColorLabel.setTextFill(Paint.valueOf(i_PlayerData.getColor().name()));
    }

    public Label[] getScoreLabels() {
        return m_PlayersScoreLabels;
    }

    public void cleanPlayers(int size) {
        String empty = "";
        for(int i = 0; i < size; i++){
            m_PlayersColorLabels[i].setText(empty);
            m_PlayersIDsLabels[i].setText(empty);
            m_PlayersNamesLabels[i].setText(empty);
            m_PlayersScoreLabels[i].setText(empty);
        }
    }
}
