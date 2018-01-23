package domain;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;


/**
 * Created by bart on 23/01/18.
 */
public class Reader {
    public Scanner sc;
    public Reader(String fileName){
        try {
            this.sc = new Scanner(new File(fileName));
        }
        catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

    }
}
