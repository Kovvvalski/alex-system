package by.kovalski.alexsystem.dto;

public enum Periodicity {

  ONCE_A_WEEK {
    @Override
    public int weeksNumber() {
      return 1;
    }
  }, ONCE_EVERY_2_WEEKS {
    @Override
    public int weeksNumber() {
      return 2;
    }
  }, ONCE_EVERY_3_WEEKS {
    @Override
    public int weeksNumber() {
      return 3;
    }
  }, ONCE_A_MONTH {
    @Override
    public int weeksNumber() {
      return 4;
    }
  };

  public abstract int weeksNumber();
}
