package commandlineparser;

import java.util.HashMap;
import java.util.Map;

public class CommandLineParser {

  public static Map<String, Option> parseInput(String[] args, Map<String, Option> availableOptions){
    Map<String, Option> options = new HashMap<>();
    for (int s = 0; s < args.length; s++){
      if (availableOptions.containsKey(args[s])){
        Option o = availableOptions.get(args[s]);
        options.put(args[s], o);
        if (o.requiresArg()){
          if (s < args.length - 1){
            o.setArgValue(args[s+1]);
          }
        }
      }
    }
    return options;
  }
}
