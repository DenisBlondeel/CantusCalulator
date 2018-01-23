package UI;
import domain.*;
import java.io.*;
import java.util.*;
import java.io.IOException; 
import java.nio.file.Paths;


public class CantusCalculator {

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
}
