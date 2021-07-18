package modelcontroller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Todo implements Comparable<Todo>{
  private String text;
  private boolean completed;
  private LocalDate due;
  private String priority;
  private String category;
  private int id;

  private Todo(Builder builder){
    this.id = builder.id;
    this.text = builder.text;
    this.completed = builder.completed;
    this.due = builder.due;
    this.priority = builder.priority;
    this.category = builder.category;
  }

  public int getId(){
    return this.id;
  }

  public String getText(){
    return this.text;
  }

  public boolean isCompleted(){
    return this.completed;
  }

  public LocalDate getDue() {
    return this.due;
  }

  public String getPriority(){
    return this.priority;
  }

  public String getCategory(){
    return this.category;
  }

  public void setDue(LocalDate due){
    this.due = due;
  }

  public void setPriority(String priority){
    this.priority = priority;
  }

  public void setCategory(String category){
    this.category = category;
  }

  public void setCompleted(boolean completed){
    this.completed = completed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Todo todo = (Todo) o;
    return isCompleted() == todo.isCompleted() && getId() == todo.getId() && getText()
        .equals(todo.getText()) && getDue().equals(todo.getDue()) && getPriority()
        .equals(todo.getPriority()) && getCategory().equals(todo.getCategory());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getText(), isCompleted(), getDue(), getPriority(), getCategory(), getId());
  }

  @Override
  public String toString() {
    return "Todo{" +
        "id=" + id +
        ", text='" + text + '\'' +
        ", completed=" + completed +
        ", due=" + due +
        ", priority='" + priority + '\'' +
        ", category='" + category +
        '}';
  }

  @Override
  public int compareTo(Todo o){
    // By default: complete (incomplete first), due date (ascending), priority (ascending)
    if (this.completed != o.completed){
      return (!this.completed? -1: 1);
    }
    if (this.due != null && o.due != null){
      return this.due.compareTo(o.due);
    }
    if (this.due == null && o.due == null){
      return 1;
    }
    if (this.due != null && o.due == null){
      return -1;
    }
    if (!this.priority.equals(o.priority)){
      char thisPriority = this.priority.charAt(0);
      char thatPriority = o.priority.charAt(0);
      return (int) thisPriority < (int) thatPriority? -1: 1;
    }
    return 0;
  }

  public static class Builder{
    private static int id = 0;
    private String text;
    private boolean completed;
    private LocalDate due;
    private String priority;
    private String category;

    public Builder(String text){
      id++;
      this.text = text;
      this.completed = false;
      this.priority = "?";
    }

    public Builder setId(int id){
      id = id;
      return this;
    }

    public Builder isCompleted(boolean status){
      this.completed = status;
      return this;
    }

    public Builder due(LocalDate date){
      this.due = date;
      return this;
    }

    public Builder due(String date){
      if (date == null){
        return this;
      }
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
      this.due = LocalDate.parse(date, formatter);
      return this;
    }

    public Builder priority(String priority){
      this.priority = priority;
      return this;
    }

    public Builder category(String category){
      this.category = category;
      return this;
    }

    public Todo build(){
      return new Todo(this);
    }
  }


}
