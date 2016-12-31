package sharedStructures;

/**
 * Created by Tal on 12/31/2016.
 */
public class SquareData {
    private int m_Row;
    private int m_Col;

    public SquareData(int i_Row, int i_Col){
        m_Row = i_Row;
        m_Col = i_Col;
    }

    public SquareData() {}

    public int getCol() {
        return m_Col;
    }

    public int getRow() {
        return m_Row;
    }

    public void setCol(int i_Col) {
        this.m_Col = i_Col;
    }

    public void setRow(int i_Row) {
        this.m_Row = i_Row;
    }
}
