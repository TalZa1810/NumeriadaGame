package javafx_ui.boardPane;


import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import shared.GameInfo;
import sharedStructures.SquareData;

public class BoardController extends Node /*implements Initializable*/{
    private GameInfo m_GameInfo;
    private final double k_CellSize = 30;
    private HBox[] m_GridRows;

    @FXML
    private ScrollPane boardScrollPane;

    @FXML
    private BorderPane boardSection;

    @FXML
    private GridPane boardGrid;

    @FXML
    private VBox boardActionButtons;

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button makeMoveButton;

    public BoardController() {
    }

    public void initializeController(GameInfo[] i_GameInfoWrapper){
        m_GameInfo = i_GameInfoWrapper[0];
    }

    public void initializeBoard() {
        initializeCols();
        initializeRows();
        initializeButtons();
        setBoardData();
        boardSection.setCenter(boardGrid);
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
            boardGrid.getRowConstraints().add(rowContainer);
        }
    }

    private void initializeCols() {
        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(k_CellSize*m_GameInfo.getBoardSize());
        boardGrid.getColumnConstraints().add(col);
    }

    private void initializeButtons(){
        Button button;
        for(int row = 0; row < m_GameInfo.getBoardSize(); row++){
            for(int col = 0; col < m_GameInfo.getBoardSize(); col++){
                //TODO: add onAction function for each button (boardCellPressed)
                button = new Button();
                button.setPrefSize(k_CellSize,k_CellSize);
                button.setText("");
                m_GridRows[row].getChildren().add(col, button);
            }
        }

        for(int i = 0; i < m_GameInfo.getBoardSize(); i++) {
            boardGrid.add(m_GridRows[i], 0, i);
        }
    }

    @Override
    protected NGNode impl_createPeer() {
        return null;
    }

    @Override
    public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
        return null;
    }

    @Override
    protected boolean impl_computeContains(double localX, double localY) {
        return false;
    }

    @Override
    public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
        return null;
    }
}