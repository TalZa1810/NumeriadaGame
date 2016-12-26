package logic;

import shared.GameInfo;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public abstract class Game {
    //TODO: CHANGE TO ARRAYLIST IN ORDER TO ADD NEW PLAYERS AND SETTING THEM ON THE SPOT
    private Player [] m_Players;
    Board m_Board;
    GameInfo m_GameInfo;
    private Player m_CurrentPlayer;
    private int m_NumOfMoves = 0;

    private eBoardStructure m_BoardStructure;
    private eGameType m_GameType;
    private eGameMode m_GameMode;

    private Square m_ChosenSquare= new Square();


    //Timer
    int m_secondsPassed = 0;
    private Timer m_Timer = new Timer();
    TimerTask m_Task = new TimerTask() {
        @Override
        public void run() {
            m_secondsPassed++;
        }
    };

    public void start(){
        m_Timer.scheduleAtFixedRate(m_Task, 1000, 1000);
    }

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
        //setPlayers();
        setBoard();
        setGameType();
        m_GameMode = eGameMode.values()[m_GameInfo.getGameMode() - 1]; //two human players or human against computer
        initBoard();

        //String m_GameType;

        m_CurrentPlayer = m_Players[0];
    }

    private  void setBoard(){
        m_Board = new Board(m_GameInfo.getBoardSize());

        m_Board.createMarker(m_GameInfo.getMarkerRow(), m_GameInfo.getMarkerCol());
        setBoardStructure();
    }

    public Player[] getPlayers() {
        return m_Players;
    }

    /*public void setPlayers( ) {
        this.m_Players = new Player[m_GameInfo.getNumOfPlayers()];
        m_CurrentPlayer = m_Players[0];
    }*/


    abstract void setPlayers(int i_NumOfPlayers);

    //TODO: SETTING PLAYERS ACCORDING TO GAME TYPE- ADVANCED OR BASIC

    //private void setPlayers(){

        //m_Players = new Player[m_GameInfo.getNumOfPlayers()];

        //TODO: function add player everytime a new player is added

        //m_CurrentPlayer = m_Players[0];
    //

    private void setGameType(){

        if(m_GameInfo.getGameType().equals(eGameType.Basic.name())) {
            m_GameType = eGameType.Basic;
        }
        else{
            m_GameType = eGameType.Advanced;
        }
    }

    private void setBoardStructure(){
        if(m_GameInfo.getBoardStructure().equals(eBoardStructure.Explicit.name())) {
            m_BoardStructure = eBoardStructure.Explicit;
        }
        else{
            m_BoardStructure = eBoardStructure.Random;
        }
    }

    public Board getBoard() {
        return m_Board;
    }

    private void initBoard() {
        if(m_BoardStructure == eBoardStructure.Explicit){
            initExplicitBoard();
        }
        else{
            m_Board.createRange(m_GameInfo.getRangeFrom(), m_GameInfo.getRangeTo());
            initRandomBoard();
        }
    }

    private void initRandomBoard() {

        final int markerCount = 1;
        int boardSize = m_Board.getBoardSize();
        int toRange = m_Board.getToRange();
        int fromRange = m_Board.getFromRange();
        int randomCol= 0, randomRow= 0;

        int amountOfNumsInRange = toRange - fromRange + 1;

        int amountOfEachNumInRange = (boardSize * boardSize - markerCount) / amountOfNumsInRange;

        for (int i = fromRange; i <= toRange ; i++){

            for(int j = 0; j < amountOfEachNumInRange; j++){

                randomCol = generateRandomPositionForRandomSquare(boardSize);
                randomRow = generateRandomPositionForRandomSquare(boardSize);

                //generate random position for square
                while(!m_Board.getSquareInPos(randomRow,randomCol).getSquareSymbol().equals("")){
                    randomCol = generateRandomPositionForRandomSquare(boardSize);
                    randomRow = generateRandomPositionForRandomSquare(boardSize);
                }

                m_Board.getSquareInPos(randomRow,randomCol).setSquareSymbol(Integer.toString(i));
            }
        }

        randomCol = generateRandomPositionForRandomSquare(boardSize);
        randomRow = generateRandomPositionForRandomSquare(boardSize);

        //generate random position for marker
        while(!m_Board.getSquareInPos(randomRow,randomCol).getSquareSymbol().equals("")){
            randomCol = generateRandomPositionForRandomSquare(boardSize);
            randomRow = generateRandomPositionForRandomSquare(boardSize);
        }
        //setting marker symbol and position
        m_Board.getSquareInPos(randomRow,randomCol).setSquareSymbol("@");
        m_Board.setMark(m_Board.getSquareInPos(randomRow,randomCol));
    }

    private int generateRandomPositionForRandomSquare(int i_BoardSize){

        final int minBoardSize = 5;
        Random rand = new Random();

        int randomNum= rand.nextInt(i_BoardSize);

        return randomNum;
    }

    private void initExplicitBoard() {

        for(int i = 0; i < m_Board.getBoardSize(); i++){
            for(int j = 0; j < m_Board.getBoardSize(); j++){
                String s = m_GameInfo.getValueInPos(i,j);
                if(!m_GameInfo.getValueInPos(i,j).equals("")){
                    m_Board.getSquareInPos(i,j).setSquareSymbol(m_GameInfo.getValueInPos(i,j));
                }
            }
        }
    }

    public void loadCurrPlayerToGameInfo() {
       // m_GameInfo.setCurrPlayer(m_CurrentPlayer.getEPlayerTypeAsString());
    }

   /* public boolean checkIfLegalMove(Player i_Player, int i_Move) {
        //TODO: change function to check row and col according to the color
        //TODO: change move to be square instead of int
        if(m_CurrentPlayer.getPlayerType() == Player.ePlayerType.COLUMN_PLAYER) {
            if (m_Board.getSquareInPos(i_Move, m_Board.getMark().getColumn()).getSquareSymbol().equals(m_Board.getMark().getSquareSymbol())
                    || m_Board.getSquareInPos(i_Move, m_Board.getMark().getColumn()).getSquareSymbol().equals("")) {
                return false;
            }
        }
        else{
            if (m_Board.getSquareInPos(m_Board.getMark().getRow(), i_Move).getSquareSymbol().equals(m_Board.getMark().getSquareSymbol())
                    || m_Board.getSquareInPos(m_Board.getMark().getRow(), i_Move).getSquareSymbol().equals("")) {
                return false;
            }
        }
        return true;
    }*/

    public boolean makeMove() {

        //TODO:

        playTurn();
        m_NumOfMoves++;
        m_CurrentPlayer = m_Players[m_NumOfMoves % m_Players.length];

        boolean gameDone = checkIfGameDone(m_Board.getMark());
        return gameDone;
    }

    public boolean checkIfGameDone(Square i_Mark) {
        boolean gameDone;

        gameDone = m_Board.checkIfGameDone(m_CurrentPlayer.getPlayerType().name());
        return gameDone;
    }

    public abstract void getBoardToPrint();

    //TODO: FIX
    public void getGameStatus() {
        getBoardToPrint();
        //m_GameInfo.setCurrPlayer(m_CurrentPlayer.getEPlayerTypeAsString());
    }

    public void getGameStatistics() {

        m_GameInfo.setNumOfMoves(m_NumOfMoves);
        m_GameInfo.setElapsedTime(m_secondsPassed);

        m_GameInfo.setRowPlayerScore(m_Players[0].getPlayerScore());
        m_GameInfo.setColPlayerScore(m_Players[1].getPlayerScore());
        loadCurrPlayerToGameInfo();
    }

    public void getCurrMarkerPosition() {
        m_GameInfo.setMarkerCol(m_Board.getMarkerCol());
        m_GameInfo.setMarkerRow(m_Board.getMarkerRow());
    }

    public void playTurn(){
        m_CurrentPlayer.playTurn( m_Board, m_GameInfo , m_ChosenSquare);


    }

     /*
    private void playComputerTurn(Player i_Player) {

        int randomMove = r.nextInt(m_Board.getBoardSize());

        if(i_Player.getPlayerType() == Player.ePlayerType.COLUMN_PLAYER) {
            while(m_Board.getSquareInPos(randomMove, m_GameInfo.getMarkerCol()).getSquareSymbol().equals("") ||
                    m_Board.getSquareInPos(randomMove, m_GameInfo.getMarkerCol()).getSquareSymbol().equals("@")){
                randomMove = r.nextInt(m_Board.getBoardSize());
            }
        }
        else {
            while(m_Board.getSquareInPos(m_GameInfo.getMarkerRow(), randomMove).getSquareSymbol().equals("") ||
                    m_Board.getSquareInPos(m_GameInfo.getMarkerRow(), randomMove).getSquareSymbol().equals("@")){
                randomMove = r.nextInt(m_Board.getBoardSize());
            }
        }

        makePlayerMove(i_Player, randomMove);

    }


        private void playHumanTurn(Player i_Player, int i_Move) {
        makePlayerMove(i_Player,i_Move);
    }
    *

    private void makePlayerMove(Player i_Player, int i_Move){
        Square squareToChange;
        Square markerToChange;

        if(i_Player.getPlayerType() == Player.ePlayerType.COLUMN_PLAYER) {
            squareToChange = m_Board.getSquareInPos( m_GameInfo.getMarkerRow(), m_GameInfo.getMarkerCol());
            markerToChange = m_Board.getSquareInPos(i_Move, m_GameInfo.getMarkerCol());
        }
        else {
            squareToChange = m_Board.getSquareInPos( m_GameInfo.getMarkerRow(),m_GameInfo.getMarkerCol());
            markerToChange = m_Board.getSquareInPos( m_GameInfo.getMarkerRow(),i_Move);
        }

        m_CurrentPlayer.addToPlayerScore(Integer.parseInt(markerToChange.getSquareSymbol()));

        m_Board.changeMarker(squareToChange, markerToChange);
    }*/
}