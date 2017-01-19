package javafx_ui.program;

/**
 * Created by Tal on 1/3/2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx_ui.gamePane.GameController;

import java.net.URL;

public class Main extends Application {
    private static final String GAME_SCENE_FXML_PATH = "/javafx_ui/gamePane/MainWindow2.fxml";
    private static GameController gameController;

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
        Scene scene = new Scene(gameLayout, 750, 550);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}


