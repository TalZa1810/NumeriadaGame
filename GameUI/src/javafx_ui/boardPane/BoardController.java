package javafx_ui.boardPane;


import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import shared.GameInfo;
import sharedStructures.MoveData;
import sharedStructures.SquareData;

//import sharedStructures.Button;

public class BoardController{
    private GameInfo m_GameInfo;
    private final double k_CellSize = 35;
    private HBox[] m_GridRows;
    private GridPane m_BoardGrid;
    private Button m_ChosenButton;
    private MoveData m_ChosenButtonPos;

    public MoveData getChosenButtonPos() {
        return m_ChosenButtonPos;
    }

    public void setChosenButtonPos(MoveData i_ChosenButtonPos) {
        this.m_ChosenButtonPos = i_ChosenButtonPos;
    }

    public Button getChosenButton() {
        return m_ChosenButton;
    }

    public BoardController(GameInfo[] i_GameInfoWrapper, GridPane i_BoardGrid){
        m_GameInfo = i_GameInfoWrapper[0];
        m_BoardGrid = i_BoardGrid;
        initializeBoard();
    }

    public void initializeBoard() {
        initializeRows();
        initializeButtons();
        setBoardData();
    }

    public void setBoardData() {
        SquareData[][] board;
        board = m_GameInfo.getBoard();
        Button button;

        for(int row = 0; row < m_GameInfo.getBoardSize(); row++){
            for(int col = 0; col < m_GameInfo.getBoardSize(); col++){
                if(!board[row][col].getValue().equals("")) {
                    button = getButtonInPos(row, col);
                    button.setText(board[row][col].getValue());
                    button.setTextFill(Paint.valueOf(board[row][col].getColor().name()));
                }
            }
        }
    }

    private Button getButtonInPos(int row, int col){
        Button button = null;
        HBox wantedRow;
        try {
            wantedRow = m_GridRows[row];
            button = (Button)wantedRow.getChildren().get(col);
        } catch(Exception e){}

        return button;
    }

    private void initializeRows() {
        m_GridRows = new HBox[m_GameInfo.getBoardSize()];
        for(int i = 0; i < m_GameInfo.getBoardSize(); i++){
            m_GridRows[i] = new HBox();
        }

        for(int i = 0; i < m_GameInfo.getBoardSize(); i++) {
            RowConstraints rowContainer = new RowConstraints();
            rowContainer.setPercentHeight(k_CellSize);
            m_BoardGrid.getRowConstraints().add(rowContainer);
        }
    }

    private void initializeButtons(){
        for(int row = 0; row < m_GameInfo.getBoardSize(); row++){
            for(int col = 0; col < m_GameInfo.getBoardSize(); col++){
                final Button button = new Button();
                button.setPrefSize(k_CellSize,k_CellSize);
                button.setMinSize(k_CellSize,k_CellSize);
                button.setMaxSize(k_CellSize,k_CellSize);
                final Integer fRow = new Integer(row);
                final Integer fCol = new Integer(col);
                button.setOnAction((event) -> buttonClicked(button, fRow, fCol));
                button.setText("");
                m_GridRows[row].getChildren().add(col, button);
            }
        }

        for(int i = 0; i < m_GameInfo.getBoardSize(); i++) {
            m_BoardGrid.add(m_GridRows[i], 0, i);
        }
    }

    private void buttonClicked(Button button, int row, int col) {
        m_ChosenButton = button;
        m_ChosenButtonPos.setCol(col);
        m_ChosenButtonPos.setRow(row);
    }
}