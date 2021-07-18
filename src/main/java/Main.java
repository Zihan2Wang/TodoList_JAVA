import commandlineparser.InvalidInputException;
import inputoutput.CSVProcessor;
import inputoutput.FileUtils;
import inputoutput.IncompatibleCSVFormatException;
import java.io.IOException;
import modelcontroller.Todo;
import modelcontroller.TodoFile;
import modelcontroller.TodoList;
import modelcontroller.TodoOptions;

public class Main {

  public static void main(String[] args) {
    TodoOptions instructions = new TodoOptions(args);
    String feedback = instructions.getInputFeedback();
    if (!feedback.isEmpty()) {
      System.out.println("Error processing input!");
      System.out.println(feedback);
    } else {
      System.out.println("Input received");
      String path = instructions.getCSVPath();
      if (!FileUtils.verifyFile(path)) {
        System.out.println("CSV does not exist");
      } else {
        try {
          CSVProcessor data = new CSVProcessor(FileUtils.read(path));
          TodoList todos = TodoList.getInstance();
          todos.populateTodos(data.getData());
          boolean rewriteFile = false;
          if (instructions.addTodoRequested()) {
            Todo todo = instructions.createTodoFromOptions();
            todos.addTodos(todo);
            rewriteFile = true;
          }
          if (instructions.completeTodoRequested()) {
            int id = instructions.getTodoToComplete();
            if (todos.completeTodo(id)) {
              rewriteFile = true;
            } else {
              System.out.println("Unable to complete todo " + id);
            }
          }
          if (instructions.showRequested()) {
            System.out.println(todos
                .display(instructions.showIncompleteOnly(), instructions.showCategory(),
                    instructions.getRequestedSort()));
          }
          if (rewriteFile) {
            FileUtils.write(path, TodoFile.convertTodoListToFileString(todos.getTodos()));
            System.out.println("Todo file updated");
          }
        } catch (InvalidInputException | IncompatibleCSVFormatException | IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
