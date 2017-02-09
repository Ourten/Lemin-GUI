package fr.belbaz.lemin.ui;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;

public class SceneEventHandler
{
    private static final double             MAX_SCALE = 10.0d;
    private static final double             MIN_SCALE = .1d;

    private final CustomCanvas              canvas;

    private final EventHandler<ScrollEvent> onScrollEventHandler;

    public SceneEventHandler(final CustomCanvas canvas)
    {
        this.canvas = canvas;

        this.onScrollEventHandler = event ->
        {
            final double delta = 1.2;

            double scale = this.canvas.getScale();
            final double oldScale = scale;

            if (event.getDeltaY() < 0)
                scale /= delta;
            else
                scale *= delta;

            scale = SceneEventHandler.clamp(scale, SceneEventHandler.MIN_SCALE, SceneEventHandler.MAX_SCALE);

            final double f = scale / oldScale - 1;

            final double dx = event.getSceneX()
                    - (this.canvas.getBoundsInParent().getWidth() / 2 + this.canvas.getBoundsInParent().getMinX());
            final double dy = event.getSceneY() - (this.canvas.getBoundsInParent().getHeight() / 2
                    + SceneEventHandler.this.canvas.getBoundsInParent().getMinY());

            this.canvas.setScale(scale);
            this.canvas.setPivot(f * dx, f * dy);
            event.consume();
        };
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler()
    {
        return this.onScrollEventHandler;
    }

    public static double clamp(final double value, final double min, final double max)
    {
        if (Double.compare(value, min) < 0)
            return min;
        if (Double.compare(value, max) > 0)
            return max;
        return value;
    }
}