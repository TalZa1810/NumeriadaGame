import javafx.application.Application;

import javafx.stage.Stage;
import manager.GameManagerFX;

import static java.lang.System.exit;

public class Main extends Application {

    public static void main(String args[]) throws Exception{

        GameManagerFX manager = new GameManagerFX();
        launch(args);
        manager.run();
        exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


    }
}