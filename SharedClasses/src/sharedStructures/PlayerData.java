package sharedStructures;

public class PlayerData {
    private String m_Name;
    private int m_ID;
    private eColor m_Color;
    private ePlayerType m_PlayerType;
    private int m_Score;

    public PlayerData(String i_Name, int i_ID, eColor i_Color, ePlayerType i_PlayerType, int i_Score){
        m_Name = i_Name;
        m_ID = i_ID;
        m_Color = i_Color;
        m_PlayerType = i_PlayerType;
        m_Score = i_Score;
    }

    public PlayerData() {}

    public ePlayerType getType() {
        return m_PlayerType;
    }

    public eColor getColor() {
        return m_Color;
    }

    public int getID() {
        return m_ID;
    }

    public int getScore() {
        return m_Score;
    }

    public String getName() {
        return m_Name;
    }

    public void setColor(eColor i_Color) {
        this.m_Color = i_Color;
    }

    public void setID(int i_ID) {
        this.m_ID = i_ID;
    }

    public void setName(String i_Name) {
        this.m_Name = i_Name;
    }

    public void setType(ePlayerType i_PlayerType) {
        this.m_PlayerType = i_PlayerType;
    }

    public void setScore ( int i_Score) { this.m_Score = i_Score;}
}
