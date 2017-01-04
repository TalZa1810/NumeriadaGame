package javafx_ui.program;

/**
 * Created by Tal on 1/3/2017.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manager.GameManagerFX;

import java.io.IOException;
import java.net.URL;

import static java.lang.System.exit;

public class Main extends Application {

    public static void main(String args[]) throws Exception{

        launch(args);
        exit(0);
    }

    private static final String GAME_SCENE_FXML_PATH = "";

    @Override
    public void start(Stage primaryStage) throws Exception {
        GameManagerFX manager = new GameManagerFX();

        FXMLLoader fxmlLoader = getGameFXMLLoader();
        Parent gameRoot = getGameRoot(fxmlLoader);
        PlayersController playersController = getPlayersController(fxmlLoader, primaryStage);

        Scene scene = new Scene(gameRoot, 500, 400);

        primaryStage.setTitle("Numberiada");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private FXMLLoader getGameFXMLLoader() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(GAME_SCENE_FXML_PATH);
        fxmlLoader.setLocation(url);
        return fxmlLoader;
    }

    private Parent getGameRoot(FXMLLoader fxmlLoader) throws IOException {
        return (Parent) fxmlLoader.load(fxmlLoader.getLocation().openStream());
    }
}

