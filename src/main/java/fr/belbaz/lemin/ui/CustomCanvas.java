package fr.belbaz.lemin.ui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;

public class CustomCanvas extends Pane
{
    DoubleProperty scale = new SimpleDoubleProperty(1.0);

    public CustomCanvas()
    {
        this.setPrefSize(600, 600);

        this.scaleXProperty().bind(this.scale);
        this.scaleYProperty().bind(this.scale);
    }

    public double getScale()
    {
        return this.scale.get();
    }

    public void setScale(final double scale)
    {
        this.scale.set(scale);
    }

    public void setPivot(final double x, final double y)
    {
        this.setTranslateX(this.getTranslateX() - x);
        this.setTranslateY(this.getTranslateY() - y);
    }
}