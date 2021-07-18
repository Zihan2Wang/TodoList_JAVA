package commandlineparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Option {

  private boolean isRequired;
  private boolean requiresArg;
  private String argValue;
  private List<String> requiredPairs;
  private List<String> optionalPairs;
  private List<String> cannotBeCombinedWith;

  private Option(Builder builder) {
    this.isRequired = builder.isRequired;
    this.requiresArg = builder.requiresArg;
    this.argValue = builder.argValue;
    this.requiredPairs = builder.requiredPairs;
    this.optionalPairs = builder.optionalPairs;
    this.cannotBeCombinedWith = builder.cannotBeCombinedWith;
  }

  public boolean isRequired() {
    return this.isRequired;
  }

  public boolean requiresArg() {
    return this.requiresArg;
  }

  public String getArgValue() {
    return this.argValue;
  }

  public void setArgValue(String arg){
    this.argValue = arg;
  }

  public List<String> getRequiredPairs() {
    return this.requiredPairs;
  }

  public List<String> getOptionalPairs() {
    return this.optionalPairs;
  }

  public List<String> getCannotBeCombinedWith() {
    return this.cannotBeCombinedWith;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Option option = (Option) o;
    return isRequired() == option.isRequired() && requiresArg() == option.requiresArg()
        && getArgValue().equals(option.getArgValue()) && getRequiredPairs()
        .equals(option.getRequiredPairs()) && getOptionalPairs().equals(option.getOptionalPairs())
        && getCannotBeCombinedWith().equals(option.getCannotBeCombinedWith());
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(isRequired(), requiresArg(), getArgValue(), getRequiredPairs(), getOptionalPairs(),
            getCannotBeCombinedWith());
  }

  @Override
  public String toString() {
    return "Option{" +
        "isRequired=" + this.isRequired +
        ", requiresArg=" + this.requiresArg +
        ", argValue='" + this.argValue + '\'' +
        ", requiredPairs=" + this.requiredPairs +
        ", optionalPairs=" + this.optionalPairs +
        ", cannotBeCombinedWith=" + this.cannotBeCombinedWith +
        '}';
  }

  public static class Builder {

    private boolean isRequired;
    private boolean requiresArg;
    private String argValue;
    private List<String> requiredPairs;
    private List<String> optionalPairs;
    private List<String> cannotBeCombinedWith;

    public Builder() {
      this.isRequired = false;
      this.requiresArg = false;
      this.requiredPairs = new ArrayList<>();
      this.optionalPairs = new ArrayList<>();
      this.cannotBeCombinedWith = new ArrayList<>();
    }

    public Builder isRequired() {
      this.isRequired = true;
      return this;
    }

    public Builder requiresArg() {
      this.requiresArg = true;
      return this;
    }

    public Builder addArgValue(String argValue) {
      this.argValue = argValue;
      return this;
    }

    public Builder addRequiredOptionPair(String key) {
      this.requiredPairs.add(key);
      return this;
    }

    public Builder addOptionalOptionPair(String key) {
      this.optionalPairs.add(key);
      return this;
    }

    public Builder addInvalidPair(String key) {
      this.cannotBeCombinedWith.add(key);
      return this;
    }

    public Option build() {
      return new Option(this);
    }
  }
}


