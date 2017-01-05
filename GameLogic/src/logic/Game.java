package logic;

import shared.GameInfo;
import sharedStructures.MoveData;
import sharedStructures.PlayerData;
import sharedStructures.SquareData;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public abstract class Game {
    ArrayList<Player> m_Players;
    Board m_Board;
    GameInfo m_GameInfo;
    Player m_CurrentPlayer;
    private int m_NumOfMoves = 0;

    private eBoardStructure m_BoardStructure;
    private eGameType m_GameType;
    private Square m_ChosenSquare= new Square();

    private ArrayList<MoveData> m_PlayersMoves = new ArrayList<MoveData>();
    private ArrayList<MoveData> m_MarkMoves = new ArrayList<MoveData>();

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

    public boolean checkMove(int chosenRow, int chosenCol) {
        Square chosenSquare = m_Board.getSquareInPos(chosenRow, chosenCol);
        return checkIfLegalMove(m_CurrentPlayer, chosenSquare);
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

    public Game(GameInfo i_GameInfo){
        m_GameInfo = i_GameInfo;
        setGameType();
        setPlayers();
        setBoard();
        initBoard();
        m_CurrentPlayer = m_Players.get(0);
        m_GameInfo.setCurrPlayer(getCurrPlayerData());
    }

    private PlayerData getCurrPlayerData() {
        return new PlayerData(m_CurrentPlayer.getName(), m_CurrentPlayer.getID(), m_CurrentPlayer.getPlayerColor(), m_CurrentPlayer.getPlayerType());
    }

    private  void setBoard(){
        m_Board = new Board(m_GameInfo.getBoardSize());

        m_Board.createMarker(m_GameInfo.getMarkerRow(), m_GameInfo.getMarkerCol());
        setBoardStructure();
    }

    public ArrayList<Player> getPlayers() {
        return m_Players;
    }

    public void setPlayers( ) {
        m_Players = new ArrayList<Player>();
        for(int i = 0; i < m_GameInfo.getNumOfPlayers(); i++) {
            PlayerData currPlayerData = m_GameInfo.getPlayer(i);
            m_Players.add(Player.CreatePlayer(currPlayerData.getID(), currPlayerData.getName(), currPlayerData.getColor(), currPlayerData.getType()));
        }

        m_CurrentPlayer = m_Players.get(0);
    }

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

                initRandomBoardSquare(randomRow, randomCol, j, i);
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

    abstract void initRandomBoardSquare(int randomRow, int randomCol, int colorIndex, int data);

    private int generateRandomPositionForRandomSquare(int i_BoardSize){

        final int minBoardSize = 5;
        Random rand = new Random();

        int randomNum = rand.nextInt(i_BoardSize);

        return randomNum;
    }

    private void initExplicitBoard() {

        for(int i = 0; i < m_Board.getBoardSize(); i++){
            for(int j = 0; j < m_Board.getBoardSize(); j++){
                SquareData s = m_GameInfo.getValueInPos(i,j);
                if(!m_GameInfo.getValueInPos(i,j).equals("")){
                    m_Board.getSquareInPos(i,j).setSquareSymbol(m_GameInfo.getValueInPos(i,j).getValue());
                    m_Board.getSquareInPos(i,j).setColor(m_GameInfo.getValueInPos(i,j).getColor());
                }
            }
        }
    }

    public void loadCurrPlayerToGameInfo() {
        PlayerData player = new PlayerData(m_CurrentPlayer.getName(), m_CurrentPlayer.getID(), m_CurrentPlayer.getPlayerColor(), m_CurrentPlayer.getPlayerType());
        m_GameInfo.setCurrPlayer(player);
    }

    abstract boolean checkIfLegalMove(Player i_Player, Square i_Move);

    public boolean makeMove() {
        playTurn();
        m_NumOfMoves++;
        m_CurrentPlayer = m_Players.get(m_NumOfMoves % m_Players.size());

        boolean gameDone = checkIfGameDone();
        return gameDone;
    }

    public abstract boolean checkIfGameDone();

    //public abstract void getBoardToPrint();

    public void getGameStatus() {
        //getBoardToPrint();
        //m_GameInfo.setCurrPlayer(m_CurrentPlayer.getEPlayerTypeAsString());
    }

    public void getGameStatistics() {

        m_GameInfo.setNumOfMoves(m_NumOfMoves);
        m_GameInfo.setElapsedTime(m_secondsPassed);

        m_GameInfo.setRowPlayerScore(m_Players.get(0).getPlayerScore());
        m_GameInfo.setColPlayerScore(m_Players.get(1).getPlayerScore());
        loadCurrPlayerToGameInfo();
    }

    public void getCurrMarkerPosition() {
        m_GameInfo.setMarkerCol(m_Board.getMarkerCol());
        m_GameInfo.setMarkerRow(m_Board.getMarkerRow());
    }

    public void playTurn(){
        m_MarkMoves.add(new MoveData(m_Board.getMark().getRow(), m_Board.getMark().getColumn()));
        m_PlayersMoves.add(new MoveData(m_ChosenSquare.getRow(), m_ChosenSquare.getColumn()));
        m_CurrentPlayer.playTurn( m_Board, m_GameInfo , m_ChosenSquare);
    }

    public abstract void playerQuit();

    //TODO: show prev\next move (the question is previos to what, do we hold a member of current move.
    //TODO: also todo is to block option to make move when only showing the previous moves

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