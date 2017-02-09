package fr.belbaz.lemin;

import java.util.ArrayList;
import java.util.Scanner;

import fr.belbaz.lemin.pojo.Anthill;
import fr.belbaz.lemin.ui.LeminUI;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Ourten 6 déc. 2016
 */
public class Lemin extends Application
{
    public static final ArrayList<String> instructions = new ArrayList<>();
    public static Anthill                 anthill;

    public static void main(final String[] args)
    {
        Lemin.read();
    }

    private static ArrayList<String> read()
    {
        final ArrayList<String> lines = new ArrayList<>();

        final Scanner scanner = new Scanner(System.in);

        String input;
        while (scanner.hasNextLine() && (input = scanner.nextLine()) != null && !input.isEmpty())
            lines.add(input);
        Lemin.anthill = Anthill.fromString(lines);
        lines.clear();
        while (scanner.hasNextLine() && (input = scanner.nextLine()) != null && !input.isEmpty())
            lines.add(input);
        scanner.close();
        Lemin.instructions.addAll(lines);
        Application.launch();

        return lines;
    }

    @Override
    public void start(final Stage stage) throws Exception
    {
        final LeminUI ui = new LeminUI();

        ui.init(stage);
    }
}
