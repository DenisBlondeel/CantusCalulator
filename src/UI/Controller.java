package UI;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.JFrame;

import domain.Facade;

public class Controller {

    private StartScreen screen;
    private Observer main;
    private Facade facade;

    public Controller()
    {
        main = new MainPane(this);
        facade = new Facade();
        screen = new StartScreen(this);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        facade.addObserver(main);
    }

    public void init()
    {
        screen.drawStartScreen();
        //facade.drawTimeline();
        //facade.drawPieChart();
    }

    public void init(String file) {
        getFacade().passFile(file);
        //facade.drawTimeline();
        //facade.drawPieChart();
    }

    public Facade getFacade()
    {
        return facade;
    }
}
