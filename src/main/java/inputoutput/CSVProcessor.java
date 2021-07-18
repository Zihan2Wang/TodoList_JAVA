package inputoutput;

import commandlineparser.InvalidInputException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVProcessor {

  private static final String COLUMN_SPLITTER = "\",\"";
  public static final String QUOTE = "\"";
  public static final int HEADER = 0;
  public static final String UNKNOWN = "?";
  List<Map<String, String>> data;

  public CSVProcessor(List<String> lines)
      throws InvalidInputException, IncompatibleCSVFormatException {
    this.data = this.processInput(lines);
  }

  private List<Map<String, String>> processInput(List<String> lines)
      throws InvalidInputException, IncompatibleCSVFormatException {
    List<Map<String, String>> rows = new ArrayList<>();
    String[] headerRow = this.preprocess(lines.get(HEADER));
    lines.remove(0);
    for (String line : lines) {
      rows.add(this.processRow(headerRow, this.preprocess(line)));
    }
    return rows;
  }

  private String[] preprocess(String line) throws IncompatibleCSVFormatException {
    int firstQuote = line.indexOf(QUOTE);
    int lastQuote = line.indexOf(QUOTE);
    if (firstQuote != 0 || lastQuote != line.length() - 1) {
      throw new IncompatibleCSVFormatException();
    }
    line = line.substring(firstQuote + 1, lastQuote);
    return line.split(COLUMN_SPLITTER);
  }

  private Map<String, String> processRow(String[] columns, String[] data)
      throws InvalidInputException {
    Map<String, String> entry = new HashMap<>();
    if (columns.length != data.length) {
      throw new InvalidInputException("CSV row length does not match the number of headers");
    }
    for (int c = 0; c < columns.length; c++) {
      entry.put(columns[c], data[c].equals(UNKNOWN) ? null : data[c]);
    }
    return entry;
  }

  public List<Map<String, String>> getData() {
    return this.data;
  }


}
