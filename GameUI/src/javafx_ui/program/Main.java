package javafx_ui.program;

/**
 * Created by Tal on 1/3/2017.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx_ui.gamePane.GameController;

import java.io.IOException;
import java.net.URL;

import static java.lang.System.exit;

public class Main extends Application {
    private static final String GAME_SCENE_FXML_PATH = "/javafx_ui/gamePane/MainWindow.fxml";

    public static void main(String args[]) throws Exception{

        launch(args);
        exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource(GAME_SCENE_FXML_PATH);
        loader.setLocation(mainFXML);
        BorderPane root = loader.load();

        GameController gameController = loader.getController();
        gameController.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Numberiada");
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

