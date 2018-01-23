package UI;
import domain.*;
import java.io.*;
import java.util.*;
import java.io.IOException; 
import java.nio.file.Paths;


public class CantusCalculator {

<<<<<<< HEAD

	public static void main(String[] args) 
		{
			Controller controller = new Controller();
			controller.init();
		}
=======
	public CantusVerzameling CV;
public static void main(String[] args) throws FileNotFoundException, IOException{
	if(args.length == 0){
        System.out.println("no input was given.");
        return ;
	}
	else{
    Scanner in = new Scanner(Paths.get(args[0]));
    in.useDelimiter(",");
	}
	Facade f = new Facade();
}
>>>>>>> branch 'master' of https://github.com/r0372092/CantusCalulator
}
