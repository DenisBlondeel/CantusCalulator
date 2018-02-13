package UI;
import domain.*;
import java.io.*;
import java.util.*;
import java.io.IOException;
import java.nio.file.Paths;

public class CantusCalculator {

    public static void main(String[] args)
    {
        Controller controller = new Controller();
        //StartScreen startscreen = new StartScreen(controller);
        //startscreen.drawStartScreen();
        if (args.length < 1)
            controller.init();
        else
            controller.init(args[0]);
        }
}
