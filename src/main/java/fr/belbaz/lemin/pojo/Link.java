package fr.belbaz.lemin.pojo;

/**
 * @author Ourten 7 déc. 2016
 */
public class Link
{
    private final Room from, to;

    public Link(final Room from, final Room to)
    {
        this.from = from;
        this.to = to;
    }

    public Room getFrom()
    {
        return this.from;
    }

    public Room getTo()
    {
        return this.to;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.from == null ? 0 : this.from.hashCode());
        result = prime * result + (this.to == null ? 0 : this.to.hashCode());
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
        final Link other = (Link) obj;
        if (this.from == null)
        {
            if (other.from != null)
                return false;
        }
        else if (!this.from.equals(other.from))
            return false;
        if (this.to == null)
        {
            if (other.to != null)
                return false;
        }
        else if (!this.to.equals(other.to))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Link [from=" + this.from + ", to=" + this.to + "]";
    }
}