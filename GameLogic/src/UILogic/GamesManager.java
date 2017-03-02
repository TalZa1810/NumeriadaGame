package UILogic;



import Generated.GameDescriptor;
import shared.GameInfo;
import shared.Validator;
import sharedStructures.eColor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*

import GriddlerWeb.GameLogic.GameLogic;
import GriddlerWeb.GameLogic.XmlManager;
import GriddlerWeb.jaxb.schema.generated.Slice;
import GriddlerWeb.jaxb.schema.generated.Square;
*/

public class GamesManager
{
    private final Map<String, GameInfo> m_GamesMap;
    GameDescriptor m_GameDescriptor = new GameDescriptor();
    Validator m_Validator = new Validator();

    public GamesManager()
    {
        m_GamesMap = new HashMap<>();
    }

    // returm error or null if all good
    public String addNewGame(InputStream file, String userNameFromSession)
    {
        boolean gameLoaded;

        synchronized (this) {
            gameLoaded = loadXML(file, userNameFromSession);
        }
        /*if(gameLoaded)
        {
            synchronized (this) {
                String gameTitle = m_GameDescriptor.getDynamicPlayers().getGameTitle();
                if (m_GamesMap.get(gameTitle) == null) // game not exsist
                {
                    int rows = m_XmlManager.getGameData().getBoard().getDefinition().getRows().intValue();
                    int cols = m_XmlManager.getGameData().getBoard().getDefinition().getColumns().intValue();
                    ////////////////
                    ArrayList<Cell> solutionCells = new ArrayList();
                    ArrayList<BlockValues>[] rowBlocks, colsBlock;
                    rowBlocks = new ArrayList[rows];
                    colsBlock = new ArrayList[cols];
                    getSolutionAndBlocks(solutionCells, rowBlocks, colsBlock);
                    ////////////////
                    int totalPlayers = Integer.parseInt(m_XmlManager.getGameData().getDynamicMultiPlayers().getTotalPlayers());
                    int totalRounds = Integer.parseInt(m_XmlManager.getGameData().getDynamicMultiPlayers().getTotalmoves());

                    GameLogic newGame = new GameLogic(userNameFromSession, gameTitle, totalPlayers, totalRounds,
                            rows, cols, rowBlocks, colsBlock, solutionCells);
                    m_GamesMap.put(gameTitle, newGame);
                } else {
                    return "Game With The Same Name Exsist";
                }
            }
        }*/
        if(!gameLoaded) {
            //TODO: get error message in a variable or something and see what nico does with it
            //return m_XmlManager.m_ExceptionOcurred.toString();
        }
        return null;
    }

    public boolean loadXML(InputStream file, String userNameFromSession) {
       GameInfo newGameInfo = new GameInfo();
        boolean res = true;
        try {
            m_GameDescriptor = fromXmlFileToObject(file);
            if(m_GameDescriptor != null) {
                getDataFromGeneratedXML(newGameInfo);
                //m_StatusBar.set(m_Notifier.fileWasLoadedSuccessfully());
                //createGame();
                //initializeGameController(m_MainWindow);
                m_GamesMap.put(newGameInfo.getGameTitle(), newGameInfo);
            }
        }
        catch(Exception e){
            //m_StatusBar.set(m_Notifier.showExceptionThrown(e.getMessage()));
            res = false;
        }
        finally {
            return res;
        }
    }

    public GameDescriptor fromXmlFileToObject(InputStream file) {
        return unmarshalFile(file);
    }

    private GameDescriptor unmarshalFile(InputStream file){
        GameDescriptor descriptor = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(GameDescriptor.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            descriptor = (GameDescriptor) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            e.printStackTrace();
           // m_StatusBar.set("Failed to load file");
        }

        return descriptor;
    }

