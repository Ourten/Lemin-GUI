package fr.belbaz.lemin.ui;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * @author Ourten 8 déc. 2016
 */
public class BoundLine extends Line
{
    public BoundLine(final DoubleProperty startX, final DoubleProperty startY, final DoubleProperty endX,
            final DoubleProperty endY)
    {
        this.startXProperty().bind(startX);
        this.startYProperty().bind(startY);
        this.endXProperty().bind(endX);
        this.endYProperty().bind(endY);
        this.setStrokeWidth(2);
        this.setStroke(Color.GRAY);
        this.setMouseTransparent(false);
    }
}
