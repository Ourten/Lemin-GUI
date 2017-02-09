package fr.belbaz.lemin.pojo;

import java.util.ArrayList;

/**
 * @author Ourten 7 déc. 2016
 */
public class Anthill
{
    private int                   ants;
    private Room                  start, end;
    private final ArrayList<Room> rooms;
    private final ArrayList<Link> links;

    public Anthill()
    {
        this.rooms = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    public static Anthill fromString(final ArrayList<String> string)
    {
        final Anthill rtn = new Anthill();

        if (!string.isEmpty())
        {
            rtn.setAnts(Integer.parseInt(string.get(0)));

            boolean start = false;
            boolean end = false;
            for (final String line : string)
            {
                if (line.equals("##start"))
                    start = true;
                else if (line.equalsIgnoreCase("##end"))
                    end = true;
                if (line.startsWith("#"))
                    continue;
                if (line.contains("-"))
                {
                    final String[] splitted = line.split("-");
                    rtn.getLinks()
                            .add(new Link(
                                    rtn.getRooms().stream().filter(room -> room.getName().equals(splitted[0]))
                                            .findFirst().get(),
                                    rtn.getRooms().stream().filter(room -> room.getName().equals(splitted[1]))
                                            .findFirst().get()));
                }
                else
                {
                    final String[] splitted = line.split(" ");
                    if (splitted.length == 3)
                    {
                        final Room room = new Room(splitted[0], Integer.valueOf(splitted[1]),
                                Integer.valueOf(splitted[2]));
                        rtn.getRooms().add(room);
                        if (start)
                        {
                            rtn.setStart(room);
                            start = false;
                        }
                        else if (end)
                        {
                            rtn.setEnd(room);
                            end = false;
                        }
                    }
                }
            }
        }
        return rtn;
    }

    public Room getRoomByName(final String name)
    {
        return this.rooms.stream().filter(room -> room.getName().equals(name)).findFirst().orElse(null);
    }

    public int getAnts()
    {
        return this.ants;
    }

    public void setAnts(final int ants)
    {
        this.ants = ants;
    }

    public Room getStart()
    {
        return this.start;
    }

    public void setStart(final Room start)
    {
        this.start = start;
    }

    public Room getEnd()
    {
        return this.end;
    }

    public void setEnd(final Room end)
    {
        this.end = end;
    }

    public ArrayList<Room> getRooms()
    {
        return this.rooms;
    }

    public ArrayList<Link> getLinks()
    {
        return this.links;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.ants;
        result = prime * result + (this.end == null ? 0 : this.end.hashCode());
        result = prime * result + (this.links == null ? 0 : this.links.hashCode());
        result = prime * result + (this.rooms == null ? 0 : this.rooms.hashCode());
        result = prime * result + (this.start == null ? 0 : this.start.hashCode());
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
        final Anthill other = (Anthill) obj;
        if (this.ants != other.ants)
            return false;
        if (this.end == null)
        {
            if (other.end != null)
                return false;
        }
        else if (!this.end.equals(other.end))
            return false;
        if (this.links == null)
        {
            if (other.links != null)
                return false;
        }
        else if (!this.links.equals(other.links))
            return false;
        if (this.rooms == null)
        {
            if (other.rooms != null)
                return false;
        }
        else if (!this.rooms.equals(other.rooms))
            return false;
        if (this.start == null)
        {
            if (other.start != null)
                return false;
        }
        else if (!this.start.equals(other.start))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Anthill [ants=" + this.ants + ", start=" + this.start + ", end=" + this.end + ", rooms=" + this.rooms
                + ", links=" + this.links + "]";
    }
}