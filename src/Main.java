import java.io.*;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
    public final File podatkiFile = new File("podatki.xlsx");
    public XSSFSheet sheet;
    public XSSFWorkbook workbook;
    MainFrame frame;
    public boolean emptyFile = false;
    public static void main(String args[]) throws IOException {
        new Main();
    }

    public Main() throws IOException{
        // Checking if file exists
        if (this.podatkiFile.exists()) {
            System.out.printf("File %s already exist%n", this.podatkiFile.getName());
        } else {
            PodatkiFileMaker.makePodatkiFile(this.podatkiFile);
            this.emptyFile = true;
        }

        // Get input stream
        FileInputStream fis = new FileInputStream(this.podatkiFile);

        // Opening as Excel file
        this.workbook = new XSSFWorkbook(fis); // creating Workbook instance that refers to .xlsx file
        this.sheet = workbook.getSheetAt(0); // creating a Sheet object to retrieve object

        // Make JFrame
        this.frame = new MainFrame(this);
    }
} 