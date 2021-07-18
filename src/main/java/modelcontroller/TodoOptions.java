package modelcontroller;

import commandlineparser.CommandLineParser;
import commandlineparser.Option;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoOptions {
  public static final String DISPLAY = "--display";
  public static final String CSV = "--csv-file";
  public static final String SHOW_INCOMPLETE =  "--show-incomplete";
  public static final String SHOW_CATEGORY = "--show-category";
  public static final String SORT_DATE = "--sort-by-date";
  public static final String SORT_PRIORITY = "--sort-by-priority";
  public static final String ADD_TODO = "--add-todo";
  public static final String TODO_TEXT = "--todo-text";
  public static final String PRIORITY = "--priority";
  public static final String CATEGORY = "--category";
  public static final String COMPLETED = "--completed";
  public static final String COMPLETE_TODO = "--complete-todo";
  public static final String DUE_DATE = "--due";

  private static Map<String, Option> AVAILABLE_OPTIONS;
  private Map<String, Option> options;

  public TodoOptions(String[] args) {
    AVAILABLE_OPTIONS = this.populateOptions();
    this.options = CommandLineParser.parseInput(args, AVAILABLE_OPTIONS);
  }

  public Map<String, Option> getOptions(){
    return options;
  }

  private Map<String, Option> populateOptions(){
    Map<String, Option> options = new HashMap<>();
    options.put(CSV, new Option.Builder().isRequired().requiresArg().build());
    options.put(SHOW_INCOMPLETE, new Option.Builder().build());
    options.put(SHOW_CATEGORY, new Option.Builder().requiresArg().build());
    options.put(SORT_DATE, new Option.Builder().addInvalidPair(SORT_PRIORITY).build());
    options.put(SORT_PRIORITY, new Option.Builder().addInvalidPair(SORT_DATE).build());
    options.put(DISPLAY, new Option.Builder().addOptionalOptionPair(SHOW_INCOMPLETE)
        .addOptionalOptionPair(SHOW_CATEGORY).addOptionalOptionPair(SORT_DATE)
        .addOptionalOptionPair(SORT_PRIORITY).build());
    options.put(TODO_TEXT, new Option.Builder().requiresArg().build());
    options.put(PRIORITY, new Option.Builder().requiresArg().build());
    options.put(CATEGORY, new Option.Builder().requiresArg().build());
    options.put(COMPLETED, new Option.Builder().build());
    options.put(DUE_DATE, new Option.Builder().requiresArg().build());
    options.put(ADD_TODO, new Option.Builder().addRequiredOptionPair(TODO_TEXT)
        .addOptionalOptionPair(COMPLETED)
        .addOptionalOptionPair(DUE_DATE)
        .addOptionalOptionPair(PRIORITY)
        .addOptionalOptionPair(CATEGORY).build());
    options.put(COMPLETE_TODO, new Option.Builder().requiresArg().build());

    return options;
  }

  public String getInputFeedback(){
    List<String> inputFeedback = new ArrayList<>();
    checkRequiredArgs(inputFeedback);
    for (String key: this.options.keySet()){
      this.checkForInvalidCombinations(key, inputFeedback);
      if (this.options.get(key).requiresArg() && this.options.get(key).getArgValue() == null){
        inputFeedback.add(key + " required an argument value");
      }
      this.checkForMissingPairedOptions(key, inputFeedback);
    }
    return String.join(System.lineSeparator(),  inputFeedback);
  }

  private void checkRequiredArgs(List<String> feedback) {
    for (String key: AVAILABLE_OPTIONS.keySet()) {
      if (AVAILABLE_OPTIONS.get(key).isRequired() && !this.optionProvided(key)){
        feedback.add(key + " is a required option");
      }
    }
  }

  private void checkForInvalidCombinations(String key, List<String> feedback) {
    for (String invalidOption: this.options.get(key).getCannotBeCombinedWith()) {
      if (this.optionProvided(invalidOption)){
        feedback.add(key + " cannot be combined with " + invalidOption);
      }
    }
  }

  private void checkForMissingPairedOptions(String key, List<String> feedback) {
    for (String requiredPair: this.options.get(key).getRequiredPairs()){
      if (!this.optionProvided(requiredPair)){
        feedback.add(key + " requires " + requiredPair);
      }
    }
  }

  private boolean optionProvided(String option) {
    return this.options.containsKey(option);
  }

  public String getCSVPath(){
    return this.options.get(CSV).getArgValue();
  }

  public boolean showRequested() {
    return this.options.containsKey(DISPLAY);
  }

  public boolean showIncompleteOnly() {
    return this.optionProvided(SHOW_INCOMPLETE);
  }

  public String showCategory() {
    if (this.optionProvided(SHOW_CATEGORY))
      return this.options.get(SHOW_CATEGORY).getArgValue();
    return null;
  }

  public Comparator<Todo> getRequestedSort() {
    if (this.optionProvided(SORT_DATE))
      return new TodoByDate();
    else if (this.optionProvided(SORT_PRIORITY))
      return new TodoByPriority();
    return null;
  }

  public boolean addTodoRequested() {
    return this.options.containsKey(ADD_TODO);
  }

  /**
   * Factory method to create new Todo from user input
   * @return The new Todo
   */
  public Todo createTodoFromOptions() {
    Todo.Builder builder = new Todo.Builder(this.options.get(TODO_TEXT).getArgValue());
    if (this.optionProvided(COMPLETED))
      builder = builder.isCompleted(true);
    if (this.optionProvided((DUE_DATE)))
      builder = builder.due(this.options.get(DUE_DATE).getArgValue());
    if (this.optionProvided((PRIORITY)))
      builder = builder.priority(this.options.get(PRIORITY).getArgValue());
    if (this.optionProvided((CATEGORY)))
      builder = builder.category(this.options.get(CATEGORY).getArgValue());
    return builder.build();
  }

  public boolean completeTodoRequested() {
    return this.options.containsKey(COMPLETE_TODO);
  }

  public int getTodoToComplete() {
    if (this.completeTodoRequested())
      return Integer.parseInt(this.options.get(COMPLETE_TODO).getArgValue());
    return -1;
  }
}

