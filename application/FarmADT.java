/**
 *  FarmADT.java 
 *	
 *	Authors:
 *	Sam Schiedermayer, LEC001, sschiedermay@wisc.edu
 *	Mya Schmitz, LEC001, mschmitz9@wisc.edu
 *	Mike Sexton, LEC001, msexton4@wisc.edu
 *	Maya Shoval, LEC001, shoval@wisc.edu
 *	Zachary Stange, LEC002, zstange@wisc.edu
 *	Date: 04/30/2019
 *	
 *	Course:		CS400
 *	Semester:	Spring 2020
 * 	
 * 	IDE: 		Eclipse for Java Developers
 *  Version:	2019-12 (4.14.0)
 * 	Build id: 	20191212-1212
 *  
 *  Due Date: 04/30/2019
 *	
 */
package application;

import java.time.LocalDate;
import java.util.HashMap;

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
  
  /**
   * Allows for adding a data point to this farm for a day
   * 
   * @param date - the day the value should be added to
   * @param weight - the value to be added
   * @throws DuplicateAdditionException - if there is already a data point for this day
   */
  public abstract void addMilkWeightForDay(LocalDate date, int weight) throws DuplicateAdditionException;
  
  /**
   * Allows for modifying a data point to this farm for a day
   * 
   * @param date - the day the value should be changed
   * @param weight - the value to be changed to
   */
  public abstract void modifyMilkWeightForDay(LocalDate date, int weight);
  
  /**
   * Allows for removing a data point to this farm for a day
   * 
   * @param date - the day the value should be added to
   * @return - the value that was removed from this day
   */
  public abstract Integer removeMilkWeightForDay(LocalDate date);
  

}
