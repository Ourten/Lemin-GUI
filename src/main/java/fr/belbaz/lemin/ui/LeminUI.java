package fr.belbaz.lemin.ui;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;

import fr.belbaz.lemin.Lemin;
import fr.belbaz.lemin.pojo.Animation;
import fr.belbaz.lemin.pojo.Anthill;
import fr.belbaz.lemin.pojo.Delta;
import fr.belbaz.lemin.pojo.Link;
import fr.belbaz.lemin.pojo.Room;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LeminUI
{
    private final HashMap<Room, Circle> nodes;
    private final SimpleIntegerProperty startAnts;
    private final SimpleIntegerProperty endAnts;

    public LeminUI()
    {
        this.nodes = new HashMap<>();
        this.startAnts = new SimpleIntegerProperty(0);
        this.endAnts = new SimpleIntegerProperty(0);

    }

    public void init(final Stage stage)
    {
        final StackPane root = new StackPane();
        root.getStyleClass().add("root");
        final Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add("/fr/belbaz/lemin/css/style.css");
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Open+Sans:300");

        stage.setTitle("Lemin - Visualizator");
        stage.setScene(scene);
        stage.show();

        final CustomCanvas group = new CustomCanvas();
        this.drawGraph(Lemin.anthill, group);

        final Pane pane = new Pane();
        root.getChildren().add(pane);
        pane.getChildren().add(group);

        final SceneEventHandler sceneGestures = new SceneEventHandler(group);
        scene.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        final Delta dragDelta = new Delta();
        pane.setOnMousePressed(mouseEvent ->
        {
            dragDelta.x = group.getLayoutX() - mouseEvent.getSceneX();
            dragDelta.y = group.getLayoutY() - mouseEvent.getSceneY();
            group.setCursor(Cursor.MOVE);
        });
        pane.setOnMouseReleased(mouseEvent -> group.setCursor(Cursor.DEFAULT));
        pane.setOnMouseDragged(mouseEvent ->
        {
            group.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
            group.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
        });

        final Button launch = new Button("START");
        launch.setMaxSize(128, 32);
        launch.getStyleClass().add("launch");
        root.getChildren().add(launch);
        StackPane.setAlignment(launch, Pos.BOTTOM_CENTER);

        this.startAnts.setValue(Lemin.anthill.getAnts());
        launch.setOnAction(e -> this.launchAnimation(Lemin.anthill,
                Animation.fromString(Lemin.anthill, Lemin.instructions), group, launch));
    }

    private void launchAnimation(final Anthill anthill, final Animation animation, final Pane group,
            final Button launch)
    {
        // Init
        launch.setDisable(true);
        this.startAnts.setValue(Lemin.anthill.getAnts());
        this.endAnts.setValue(0);

        final HashMap<Integer, AntNode> ants = new HashMap<>();
        final double totalTime = 10_000;

        try
        {
            final SequentialTransition sequentialTransition = new SequentialTransition();
            final ArrayList<Integer> seens = new ArrayList<>();

            for (int i = 1; i <= anthill.getAnts(); i++)
                ants.put(i, new AntNode(i, anthill.getStart()));
            group.getChildren().addAll(ants.values());
            for (final Integer step : animation.getSteps().keySet())
            {
                for (final Pair<Integer, Room> ant : animation.getSteps().get(step))
                {
                    final PauseTransition pauseTransition = new PauseTransition(Duration.millis(1));
                    pauseTransition.setOnFinished(e ->
                    {
                        if (!seens.contains(ant.getLeft()))
                        {
                            this.startAnts.setValue(this.startAnts.get() - 1);
                            ants.get(ant.getLeft()).setVisible(true);
                            seens.add(ant.getLeft());
                        }
                    });

                    final TranslateTransition translateTransition = new TranslateTransition(
                            Duration.millis(totalTime / animation.getSteps().size()), ants.get(ant.getLeft()));
                    translateTransition
                            .setFromX(this.nodes.get(ants.get(ant.getLeft()).getPosition()).getCenterX() - 8);
                    translateTransition
                            .setFromY(this.nodes.get(ants.get(ant.getLeft()).getPosition()).getCenterY() - 8);
                    translateTransition.setToX(this.nodes.get(ant.getRight()).getCenterX() - 8);
                    translateTransition.setToY(this.nodes.get(ant.getRight()).getCenterY() - 8);
                    translateTransition.setInterpolator(Interpolator.EASE_BOTH);
                    translateTransition.setCycleCount(1);
                    translateTransition.setOnFinished(e ->
                    {
                        if (ant.getRight().equals(anthill.getEnd()))
                        {
                            this.endAnts.setValue(this.endAnts.get() + 1);
                            ants.get(ant.getLeft()).setVisible(false);
                        }
                    });

                    ants.get(ant.getLeft()).setPosition(ant.getRight());
                    sequentialTransition.getChildren().addAll(pauseTransition, translateTransition);
                }
            }
            sequentialTransition.setCycleCount(1);
            sequentialTransition.setOnFinished(e ->
            {
                ants.values().forEach(node -> node.setVisible(false));
                this.endAnts.setValue(anthill.getAnts());
                launch.setDisable(false);
            });
            sequentialTransition.play();
        } catch (final Exception e)
        {
            e.printStackTrace();
        }
    }

    private void drawGraph(final Anthill anthill, final Pane group)
    {
        for (final Room room : anthill.getRooms())
        {
            final Circle circle = new Circle(64 * room.getPosX(), 64 * room.getPosY(), 16);
            circle.getStyleClass().add("node");
            if (anthill.getStart().equals(room))
                circle.setId("start");
            if (anthill.getEnd().equals(room))
                circle.setId("end");
            circle.setFill(Color.LIGHTBLUE);
            circle.setStroke(Color.web("#808080"));
            circle.setStrokeWidth(1);
            group.getChildren().add(circle);
            this.nodes.put(room, circle);

            final Text text = new Text(room.getName());
            text.setX(64 * room.getPosX() - text.getLayoutBounds().getWidth() / 2);
            text.setY(64 * room.getPosY() + 19 + text.getLayoutBounds().getHeight());
            text.getStyleClass().add("name");
            group.getChildren().add(text);

            if (room.equals(anthill.getStart()))
            {
                final Text numbers = new Text();
                numbers.textProperty().bind(this.startAnts.asString());
                numbers.getStyleClass().add("count");
                numbers.layoutBoundsProperty()
                        .addListener(l -> numbers.setX(64 * room.getPosX() - numbers.getLayoutBounds().getWidth() / 2));
                numbers.setX(64 * room.getPosX() - numbers.getLayoutBounds().getWidth() / 2);
                numbers.setY(64 * room.getPosY() + numbers.getLayoutBounds().getHeight() / 4);
                group.getChildren().add(numbers);
            }
            else if (room.equals(anthill.getEnd()))
            {
                final Text numbers = new Text();
                numbers.textProperty().bind(this.endAnts.asString());
                numbers.getStyleClass().add("count");
                numbers.layoutBoundsProperty()
                        .addListener(l -> numbers.setX(64 * room.getPosX() - numbers.getLayoutBounds().getWidth() / 2));
                numbers.setX(64 * room.getPosX() - numbers.getLayoutBounds().getWidth() / 2);
                numbers.setY(64 * room.getPosY() + numbers.getLayoutBounds().getHeight() / 4);
                group.getChildren().add(numbers);
            }
        }

        for (final Link link : anthill.getLinks())
        {
            final BoundLine line = new BoundLine(this.nodes.get(link.getFrom()).centerXProperty(),
                    this.nodes.get(link.getFrom()).centerYProperty(), this.nodes.get(link.getTo()).centerXProperty(),
                    this.nodes.get(link.getTo()).centerYProperty());
            line.setId("link");
            group.getChildren().add(line);
            line.toBack();
        }
    }
}
