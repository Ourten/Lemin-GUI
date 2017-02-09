package fr.belbaz.lemin.ui;

import fr.belbaz.lemin.pojo.Room;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * @author Ourten 8 déc. 2016
 */
public class AntNode extends StackPane
{
    private final int id;
    private Room      position;

    public AntNode(final int id, final Room position)
    {
        this.id = id;
        this.position = position;

        final Circle circle = new Circle(8);
        circle.setStroke(Color.web("#808080"));
        circle.setId("ant");
        circle.setStrokeWidth(1);
        StackPane.setAlignment(circle, Pos.CENTER);

        final Text text = new Text(String.valueOf(id));
        StackPane.setAlignment(text, Pos.CENTER);

        this.getChildren().addAll(circle, text);
        this.setVisible(false);
    }

    public int getID()
    {
        return this.id;
    }

    public Room getPosition()
    {
        return this.position;
    }

    public void setPosition(final Room position)
    {
        this.position = position;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.id;
        result = prime * result + (this.position == null ? 0 : this.position.hashCode());
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
        final AntNode other = (AntNode) obj;
        if (this.id != other.id)
            return false;
        if (this.position == null)
        {
            if (other.position != null)
                return false;
        }
        else if (!this.position.equals(other.position))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "AntNode [id=" + this.id + ", position=" + this.position + "]";
    }
}