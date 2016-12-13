package Logic;

import Shared.GameInfo;

import java.sql.Time;
import java.util.Random;

/**
 * Created by talza on 20/11/2016.
 */
public abstract class Game {

    private Player [] m_Players;
    Board m_Board;
    GameInfo m_GameInfo;
    protected  Player m_CurrentPlayer;
    private int m_NumOfMoves;
    private Time m_Timer;
    private eBoardStructure m_BoardStructure;
    private eGameType m_GameType;
    private eGameMode m_GameMode;

    public enum eGameMode {
        HumanVsHuman, HumanVsComputer
    }

    public enum eGameType {
        Basic, Advanced
    }

    public enum eBoardStructure {
        Explicit, Random
    }


    public Game(GameInfo[] i_GameInfoWrapper){
        m_GameInfo = i_GameInfoWrapper[0];
        m_Board = new Board(m_GameInfo.GetBoardSize());
        m_Players = new Player[m_GameInfo.getNumOfPlayers()];
        for(int i = 0; i < m_Players.length; i++ ){
            m_Players[i] = new Player();
            if(i % 2 == 0){
                m_Players[i].setPlayerType(Player.ePlayerType.ROW_PLAYER);
            }
            else{
                m_Players[i].setPlayerType(Player.ePlayerType.COLUMN_PLAYER);
            }
        }
        m_CurrentPlayer = m_Players[0];
        m_NumOfMoves = 0;

        if(m_GameInfo.getGameType() == eGameType.Basic.toString()) {
            m_GameType = eGameType.Basic;
        }
        else{
            m_GameType = eGameType.Advanced;
        }
        String s = eBoardStructure.Explicit.name();
        if(m_GameInfo.getBoardStructure().equals(eBoardStructure.Explicit.name())) {
            m_BoardStructure = eBoardStructure.Explicit;
        }
        else{
            m_BoardStructure = eBoardStructure.Random;
        }

        m_GameMode = eGameMode.values()[m_GameInfo.getGameMode()];

        initBoard();
    }

    public Board getBoard() {
        return m_Board;
    }

    private void initBoard() {
        if(m_BoardStructure == eBoardStructure.Explicit){
            initExplicitBoard();
        }
        else{

            //THROWS EXCEPTION HERE NULL PTR WITH THE SETTORANGE FUNCTION
            m_Board.SetToRange(m_GameInfo.GetRangeTo());
            m_Board.SetFromRange(m_GameInfo.GetRangeFrom());
            initRandomBoard();
        }
    }

    private void initRandomBoard() {

        final int markerCount = 1;
        int boardSize = m_Board.getBoardSize();
        Integer toRange = m_Board.GetToRange();
        Integer fromRange = m_Board.GetFromRange();

        int randomCol = generateRandomPositionForRandomSquare(boardSize);
        int randomRow = generateRandomPositionForRandomSquare(boardSize);

        int amountOfNumsInRange = Math.abs(toRange) - Math.abs(fromRange)  + 1;

        int amountOfEachNumInRange = (boardSize * boardSize - markerCount) / amountOfNumsInRange;

        for (int i = fromRange; i < toRange ; i++){

            for(int j = 0; j < amountOfEachNumInRange; j++){

                //generate random position for square
                while(!m_GameInfo.getValueInPos(randomRow,randomCol).equals("")){
                    randomCol = generateRandomPositionForRandomSquare(boardSize);
                    randomRow = generateRandomPositionForRandomSquare(boardSize);
                }

                m_Board.getSquareInPos(randomRow,randomCol).SetSquareSymbol( Integer.toString(i));
            }
        }
    }

    private int generateRandomPositionForRandomSquare(int i_BoardSize){

        final int minBoardSize = 5;
        Random rand = new Random();

        int randomNum= rand.nextInt((i_BoardSize - minBoardSize) + 1) + minBoardSize;

        return randomNum;
    }

    private void initExplicitBoard() {

        for(int i = 0; i < m_Board.getBoardSize(); i++){
            for(int j = 0; j < m_Board.getBoardSize(); j++){
                String s = m_GameInfo.getValueInPos(i,j);
                if(!m_GameInfo.getValueInPos(i,j).equals("")){
                    m_Board.getSquareInPos(i,j).SetSquareSymbol(m_GameInfo.getValueInPos(i,j));
                }
            }
        }
    }


