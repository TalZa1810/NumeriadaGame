package UI;

import java.sql.Time;

/**
 * Created by Tal on 11/26/2016.
 */
public interface IGUI {
    void ShowBoard(int i_MaxRange, int i_BoardSize, char[][] i_Board);
    void ShowStatus(int i_MaxRange, int i_BoardSize, char[][] i_Board, String i_NextPlayer);
    void ShowStatistics(int i_NumOfMoves, Time i_ElapsedTime, int i_RowPlayerScore, int i_ColPlayerScore);
}
