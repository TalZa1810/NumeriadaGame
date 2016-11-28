package UI;

/**
 * Created by Tal on 11/26/2016.
 */

import java.lang.*;
import java.sql.Time;
import java.util.Scanner;

public class Menu implements IGUI {

    private enum eMenuOptions {
        LOAD_FILE, SET_GAME, GAME_STATUS, MAKE_MOVE, GET_STATISTICS, END_GAME, EXIT_GAME
    }

    public void HandleMenuChoice() {

        boolean userWantsToPlay = true;

        while (userWantsToPlay) {

            eMenuOptions menuSelection = eMenuOptions.values()[mainMenu()];
            switch (menuSelection)
            {
                case LOAD_FILE:
                    //LOAD FILE
                    break;
                case SET_GAME:
                    //SET GAME AND SHOW BOARD
                    break;
                case GAME_STATUS:
                    //SHOW GAME STATUS
                    break;
                case MAKE_MOVE:
                    //MAKE MOVE
                    break;
                case GET_STATISTICS:
                    //PRINT STATISTICS
                    break;
                case END_GAME:
                    //QUIT CURRENT GAME AND SHOW MAIN MENU AGAIN
                    break;
                case EXIT_GAME:
                    userWantsToPlay = false;
                    break;
                default:
                    userWantsToPlay = false;
            }
        }
    }

    private int mainMenu()  {
        int choice;
        Scanner s = new Scanner(System.in);

        System.out.println("1. LOAD GAME - load board game from xml");
        System.out.println("2. SET GAME - initiate game components and show board");
        System.out.println("3. GAME STATUS - show status");
        System.out.println("4. MAKE MOVE");
        System.out.println("5. STATISTICS - show game statistics");
        System.out.println("6. END GAME - stop current game and back to main menu");
        System.out.println("7. EXIT GAME - exit program");

        choice = s.nextInt();
        return choice;
    }

    public void ShowBoard(int i_MaxRange, int i_BoardSize, char[][] i_Board) {
        int numOfSpaces = getNumOfDigits(i_MaxRange) + 2;
        String strOfSpaces = " ";

        for(int i = 1; i < numOfSpaces; i++) {
            strOfSpaces += " ";
        }

        System.out.print(strOfSpaces);

        for (int i = 0; i < i_BoardSize; i++) {
            System.out.print((i + 1) + strOfSpaces);
        }

        System.out.print("\n");

        for (int i = 0; i < i_BoardSize * 2; i++) {
            if (i % 2 == 1) {
                for (int j = 0; j < (i_BoardSize * numOfSpaces) + 1; j++) {
                    System.out.print('=');
                }

                System.out.print("\n");
            }
            else {
                for (int j = 0; j < (i_BoardSize * numOfSpaces) + 1; j++) {
                    if (j % numOfSpaces == 0) {
                        System.out.print('|');
                    }
                    else {
                        if(i % 2 == 0 && (j - 2) % numOfSpaces == 0 && i_Board[(i / 2)][(j / numOfSpaces)] != ' ') {
                            System.out.print(i_Board[(i / 2)][(j / 4)]);
                        }
                        else {
                            System.out.print(strOfSpaces);
                        }
                    }
                }

                System.out.print("\n");
            }
        }
    }

    private int getNumOfDigits(int i_MaxRange) {
        int res = 0;
        for(int i = 0; i < i_MaxRange; i++)
            res++;

        return res;
    }

    public void ShowStatus(int i_MaxRange, int i_BoardSize, char[][] i_Board, String i_NextPlayer) {
        ShowBoard(i_MaxRange, i_BoardSize, i_Board);
        System.out.println("Next is the " + i_NextPlayer + " player's turn");
    }

    public void ShowStatistics(int i_NumOfMoves, Time i_ElapsedTime, int i_RowPlayerScore, int i_ColPlayerScore) {
        System.out.println("Number of moves made: " + i_NumOfMoves);
        System.out.println("Time elapsed: " + i_ElapsedTime);
        System.out.println("Row player score: " + i_RowPlayerScore);
        System.out.println("Column player score: " + i_ColPlayerScore);
    }
}
