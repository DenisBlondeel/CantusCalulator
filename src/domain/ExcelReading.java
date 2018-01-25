package domain;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
class ExcelReading {

    static void convert(File inputFile,String target, boolean debugOn){
        InputStream inp = null;
        try {
            print("inputFile.getName()"+inputFile.getName(),debugOn);
            inp = new FileInputStream(inputFile);
            print("file read",debugOn);
            Workbook wb = WorkbookFactory.create(inp);
            //Workbook wb = WorkbookFactory.create(inp);

            print("targetDir="+target,debugOn);
            File fout = new File(target);
            print("fout="+fout.getName(),debugOn);
            FileOutputStream fos = new FileOutputStream(fout);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            print("<wb.getNumberOfSheets()="+wb.getNumberOfSheets(),debugOn);
            for(int i=0;i<wb.getNumberOfSheets();i++) {
                print(wb.getSheetAt(i).getSheetName(),debugOn);

                echoAsCSV(wb.getSheetAt(i),osw,debugOn);
            }
            osw.close();
            print("file closed",debugOn);
        } catch (InvalidFormatException | IOException ex) {
            Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                assert inp != null;
                inp.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }
    private static void echoAsCSV(Sheet sheet, OutputStreamWriter osw, boolean debugOn)throws IOException {
        print("sheet.getSheetName()"+sheet.getSheetName(),debugOn);
        Row row;
        print("sheet.getLastRowNum()="+sheet.getLastRowNum(),debugOn);
        for (int i = 0; i <=sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            StringBuilder line= new StringBuilder();
            if(row!=null){
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    if(row.getCell(j) != null && !row.getCell(j).toString().equals("")){
                        line.append(row.getCell(j)).append(",");


                    }

                }
            }
            if( line.length()>0) {
                print("line=" + line, debugOn);
                osw.write(line.substring(0, line.length() - 1) + "\n");
            }
        }


    }



    private static String getFileName(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(0,fileName.lastIndexOf(".")+1);
        else return "";
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    private static void print(String str, boolean debugOn){
        if(debugOn){
            System.out.println(str);

        }

    }
}