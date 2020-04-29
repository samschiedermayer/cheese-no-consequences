package backend;

import java.time.LocalDate;
import java.util.HashMap;
import exceptions.DuplicateAdditionException;

public abstract class FarmADT {

  public String farmID;
  
  public abstract HashMap<LocalDate, Integer> getMilkWeightsHashMap();

  /**
   * Will return an integer that represents the milk weight of a farm for the given year
   * 
   * @param year - the year requested
   * @return an integer representing total milk weight of the farm
   */
  public abstract int getMilkWeight(int year);

  /**
   * Will return an integer for the total milk weight of a farm with the given month/year
   * 
   * @param year  - the year requested
   * @param month - the month requested
   * @return an integer representing the total milk weight for the given month/year of the farm
   */
  public abstract int getMilkWeight(int year, int month);
  
  /**
   * Will return an integer for the total milk weight of a farm with the given date
   * 
   * @param date - the date requested
   * @return an integer representing the total milk weight for the given date for this farm
   */
  public abstract int getMilkWeight(LocalDate date);
  
  /**
   * Will return an integer for the sum of milk weight of a farm within the given date range
   * 
   * @param start - the date at which the summation should start (inclusive)
   * @param end   - the date at which the summation should end (inclusive)
   * @return an integer representing the total milk weight from a certain time period from this farm
   */
  public abstract int getMilkWeight(LocalDate start, LocalDate end);
  
  public abstract void addMilkWeightForDay(LocalDate date, int weight) throws DuplicateAdditionException;
  
  public abstract void modifyMilkWeightForDay(LocalDate date, int weight);
  
  public abstract void removeMilkWeightForDay(LocalDate date, int weight);
  

}
