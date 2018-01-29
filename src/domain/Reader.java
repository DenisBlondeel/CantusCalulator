package domain;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;



/**
 * Created by bart on 23/01/18.
 */
public class Reader {
    Scanner sc;
    public Reader(String fileName){
        System.out.println(fileName);
        if(fileName.substring(fileName.lastIndexOf(".") + 1).contains("xls")){
            System.out.println("xls(x)");
            ExcelReading.convert(new File(fileName),fileName.substring(0,fileName.lastIndexOf(".") + 1) + "csv",false);
            fileName = fileName.substring(0,fileName.lastIndexOf(".") + 1) + "csv";
        } else if (fileName.substring(fileName.lastIndexOf(".") + 1).contains("ods")){
            System.out.println("ODF");
            OdsReading.convert(new File(fileName),fileName.substring(0,fileName.lastIndexOf(".") + 1) + "csv",false);
            fileName = fileName.substring(0,fileName.lastIndexOf(".") + 1) + "csv";
            System.out.println(fileName);
        }
        try {
            this.sc = new Scanner(new File(fileName));
        }
        catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

    }
}