    private void getDataFromGeneratedXML(GameInfo gameInfo) throws Exception {
        m_Validator = new Validator(gameInfo);

        gameInfo.setGameType(m_GameDescriptor.getGameType());
        m_Validator.checkBoardSize(m_GameDescriptor.getBoard().getSize().intValue());
        gameInfo.setBoardSize(m_GameDescriptor.getBoard().getSize().intValue());
        gameInfo.setBoardStructure(m_GameDescriptor.getBoard().getStructure().getType());

        if(gameInfo.getBoardStructure().equals("Random")) {
            gameInfo.setRangeFrom(m_GameDescriptor.getBoard().getStructure().getRange().getFrom());
            gameInfo.setRangeTo(m_GameDescriptor.getBoard().getStructure().getRange().getTo());
        }
        //TODO: check if num of players in xml file is the min and max of players
        gameInfo.setNumOfPlayers(m_GameDescriptor.getDynamicPlayers().getTotalPlayers());
        gameInfo.initBoard();
        setBoardValuesFromXML(gameInfo);
    }

    private void setBoardValuesFromXML(GameInfo gameInfo) throws Exception {
        if(gameInfo.getBoardStructure().equals("Explicit")) {
            int row, col;

            List<GameDescriptor.Board.Structure.Squares.Square> squares = m_GameDescriptor.getBoard().getStructure().getSquares().getSquare();

            m_Validator.checkValidSquaresLocation(squares);

            for (GameDescriptor.Board.Structure.Squares.Square s : squares) {

                col = s.getColumn().intValue() - 1;
                row = s.getRow().intValue() - 1;
                gameInfo.setSquare(row, col, s.getValue().toString(), s.getColor());
            }

            m_Validator.checkValidMarkerLocation(m_GameDescriptor.getBoard().getStructure().getSquares().getMarker());
            GameDescriptor.Board.Structure.Squares.Marker marker = m_GameDescriptor.getBoard().getStructure().getSquares().getMarker();
            gameInfo.setSquare(marker.getRow().intValue() - 1, marker.getColumn().intValue() - 1, "@", eColor.BLACK.ordinal());
        }
        else {
            m_Validator.checkRangeForRandomBoard(gameInfo.getGameType());
        }
    }

    public Map<String, GameInfo> getGames() {
        return Collections.unmodifiableMap(m_GamesMap);
    }
/*
    //region Load Game
    public void getSolutionAndBlocks(ArrayList<Cell> i_SolutionCells,ArrayList<BlockValues>[] i_RowBlock, ArrayList<BlockValues>[] i_ColBlocks)
    {
        getBlocks(i_RowBlock, i_ColBlocks);
        List<Square> solutionSquares = m_XmlManager.getGameData().getBoard().getSolution().getSquare();
        for (Square currSquare : solutionSquares) {
            int currR = currSquare.getRow().intValue();
            int currC = currSquare.getColumn().intValue();
            i_SolutionCells.add(new Cell(currR, currC, null));
        }
    }

    private void getBlocks(ArrayList<BlockValues>[]i_RowBlocks,ArrayList<BlockValues>[] i_CollBlocks)
    {
        List<Slice> allSlices = m_XmlManager.getGameData().getBoard().getDefinition().getSlices().getSlice();
        allSlices.sort((a, b) -> a.getId().intValue() - b.getId().intValue());
        boolean incrRow=false;
        int indexRowArray = 0, indexColArray = 0;
        initializeBlocks(i_RowBlocks,i_CollBlocks);
        for (Slice currSlice : allSlices)
        {
            String[] sizeB = currSlice.getBlocks().trim().split(",");
            for (String currBlock : sizeB)
            {
                currBlock=currBlock.trim();
                int currNum=Integer.parseInt(currBlock);
                BlockValues blockToAdd = new BlockValues(currNum);
                if (currSlice.getOrientation().equals("row"))
                {
                    i_RowBlocks[indexRowArray].add(blockToAdd);
                    incrRow=true;
                } else
                {
                    i_CollBlocks[indexColArray].add(blockToAdd);
                    incrRow=false;
                }
            }
            if(incrRow) { indexRowArray++;}
            else    { indexColArray++;}
        }
    }

    private void initializeBlocks(ArrayList<BlockValues>[]i_RowBlocks,ArrayList<BlockValues>[]i_CollBlocks)
    {
        for(int index = 0; index < i_RowBlocks.length; index++)
        {
            i_RowBlocks[index] = new ArrayList<>();
        }
        for(int index=0; index < i_CollBlocks.length; index++)
        {
            i_CollBlocks[index] = new ArrayList<>();
        }
    }
    */

    public GameInfo getSpecificGame(String gameTitleToJoin)
    {
        return m_GamesMap.get(gameTitleToJoin);
    }
    //endregion
}