    public void MakeMove() {
        playTurn(m_CurrentPlayer, m_GameInfo.getMove());
        m_NumOfMoves++;
        m_CurrentPlayer = m_Players[m_NumOfMoves % m_Players.length];

        checkIfGameDone(m_Board.getMark());
    }

    private boolean checkIfGameDone(Marker i_Mark) {
        int markRow = i_Mark.GetRow();
        int markCol = i_Mark.GetColumn();
        boolean gameDone = true;

        for(int i = 0; i < m_Board.getBoardSize(); i++){
            //check if row empty of numbers
            if(m_Board.getSquareInPos(markRow,i).toString() != m_Board.getMark().GetSquareSymbol() &&
                    m_Board.getSquareInPos(markRow,i).toString() != ""){
                gameDone = false;
            }
            //check if col empty of numbers
            if(m_Board.getSquareInPos(i,markCol).toString() != m_Board.getMark().GetSquareSymbol() &&
                    m_Board.getSquareInPos(i, markCol).toString() != ""){
                gameDone = false;
            }
        }

        return gameDone;
    }


    public abstract void GetBoardToPrint();


    public void GetGameStatus() {
        GetBoardToPrint();
        m_GameInfo.setCurrPlayer(m_CurrentPlayer.GetEPlayerTypeAsString());
    }

    public void GetGameStatistics() {

        m_GameInfo.setNumOfMoves(m_NumOfMoves);
        m_GameInfo.setElapsedTime(m_Timer);

        for (Player p: m_Players){
            m_GameInfo.setRowPlayerScore(p.getPlayerScore());
        }
    }


    public void getCurrMarkerPosition() {
        m_GameInfo.setMarkerCol(m_Board.GetMarkerCol());
        m_GameInfo.setMarkerRow(m_Board.GetMarkerRow());
    }

    public void playTurn(Player i_Player, int i_Move){
        if(m_GameMode == eGameMode.HumanVsHuman){
            playHumanTurn(i_Player,i_Move);
        }
        else if(m_GameMode == eGameMode.HumanVsComputer && i_Player.getPlayerType() == Player.ePlayerType.COLUMN_PLAYER){
            playComputerTurn(i_Player);
        }
    }

    private void playComputerTurn(Player i_Player) {
        Random r = new Random();
        int randomMove = r.nextInt(m_Board.getBoardSize()) + 1;

        Square squareToChange;
        Marker markerToChange;

        if(i_Player.getPlayerType() == Player.ePlayerType.COLUMN_PLAYER) {
            squareToChange = (Square)m_Board.getSquareInPos(randomMove, m_GameInfo.getMarkerCol());
            markerToChange = (Marker)m_Board.getSquareInPos(randomMove, m_GameInfo.getMarkerCol());
        }
        else {
            squareToChange = (Square)m_Board.getSquareInPos( m_GameInfo.getMarkerRow(),randomMove);
            markerToChange = (Marker)m_Board.getSquareInPos( m_GameInfo.getMarkerRow(),randomMove);
        }

        m_CurrentPlayer.addToPlayerScore(Integer.parseInt(squareToChange.GetSquareSymbol()));

        m_Board.SetSquare(i_Player, squareToChange);
        m_Board.SetMarker(i_Player, markerToChange);
    }

    private void playHumanTurn(Player i_Player, int i_Move) {
        Square squareToChange;
        Marker markerToChange;

        if(i_Player.getPlayerType() == Player.ePlayerType.COLUMN_PLAYER) {
            squareToChange = (Square)m_Board.getSquareInPos(i_Move, m_GameInfo.getMarkerCol());
            markerToChange = (Marker)m_Board.getSquareInPos(i_Move, m_GameInfo.getMarkerCol());
        }
        else {
            squareToChange = (Square)m_Board.getSquareInPos( m_GameInfo.getMarkerRow(),i_Move);
            markerToChange = (Marker)m_Board.getSquareInPos( m_GameInfo.getMarkerRow(),i_Move);
        }

        m_CurrentPlayer.addToPlayerScore(Integer.parseInt(squareToChange.GetSquareSymbol()));

        m_Board.SetSquare(i_Player, squareToChange);
        m_Board.SetMarker(i_Player, markerToChange);
    }
}

/*
* update points
* timer
* move random
* */