package Utils;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ExcelUtility {

    private String file;
    private String format;
    Workbook book;

    public ExcelUtility(String filename){
        this.file=filename.contains("xlsx")?filename:filename.contains("xls")?filename:filename+"xlsx";
        this.format=file.substring(file.lastIndexOf(".")+1);

    }

    public ExcelUtility loadExcel() {
        this.createExcel();
        if(format.equalsIgnoreCase("xlsx"))
            book=new XSSFWorkbook();
        else book=new HSSFWorkbook();

        return this;
    }

    public ExcelUtility createSheet(String sheetname) {
        if(this.book==null)
            loadExcel();
        book.createSheet(sheetname);
        return this;
    }

    public void writeToSheet(String sheetname, TreeMap<String, List<String>> headerToData){
        if(book.getSheet(sheetname)==null)
            createSheet(sheetname);
        else{

            Sheet sh=book.getSheet(sheetname);
            int col=0;
            for(String head:headerToData.keySet())
            {
                //writing headers
                int row=0;

                if(sh.getRow(row)==null)
                    sh.createRow(row);
                if(sh.getRow(row).getCell(col)==null)
                    sh.getRow(row).createCell(col);
                sh.getRow(row).getCell(col).setCellValue(head);
                row++;

                List<String> data=headerToData.get(head);
                for(String celdata:data){
                    if(sh.getRow(row)==null)
                        sh.createRow(row);
                    if(sh.getRow(row).getCell(col)==null)
                        sh.getRow(row).createCell(col);

                    sh.getRow(row).getCell(col).setCellValue(celdata);
                    row++;
                }
                col++;
            }
        }

        try{
            FileOutputStream fos=new FileOutputStream(new File(file));
            book.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void closeSheet(){
      try{
          book.close();
      } catch (IOException e) {
          e.printStackTrace();
      }

    }



    @SneakyThrows
    private ExcelUtility createExcel()  {
        FileProvider fp=FileProvider.createInstance();
        File f=fp.getFile(file);
                if(f.exists()) {
                    f.delete();
                    return this;
                }
                else {
                    f.createNewFile();
                    return this;
                }
    }




}
