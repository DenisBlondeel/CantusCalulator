package UI;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;

public class Controller {

	private StartScreen screen;
	
	public Controller()
	{
		screen = new StartScreen();
	}
	
	public void init()
	{
		
		screen.drawStartScreen(this);
		
		/*if(args.length == 0){
	        System.out.println("no input was given.");
	        return ;
		}
		else{
	    Scanner sc = new Scanner(new File(args[0])).useLocale(Locale.US);
	    
		}*/
	}

}
