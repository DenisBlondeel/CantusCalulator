package UI;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.JFrame;

import domain.Facade;

public class Controller {

    private StartScreen screen;
    private MainPane main;
    private Facade facade;

    public Controller()
    {
        facade = new Facade();
        screen = new StartScreen(this);
        main = new MainPane(this);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void init()
    {
        screen.drawStartScreen();
    }

    public void init(String file) {
        getFacade().passFile(file);
        facade.drawTimeline();
    }

    public Facade getFacade()
    {
        return facade;
    }
}
