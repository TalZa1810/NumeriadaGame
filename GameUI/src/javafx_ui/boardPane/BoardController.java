package javafx_ui.boardPane;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import shared.GameInfo;
import sharedStructures.SquareData;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable{
    private GameInfo m_GameInfo;
    private final double k_CellSize = 20;
    private final double k_ButtonSize = 18.5;

    @FXML
    private ScrollPane boardScrollPane;

    @FXML
    private GridPane boardGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardGrid = new GridPane();
        initializeRows();
        initializeCols();
        initializeButtons();
        setBoardData();
    }

    private void setBoardData() {
        SquareData[][] board;
        board = m_GameInfo.getBoard();
        Button button = new Button();

        for(int row = 0; row < m_GameInfo.getBoardSize(); row++){
            for(int col = 0; col < m_GameInfo.getBoardSize(); col++){
                if(!board[row][col].equals("")) {
                    button = getButtonInPos(row, col);
                    button.setText(board[row][col].getValue());
                    button.setTextFill(Paint.valueOf(board[row][col].getColor().name()));
                }
            }
        }
    }

    private Button getButtonInPos(int row, int col) {
        return (Button)boardGrid.getChildren().get(row * m_GameInfo.getBoardSize() + col);
    }

    public BoardController(GameInfo i_GameInfo){
        m_GameInfo = i_GameInfo;
    }

    private void initializeRows() {
        for(int i = 0; i < m_GameInfo.getBoardSize(); i++) {
            boardGrid.addRow(i);
        }

        RowConstraints row = new RowConstraints();
        row.setPercentHeight(k_CellSize);
        boardGrid.getRowConstraints().add(row);
    }

    private void initializeCols() {
        for(int i = 0; i < m_GameInfo.getBoardSize(); i++) {
            boardGrid.addColumn(i);
        }

        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(k_CellSize);
        boardGrid.getColumnConstraints().add(col);
    }

    private void initializeButtons(){
        Button button;
        for(int row = 0; row < m_GameInfo.getBoardSize(); row++){
            for(int col = 0; col < m_GameInfo.getBoardSize(); col++){
                button = new Button();
                button.setPrefSize(k_ButtonSize,k_ButtonSize);
                button.setText("");
                boardGrid.add(button, col, row);
            }
        }
    }
}