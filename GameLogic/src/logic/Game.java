package logic;

import shared.GameInfo;
import sharedStructures.MoveData;
import sharedStructures.PlayerData;
import sharedStructures.SquareData;
import sharedStructures.eColor;

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
    private int m_NumOfPlayers;

    private eBoardStructure m_BoardStructure;
    private eGameType m_GameType;
    private Square m_ChosenSquare = new Square();

    protected ArrayList<MoveData> m_PlayersMoves = new ArrayList<MoveData>();
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

    public Player getCurrentPlayer() {
        return m_CurrentPlayer;
    }

    public void start(){
        m_Timer.scheduleAtFixedRate(m_Task, 1000, 1000);
    }

    public boolean checkMove(int chosenRow, int chosenCol) {
        Square chosenSquare = m_Board.getSquareInPos(chosenRow, chosenCol);
        return checkIfLegalMove(m_CurrentPlayer, chosenSquare);
    }

    public eColor getCurrentPlayerColor() {
        return getCurrentPlayer().getColor();
    }

    public abstract boolean checkIfNotPossibleMove();

    public void setNumOfPlayers(int i) {
        m_NumOfPlayers = i;
    }

    public int getNumOfPlayers() {
        return m_NumOfPlayers;
    }

    public void nextPlayer(int i_IndexOfCurrentPlayer) {
        m_CurrentPlayer = m_Players.get((i_IndexOfCurrentPlayer + 1) % m_Players.size());
        loadCurrPlayerAndNumOfMovesToGameInfo();
    }

    public void removePlayerFromList() {
        m_Players.remove(m_CurrentPlayer);
        m_GameInfo.removeCurrentPlayer();
        m_NumOfPlayers -= 1;
        m_GameInfo.setNumOfPlayers(m_NumOfPlayers);
    }

    public abstract void removeCurrentPlayerCellsFromBoard();

    public void updateCurrPlayer(int nextPlayerIndex) {
        m_CurrentPlayer = m_Players.get(nextPlayerIndex);
    }

    public void loadPastMovesToGameInfo() {
        m_GameInfo.setPlayersMoves(m_PlayersMoves);
        m_GameInfo.setMarkMoves(m_MarkMoves);
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
        setGameType();
        setPlayers();
        setBoard();
        initBoard();
        m_CurrentPlayer = m_Players.get(0);
        m_NumOfPlayers = m_Players.size();
        m_GameInfo.setCurrPlayer(getCurrPlayerData());
    }

    private PlayerData getCurrPlayerData() {
        return new PlayerData(m_CurrentPlayer.getName(), m_CurrentPlayer.getID(), m_CurrentPlayer.getColor(), m_CurrentPlayer.getPlayerType(), m_CurrentPlayer.getPlayerScore());
    }

    private  void setBoard(){
        m_Board = new Board(m_GameInfo.getBoardSize());

        m_Board.createMarker(m_GameInfo.getMarkerRow(), m_GameInfo.getMarkerCol());
        setBoardStructure();
    }

    public ArrayList<Player> getPlayers() {
        return m_Players;
    }

    abstract void setPlayers( );

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
        loadBoardToGameInfo();
    }

    public void loadBoardToGameInfo() {
        SquareData board[][] = new SquareData[m_Board.getBoardSize()][m_Board.getBoardSize()];
        Square square;
        for (int i=0; i< m_Board.getBoardSize(); i++) {
            for (int j = 0; j< m_Board.getBoardSize(); j++) {
                square = m_Board.getSquareInPos(i,j);
                board[i][j] = new SquareData(square.getRow(), square.getColumn(), square.getColor(), square.getSquareSymbol());
            }
        }
        m_GameInfo.setBoard(board);
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

    public void loadCurrPlayerAndNumOfMovesToGameInfo() {
        PlayerData player = new PlayerData(m_CurrentPlayer.getName(), m_CurrentPlayer.getID(), m_CurrentPlayer.getColor(), m_CurrentPlayer.getPlayerType(), m_CurrentPlayer.getPlayerScore());
        m_GameInfo.setCurrPlayer(player);
        m_GameInfo.setNumOfMoves(m_NumOfMoves);
    }

    abstract boolean checkIfLegalMove(Player i_Player, Square i_Move);

    public boolean makeMove() {
        loadChosenSquare();
        playTurn();
        m_NumOfMoves++;
        m_CurrentPlayer = m_Players.get(m_NumOfMoves % m_Players.size());
        loadCurrPlayerAndNumOfMovesToGameInfo();
        boolean gameDone = checkIfGameDone();
        return gameDone;
    }

    private void loadChosenSquare() {
        m_ChosenSquare = m_Board.getSquareInPos(m_GameInfo.getChosenRow(), m_GameInfo.getChosenCol());
    }

    public abstract boolean checkIfGameDone();

    public void getGameStatus() {
        //getBoardToPrint();
        //m_GameInfo.setCurrPlayer(m_CurrentPlayer.getEPlayerTypeAsString());
    }

    public void getGameStatistics() {

        m_GameInfo.setNumOfMoves(m_NumOfMoves);
        m_GameInfo.setElapsedTime(m_secondsPassed);

        m_GameInfo.setRowPlayerScore(m_Players.get(0).getPlayerScore());
        m_GameInfo.setColPlayerScore(m_Players.get(1).getPlayerScore());
        loadCurrPlayerAndNumOfMovesToGameInfo();
    }

    public void getCurrMarkerPosition() {
        m_GameInfo.setMarkerCol(m_Board.getMarkerCol());
        m_GameInfo.setMarkerRow(m_Board.getMarkerRow());
    }

    public void playTurn(){
        m_CurrentPlayer.playTurn(m_Board, m_GameInfo , m_ChosenSquare, m_MarkMoves, m_PlayersMoves);
        loadBoardToGameInfo();
    }

    public abstract void playerQuit();

    public ArrayList<MoveData> getPlayersMoves() {
        return m_PlayersMoves;
    }

    public ArrayList<MoveData> getMarkMoves() {
        return m_MarkMoves;
    }

    //TODO: show prev\next move (the question is previos to what, do we hold a member of current move.
    //TODO: also todo is to block option to make move when only showing the previous moves
}