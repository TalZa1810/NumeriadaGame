package UI;


import Generated.GameDescriptor;
import Shared.GameInfo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Scanner;

public class GameUI {

    private GameInfo m_GameInfo;

    public void ShowExceptionThrown(String message) {
        System.out.println(message);
    }

    public void notifyGameDone() {
        System.out.println("Game finished!");
    }

    public void notifyGameEndedByUser() {
        System.out.println("Anyways, the loser is the " + m_GameInfo.GetCurrPlayer() + " player for quitting so soon");
    }

    public void notifyOngoingGame() {
        System.out.println("Ongoing game. To load new game first end the current by choosing option 6");
    }

    public void notifyShouldSetGame() {
        System.out.println("First set game");
    }

    public void notifyShouldLoadFile() {
        System.out.println("First load file");
    }

    public void notifyPlayerExitGame(){
        System.out.println("BYE");
    }

    public enum eMenuOptions {
        LOAD_FILE, SET_GAME, GAME_STATUS, MAKE_MOVE, GET_STATISTICS, END_GAME, EXIT_GAME
    }

    public GameUI(GameInfo[] i_GameInfoWrapper) {
        m_GameInfo = i_GameInfoWrapper[0];
    }

    public int MainMenu() {
        int choice = -1;
        Scanner s = new Scanner(System.in);
        boolean validInput = false;

        while (!validInput) {
            System.out.println("1. LOAD GAME - load board game from xml");
            System.out.println("2. SET GAME - initiate game components and show board");
            System.out.println("3. GAME STATUS - show status");
            System.out.println("4. MAKE MOVE");
            System.out.println("5. STATISTICS - show game statistics");
            System.out.println("6. END GAME - stop current game and back to main menu");
            System.out.println("7. EXIT GAME - exit program");

            choice = s.nextInt() - 1;
            validInput = checkMenuInput(choice);
        }
        return choice;
    }

    public void ShowBoard() {
        int boardSize = m_GameInfo.GetBoardSize();
        String[][] board = m_GameInfo.GetBoard();
        final int numOfSpaces = 5;
        String strOfSpaces = "";
        int row = 0;
        int col = 0;

        for(int i = 0; i < numOfSpaces; i++) {
            strOfSpaces += " ";
        }

        //printing indentation
        System.out.print(strOfSpaces + "|");

        //printing columns
        for (int i = 0; i < boardSize; i++) {
            System.out.print("  " + (i + 1) + "  |");
        }

        System.out.print("\n");

        for (int i = 0; i < boardSize * 2; i++) {
            col = 0;
            if (i % 2 == 0) {
                for (int j = 0; j < boardSize * (numOfSpaces + 2); j++) {
                    System.out.print('=');
                }

                System.out.print("\n");
            }
            else {
                for (int j = 0; j <= boardSize; j++) {
                    if (j == 0) {
                        System.out.print("  " + (i / 2 + 1) + "  |");
                    }
                    else {
                        if (board[row][col] != "") {
                            String strToPrint = getStrOfValue(board[row][col]);
                            System.out.print(strToPrint + "|");
                        }
                        else {
                            System.out.print(strOfSpaces + "|");
                        }
                        col++;
                    }
                }

                System.out.print("\n");
                row++;
            }
        }
    }

    public int getGameMode() {
        Scanner s = new Scanner(System.in);
        System.out.println("Choose game mode:\n1. Human players\n2. Human and computer");
        int res = s.nextInt();

        while(res < 1 || res > 2) {
            System.out.println("Invalid input");
            res = s.nextInt();
        }

        return res;
    }

    private String getStrOfValue(String i_Num) {
        int count = 0;
        String res;
        for(int i = 0; i < i_Num.length(); i++) {
            count++;
        }

        if(count == 1){
            res = "  " + i_Num + "  ";
        }
        else if(count == 2){
            res = "  " + i_Num + " ";
        }
        else{
            res = " " + i_Num + " ";
        }

        return res;
    }

    public void ShowStatus() {
        ShowBoard(); //show board from game info object
        System.out.println("Current player is the " + m_GameInfo.GetCurrPlayer() + " player");
    }

    public void ShowStatistics() {

        int gameSeconds = m_GameInfo.GetElapsedTime() % 60;
        int gameMinutes =  m_GameInfo.GetElapsedTime() / 60 ;

        System.out.println("Number of moves made: " + m_GameInfo.GetNumOfMoves());
        System.out.println("Time elapsed (min:sec) is:  " + gameMinutes + ':' + gameSeconds );
        System.out.println("Row player score: " + m_GameInfo.GetRowPlayerScore());
        System.out.println("Column player score: " + m_GameInfo.GetColPlayerScore());
    }

    public int GetMoveFromUser() {
        Scanner s = new Scanner(System.in);
        int choice;

        System.out.print("Please enter your choice in ");

        if (m_GameInfo.GetCurrPlayer().equals("row")) {
            System.out.println("row " + (m_GameInfo.getMarkerRow() + 1));
        }
        else {
            System.out.println("column " + (m_GameInfo.getMarkerCol() + 1));
        }

        choice = s.nextInt();
        while(choice < 1 || choice > m_GameInfo.GetBoardSize())
        {
            System.out.println("Invalid input/nPlease enter number between 1 to " + m_GameInfo.GetBoardSize());
            choice = s.nextInt();
        }

        return choice;
    }

    public GameDescriptor fromXmlFileToObject() {
        System.out.println("Enter path for xml file");
        Scanner s = new Scanner(System.in);
        String path =  s.nextLine();
        GameDescriptor descriptor = null;

        //checking if file type is correct
        while (!path.endsWith( ".xml" )){
            System.out.println("wrong xml file");
            path =  s.nextLine();
        }

        try {

            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(GameDescriptor.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            descriptor = (GameDescriptor) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("Failed to load file");
        }

        return descriptor;
    }


    private boolean checkMenuInput(int i_Input) {
        if (i_Input < 0 || i_Input > 6)
            return false;

        return true;
    }
}
