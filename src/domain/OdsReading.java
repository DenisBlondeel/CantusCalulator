package domain;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * Created by bart on 29/01/18.
 */
public class OdsReading {
    static void convert(File inputFile,String target, boolean debugOn){
            Sheet sheet;
            try {
                //Getting the 0th sheet for manipulation| pass sheet name as string
                sheet = SpreadSheet.createFromFile(inputFile).getSheet(0);
                File fout = new File(target);
                FileOutputStream fos = new FileOutputStream(fout);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                //Get row count and column count
                int nColCount = 4;
                int nRowCount = sheet.getRowCount();

                System.out.println("Rows :"+nRowCount);
                System.out.println("Cols :"+nColCount);
                //Iterating through each row of the selected sheet
                MutableCell cell = null;
                DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

                for(int nRowIndex = 0; nRowIndex < nRowCount; nRowIndex++)
                {
                    //Iterating through each column
                    StringBuilder line= new StringBuilder();
                    int nColIndex = 0;
                    for( ;nColIndex < nColCount; nColIndex++)
                    {
                        cell = sheet.getCellAt(nColIndex, nRowIndex);
                        if(cell.getValue()!="")
                            if(cell.getValueType()== ODValueType.DATE)
                                line.append(df.format(cell.getValue())).append(",");
                            else
                                line.append(cell.getValue()).append(",");
                    }
                    if( line.length()>0) {
                        System.out.println(line);
                        osw.write(line.substring(0, line.length() - 1) + "\n");
                    }
                    else{
                        break;
                    }

                }
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

}
