package modelcontroller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import modelcontroller.Todo.Builder;

public class TodoFile {
  private static String ID = "id";
  private static String TEXT = "text";
  private static String COMPLETE = "completed";
  private static String DUE = "due";
  private static String PRIORITY = "priority";
  private static String CATEGORY = "category";
  private static String MISSING_VALUE = "?";

  private TodoFile(){ }
  public static Todo createTodoFromFileRow(Map<String, String> row) {
    Todo.Builder todoBuilder = new Todo.Builder(row.get(TEXT))
        .setId(Integer.parseInt(row.get(ID)))
        .isCompleted(Boolean.parseBoolean(row.get(COMPLETE)))
        .category(row.get(CATEGORY))
        .due(row.get(DUE))
        .priority(row.get(PRIORITY));
    return todoBuilder.build();
  }

  public static String convertTodoListToFileString(List<Todo> todos){
    String START = "\"";
    String SEPARATOR = "\",\"";
    StringBuilder output = new StringBuilder(START + ID + SEPARATOR + TEXT + SEPARATOR + COMPLETE
    + SEPARATOR + DUE + SEPARATOR + PRIORITY + SEPARATOR + CATEGORY + START + System.lineSeparator());
    for (Todo todo: todos){
      output.append(START + todo.getId() + SEPARATOR + todo.getText() + SEPARATOR + todo.isCompleted()
      + SEPARATOR + prepareDate(todo.getDue()) + SEPARATOR + prepareOptionalString(todo.getPriority())
      + SEPARATOR + prepareOptionalString(todo.getCategory()) + START + System.lineSeparator());
    }
    return output.toString();
  }

  private static String prepareDate(LocalDate date){
    if (date == null) {
      return MISSING_VALUE;
    }
    return date.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
  }

  private static String prepareOptionalString(String field) {
    if (field == null || field == ""){
      return MISSING_VALUE;
    }
    return field;
  }

}
