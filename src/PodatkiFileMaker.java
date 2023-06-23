import java.io.*;
import java.util.Scanner;

public class PodatkiFileMaker {
    PodatkiFileMaker() {

    }

    public static void makePodatkiFile(File podatkiFile) throws IOException {
        System.out.printf("Started making %s file...%n", podatkiFile.getName());
        // First read init info form init.xlsx
        // Then make podatki.xlsx file
        // Then write init info to podatki.xlsx

        // Create podatki file
        if (podatkiFile.createNewFile()) {
            System.out.printf("Succesfully created %s file.%n", podatkiFile.getName());
        } else {
            System.out.printf("Something went wrong, %s file already exists.%n", podatkiFile.getName());
        }

        // Open init file
        File initFile = new File("init.xlsx");
        if (initFile.exists()) {
            System.out.printf("Succesfully found %s file.%n", initFile.getName());
        } else {
            System.out.printf("%s file does not exist. Please create an empty .xlsx file and name it init.%n",
                    initFile.getName());
        }

        try {
            // Read all lines then print them into podatki.xlsx
            InputStream inputStream = new FileInputStream(initFile);
            OutputStream outputStream = new FileOutputStream(podatkiFile);
            int byteRead = -1;
 
            while ((byteRead = inputStream.read()) != -1) {
                outputStream.write(byteRead);
            }
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred when copying from init.xlsx into podatki.xlsx.");
            e.printStackTrace();
        }

        System.out.printf("Finished making %s file.%n", podatkiFile.getName());
    }
}
