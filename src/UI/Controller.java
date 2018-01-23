package UI;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;

import domain.Facade;

public class Controller {

	private StartScreen screen;
	private Facade facade;
	
	public Controller()
	{
		facade = new Facade();
		screen = new StartScreen(this);
	}
	
	public void init()
	{
		
		screen.drawStartScreen();
	}
	
	public Facade getFacade()
	{
		return facade;
	}

}
