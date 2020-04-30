package backend;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;

import exceptions.DuplicateAdditionException;

public interface CheeseFactoryADT {

  /**
   * Will return all of the farm names in a list
   * 
   * @return list of all farm names
   */
  Set<String> getAllFarmNames();

  /**
   * Will return list of all milk weights and percentage milk weights for a given farm for a given
   * year
   * 
   * @param farmName - the name of the farm that this report is being requested for
   * @param year     - the year that this report is being requested for
   * @return - an 12 long array of arrays of integers with [month][0] storing total weight, and
   *         [month][1] storing percent total milk
   */
  double[][] getFarmReport(String farmName, int year) throws IllegalArgumentException;


  /**
   * Will return a map of the milk weight totals and percentages per farm for a given year
   * 
   * @param year - the year that the report is to be requested for
   * @return - a HashMap with keys as a farm id String and values as a 2 length array that stores
   *         [0] -> total milk weight, [1] -> percent milk weight
   */
  HashMap<String, double[]> getAnnualReport(int year);

  /**
   * Will return a map of the milk weight totals and percentages per farm for a given month within a
   * year
   * 
   * @param year  - the year that the report is to be requested for
   * @param month - the month that the report is to be requested for
   * @return - a HashMap with keys as a farm id String and values as a 2 length array that stores
   *         [0] -> total milk weight, [1] -> percent milk weight
   */
  HashMap<String, double[]> getMonthlyReport(int year, int month);

  /**
   * Will return a map of the milk weight totals and percentages per farm within a date range
   * 
   * @param start - the date at which the report should start (inclusive)
   * @param end   - the date at which the report should end (inclusive)
   * @return - a HashMap with keys as a farm id String and values as a 2 length array that stores
   *         [0] -> total milk weight, [1] -> percent milk weight
   */
  HashMap<String, double[]> getDateRangeReport(LocalDate start, LocalDate end);

  /**
   * Add a new data entry for milk on a day
   * 
   * @param farmId - the name of the farm for this data point
   * @param milkWeight - the amount of milk for this farm on this date
   * @param date - the day on which this data point takes place
   * @throws DuplicateAdditionException - if there is already an entry on the specified date
   */
  void addDataPoint(String farmId, int milkWeight, LocalDate date, boolean keepInHistory) throws DuplicateAdditionException;
  
  /**
   * Add a new data entry for milk on a day, regardless of whether there is already an entry
   * 
   * @param farmId - the name of the farm for this data point
   * @param milkWeight - the amount of milk for this farm on this date
   * @param date - the day on which this data point takes place
   */
  void forceAddDataPoint(String farmId, int milkWeight, LocalDate date, boolean keepInHistory);
  
  /**
   * Will load in data from a specified CSV file
   * 
   * @param fileName - the name of the file to be imported
   * @throws IOException - if file cannot be reached
   */
  void importFarmData(String fileName) throws IOException;

  /**
   * Will export all farm data to a CSV file
   * 
   * @param fileName - the name of the file the data should be exported to
   * @throws IOException  - if there is an error writing to the file
   */
  void exportFarmData(String fileName) throws IOException;
}
