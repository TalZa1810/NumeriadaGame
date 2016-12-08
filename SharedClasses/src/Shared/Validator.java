package Shared;


import Generated.GameDescriptor;

import java.util.List;

public class Validator {
    GameInfo m_GameInfo;

    public Validator(GameInfo i_GameInfo){
        m_GameInfo = i_GameInfo;
    }

    public void checkBoardSize(int i_Size) throws Exception {
        if(i_Size > 50 || i_Size < 5) {
            throw(new Exception("Invalid board size"));
        }
    }

    public void checkValidSquaresLocation(List<GameDescriptor.Board.Structure.Squares.Square> squares) throws Exception {
        boolean validInput = true;
        String exceptionMessage = "";

        for(GameDescriptor.Board.Structure.Squares.Square s: squares){
            if(s.getRow().intValue() > m_GameInfo.GetBoardSize() || s.getRow().intValue() < 0) {
                validInput = false;
                exceptionMessage = "Square row out of bounds";
            }
            else if(s.getColumn().intValue() > m_GameInfo.GetBoardSize() || s.getColumn().intValue() < 0) {
                validInput = false;
                exceptionMessage = "Square column out of bounds";
            }

            for(GameDescriptor.Board.Structure.Squares.Square currSquare: squares) {
                if(s.getColumn() == currSquare.getColumn() && s.getRow() == currSquare.getRow()){
                    validInput = false;
                    exceptionMessage = "Two values assigned to same square";
                }
            }
        }

        if(!validInput) {
            throw(new Exception(exceptionMessage));
        }
    }

    public void checkRangeForRandomBoard() throws Exception {
        if(m_GameInfo.GetRangeFrom() > m_GameInfo.GetRangeTo()){
            throw(new Exception("Illegal range of numbers: FROM is bigger than TO"));
        }

        if(m_GameInfo.GetRangeTo() - m_GameInfo.GetRangeFrom() + 1 > (m_GameInfo.GetBoardSize() * m_GameInfo.GetBoardSize()) - 1){
            throw(new Exception("Range of numbers is bigger than size of board"));
        }
    }
}
