package domain;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

class ExcelReading {

    static void convert(File inputFile,String target, boolean debugOn){
        InputStream inp = null;
        try {
            print("inputFile.getName()"+inputFile.getName(),debugOn);
            inp = new FileInputStream(inputFile);
            print("file read",debugOn);
            Workbook wb = WorkbookFactory.create(new File(inputFile.getPath()));
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
        Row r;
        print("sheet.getLastRowNum()="+sheet.getLastRowNum(),debugOn);
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        for (int i = 0; i <=sheet.getLastRowNum(); i++) {
            r = sheet.getRow(i);
            StringBuilder line= new StringBuilder();
            if(r!=null){
                for (int j = 0; j < r.getLastCellNum(); j++) {
                    if(r.getCell(j) != null && !r.getCell(j).toString().equals("")){

                        if(r.getCell(j).getCellType() == Cell.CELL_TYPE_FORMULA) {
                            //System.out.println(row.getCell(j).getNumericCellValue() + " = " + row.getCell(j).getDateCellValue() + " = " + df.format(row.getCell(j).getDateCellValue()));
                            if(DateUtil.isCellDateFormatted(r.getCell(j)))
                                line.append(df.format(r.getCell(j).getDateCellValue())).append(",");
                            else if(r.getCell(j).getCachedFormulaResultType()==Cell.CELL_TYPE_NUMERIC){
                                line.append(r.getCell(j).getNumericCellValue()).append(",");
                            } else if(r.getCell(j).getCachedFormulaResultType()==Cell.CELL_TYPE_STRING){
                                line.append(r.getCell(j).getRichStringCellValue()).append(",");
                            }
                        }
                        else{
                            line.append(r.getCell(j)).append(",");
                        }



                    }

                }
            }
            if( line.length()>0) {
                print("line=" + line, debugOn);
                osw.write(line.substring(0, line.length() - 1) + "\n");
            }
        }


    }


    private static void print(String str, boolean debugOn){
        if(debugOn){
            System.out.println(str);

        }

    }
}