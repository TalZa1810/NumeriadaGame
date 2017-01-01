package javafx_ui;


import Generated.GameDescriptor;
import javafx.stage.FileChooser;
import shared.GameInfo;

public class GameUIFX {

    private GameInfo m_GameInfo;

    public GameUIFX(GameInfo[] i_GameInfoWrapper){
        m_GameInfo = i_GameInfoWrapper[0];
    }


    public void showStatus() {

    }

    public void showStatistics() {

    }


    public void fileWasLoadedSuccessfully() {


    }

    public void browseFileButtonClicked() {

        //TODO: load File from path

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(stage);

    }

    public GameDescriptor fromXmlFileToObject() {

        //TODO: CHANGE IT TO FXML

        loadFileButtonClicked();

        //Scanner s = new Scanner(System.in);
        //String path =  s.nextLine();

        //checking if file type is correct
        while (!path.endsWith( ".xml" )){
            //System.out.println("Wrong xml file. file must have .xml suffix");
            //path =  s.nextLine();
        }

        m_GameInfo.setPath(path);
        unmarshalFile(path);
    }
}
