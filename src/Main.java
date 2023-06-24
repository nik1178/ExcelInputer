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
        this.readExcelData();
    }

    public void readExcelData() {
        Iterator<Row> rowIterator = this.sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell firstCell = row.getCell(0);

            if (firstCell == null) {
                System.out.println("Cell 0 is null.");
                continue;
            }

            switch (firstCell.getCellType()) {  
                case Cell.CELL_TYPE_STRING:    //field that represents string cell type  
                    this.frame.comboBox.addValue(firstCell.getStringCellValue(), row);  
                    break;  
                case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type  
                    this.frame.comboBox.addValue(String.valueOf(firstCell.getNumericCellValue()), row); 
                    break;  
                default:  
                    System.out.println("Invalid input in cell 0.");
            }  

            row.getCell(0).setCellValue("test");
        }
    }
} 