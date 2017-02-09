package fr.belbaz.lemin.pojo;

/**
 * @author Ourten 7 déc. 2016
 */
public class Room
{
    private final String name;
    private final int    antID;
    private final int    posX, posY;

    public Room(final String name, final int posX, final int posY)
    {
        this.name = name;
        this.antID = 0;
        this.posX = posX;
        this.posY = posY;
    }

    public String getName()
    {
        return this.name;
    }

    public int getAntID()
    {
        return this.antID;
    }

    public int getPosX()
    {
        return this.posX;
    }

    public int getPosY()
    {
        return this.posY;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.antID;
        result = prime * result + (this.name == null ? 0 : this.name.hashCode());
        result = prime * result + this.posX;
        result = prime * result + this.posY;
        return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        final Room other = (Room) obj;
        if (this.antID != other.antID)
            return false;
        if (this.name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!this.name.equals(other.name))
            return false;
        if (this.posX != other.posX)
            return false;
        if (this.posY != other.posY)
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Room [name=" + this.name + ", antID=" + this.antID + ", posX=" + this.posX + ", posY=" + this.posY
                + "]";
    }
}