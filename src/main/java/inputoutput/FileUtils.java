package inputoutput;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

  public static boolean verifyFile(String file) {
    return new File(file).exists();
  }

  public static List<String> read(String filePath) {
    List<String> lines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    } catch (FileNotFoundException e) {
      System.out.println("Unable to read" + filePath + "! Please Check the path and try again");
    } catch (IOException e) {
      System.out.println("Unable to read" + filePath + "! Please Check the path and try again");
    }
    return lines;
  }

  public static boolean write(String fileName, String contents) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      writer.write(contents);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

}
