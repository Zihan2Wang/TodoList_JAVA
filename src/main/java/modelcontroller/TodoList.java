package modelcontroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TodoList {
  private static TodoList instance = null;
  private List<Todo> todos;

  private TodoList(){
    this.todos = new ArrayList<>();
  }

  public static TodoList getInstance() {
    if (instance == null) {
      return new TodoList();
    }
    return instance;
  }

  public void populateTodos(List<Map<String, String>> data){
    for (Map<String, String> row: data) {
      Todo todo = TodoFile.createTodoFromFileRow(row);
      this.todos.add(todo);
    }
  }

  public List<Todo> getTodos() {
    return this.todos;
  }

  public void addTodos(Todo todo) {
    this.todos.add(todo);
  }

  private String showTodos(List<Todo> displayList) {
    StringBuilder sb = new StringBuilder();
    for (Todo todo: displayList){
      sb.append(todo.toString()).append(System.lineSeparator());
    }
    return sb.toString();
  }

  public boolean completeTodo(int id) {
    Todo todo = findTodoByID(id);
    if (todo == null) {
      return false;
    }
    todo.setCompleted(true);
    return true;
  }

  private Todo findTodoByID(int id){
    if (id < this.todos.size() && this.todos.get(id - 1).getId() == id){
      return this.todos.get(id - 1);
    }
    for (int i = 0; i < this.todos.size(); i++){
      if (this.todos.get(i).getId() == id) {
        return this.todos.get(i);
      }
    }
    return null;
  }

  public String display(boolean incompleteOnly, String category, Comparator<Todo> sortBy) {
    List<Todo> displayList = this.getTodosForDisplay(incompleteOnly, category);
    if (sortBy != null) {
      Collections.sort(displayList, sortBy);
    }
    return showTodos(displayList);
  }

  private List<Todo> getTodosForDisplay(boolean incompleteOnly, String category) {
    List<Todo> displayList = new ArrayList<>();
    for (Todo todo: this.todos) {
      boolean addTodo = true;
      if (incompleteOnly && todo.isCompleted()) {
        addTodo = false;
      }
      if (category != null && !category.equals(todo.getCategory())){
        addTodo = false;
      }
      if (addTodo) {
        displayList.add(todo);
      }
    }
    return displayList;
  }

}
