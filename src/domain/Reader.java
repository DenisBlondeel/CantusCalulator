package domain;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;



/**
 * Created by bart on 23/01/18.
 */
public class Reader {
    public Scanner sc;
    public Reader(String fileName){
        System.out.println(fileName);
        if(fileName.substring(fileName.lastIndexOf(".") + 1).contains("xls")){
            System.out.println("xls(x)");
            ExcelReading ER = new ExcelReading();
            ER.convert(new File(fileName),fileName.substring(0,fileName.lastIndexOf(".") + 1) + "csv",false);
            fileName = fileName.substring(0,fileName.lastIndexOf(".") + 1) + "csv";
        }
        try {
            this.sc = new Scanner(new File(fileName));
        }
        catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

    }
}



