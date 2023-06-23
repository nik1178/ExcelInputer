import java.io.*;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
    File podatkiFile = new File("podatki.xlsx");
    XSSFSheet sheet;
    public static void main(String args[]) throws IOException {
        new Main();
    }

    public Main() throws IOException{
        // Checking if file exists
        if (this.podatkiFile.exists()) {
            System.out.printf("File %s already exist%n", this.podatkiFile.getName());
        } else {
            PodatkiFileMaker.makePodatkiFile(this.podatkiFile);
        }

        // Get input stream
        FileInputStream fis = new FileInputStream(this.podatkiFile);

        // Opening as Excel file
        XSSFWorkbook wb = new XSSFWorkbook(fis); // creating Workbook instance that refers to .xlsx file
        this.sheet = wb.getSheetAt(0); // creating a Sheet object to retrieve object

        // Make JFrame
        new MainFrame(this.sheet);
    }
} 