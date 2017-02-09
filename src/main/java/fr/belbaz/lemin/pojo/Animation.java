package fr.belbaz.lemin.pojo;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ArrayListMultimap;

/**
 * @author Ourten 7 déc. 2016
 */
public class Animation
{
    private final ArrayListMultimap<Integer, Pair<Integer, Room>> steps = ArrayListMultimap.create();

    public Animation()
    {

    }

    public static Animation fromString(final Anthill anthill, final ArrayList<String> lines)
    {
        final Animation animation = new Animation();

        for (final String line : lines)
        {
            final String[] splitted = line.split(" ");
            for (final String ant : splitted)
            {
                if (ant.startsWith("L"))
                {
                    final int id = Integer.valueOf(StringUtils.removeFirst(ant.split("-")[0], "L"));
                    final String roomName = ant.split("-")[1];
                    animation.getSteps().put(lines.indexOf(line), Pair.of(id, anthill.getRoomByName(roomName)));
                }
            }
        }

        return animation;
    }

    public ArrayListMultimap<Integer, Pair<Integer, Room>> getSteps()
    {
        return this.steps;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.steps == null ? 0 : this.steps.hashCode());
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
        final Animation other = (Animation) obj;
        if (this.steps == null)
        {
            if (other.steps != null)
                return false;
        }
        else if (!this.steps.equals(other.steps))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Animation [steps=" + this.steps + "]";
    }
}