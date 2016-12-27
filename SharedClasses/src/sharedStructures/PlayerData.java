package sharedStructures;

public class PlayerData {
    private String m_Name;
    private int m_ID;
    private eColor m_Color;
    private ePlayerType m_PlayerType;

    public ePlayerType getType() {
        return m_PlayerType;
    }

    public eColor getColor() {
        return m_Color;
    }

    public int getID() {
        return m_ID;
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
}
