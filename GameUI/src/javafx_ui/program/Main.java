//MAIN

/*
package javafx_ui.program;


import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx_ui.gamePane.GameController;

import java.net.URL;

public class Main extends Application {
    private static final String GAME_SCENE_FXML_PATH = "/javafx_ui/gamePane/MainWindow2.fxml";
    private static GameController gameController;
    private Scene scene;
    private SimpleStringProperty m_styleCssProperty = new SimpleStringProperty("");


    public static void main(String args[]) throws Exception{
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource(GAME_SCENE_FXML_PATH);
        loader.setLocation(mainFXML);

        BorderPane gameLayout = loader.load();

        gameController = new GameController();
        gameController = loader.getController();
        gameController.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Numberiada");
        this.scene = new Scene(gameLayout, 670, 580);

        this.m_styleCssProperty.bind(gameController.getStyleCssProperty());
        this.m_styleCssProperty.addListener((observable, oldValue, newValue) -> {
            this.styleChanged(oldValue, newValue);
        });

        scene.getStylesheets().add("/javafx_ui/CSS/" + m_styleCssProperty.getValue() + ".css");

        primaryStage.setScene(this.scene);
        primaryStage.show();
    }

    private void styleChanged(String oldStyle, String newStyle) {
        this.scene.getStylesheets().remove("/javafx_ui/CSS/" + oldStyle + ".css");
        this.scene.getStylesheets().add("/javafx_ui/CSS/" + newStyle + ".css");
    }

}
*/