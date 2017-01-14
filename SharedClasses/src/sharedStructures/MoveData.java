package sharedStructures;

/**
 * Created by Tal on 1/1/2017.
 */
public class MoveData {
    private int m_Row;
    private int m_Col;

    public MoveData(int i_Row, int i_Col){
        m_Row = i_Row;
        m_Col = i_Col;
    }

    public MoveData() {

    }

    public int getRow() {
        return m_Row;
    }

    public int getCol() {
        return m_Col;
    }

    public void setCol(int i_Col) {
        this.m_Col = i_Col;
    }

    public void setRow(int i_Row) {
        this.m_Row = i_Row;
    }
}
