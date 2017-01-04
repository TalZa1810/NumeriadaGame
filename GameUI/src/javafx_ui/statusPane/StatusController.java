package javafx_ui.statusPane;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class StatusController {

    @FXML
    private TextArea statusBarText;

    private void showStatus(String i_Msg){
        statusBarText.setText(i_Msg);
    }

}
