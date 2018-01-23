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
        if (args.length == 0)
            controller.init();
        else
            controller.init(args[0]);
    }
}
