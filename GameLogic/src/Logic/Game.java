package Logic;

/**
 * Created by talza on 20/11/2016.
 */
abstract class Game extends GameTypeFactory{

    protected Player [] m_Players;
    protected Board m_Board;
    protected eBoardStructure m_BoardStructure;

    public enum eBoardStructure {
        EXPLICIT, RANDOM
    }


    public Game(int i_Players, int i_BoardSize, eBoardStructure i_BoardStructure){
        m_Players = new Player[i_Players];
        m_Board = new Board(i_BoardSize);
        m_BoardStructure = i_BoardStructure;

        if (i_BoardStructure == eBoardStructure.EXPLICIT)
        {
            //USE
        }
        else
        {

        }


    }
}

/*
* play turn
* change square
* move mark
* update points
* timer
* move random
* */