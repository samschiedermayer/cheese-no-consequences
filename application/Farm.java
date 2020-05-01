/**
 *  Farm.java 
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Farm extends FarmADT {

	private HashMap<LocalDate,Integer> milkWeights; //stores all of the data for this farm
	
	public Farm(String farmID) {
		this.farmID = farmID;
		milkWeights = new HashMap<>();
	}

	@Override
	public HashMap<LocalDate, Integer> getMilkWeightsHashMap(){
	  return milkWeights;
	}
	
	@Override
	public int getMilkWeight(int year) {
		final int[] total = new int[1];
		
		milkWeights.forEach((date, weight) -> {
			if (date.getYear() == year)
				total[0] += weight;
		});
		
		return total[0];
	}

	@Override
	public int getMilkWeight(int year, int month) {
		final int[] total = new int[1];
		
		milkWeights.forEach((date, weight) -> {
			if (date.getYear() == year && date.getMonthValue() == month)
				total[0] += weight;
		});
		
		return total[0];
	}
	
	public int[] getMilkWeightStatistics(int year, int month) {
		final int[] stats = new int[3];
		stats[0] = 0; //average
		stats[1] = Integer.MAX_VALUE; //min
		stats[2] = Integer.MIN_VALUE; //max
		
		final int[] count = new int[1];
		count[0] = 0;
		milkWeights.forEach((date, weight) -> {
			if (date.getYear() == year && date.getMonthValue() == month) {
				++count[0];
				stats[0] += weight;
				if (weight < stats[1])
					stats[1] = weight;
				if (weight > stats[2])
					stats[2] = weight;
			}
		});
		if (count[0] == 0)
			return new int[] {0, 0, 0};
		
		stats[0] /= count[0];
		
		return stats;
	}
	
	public int[] getMilkWeightStatistics(int year) {
		final int[] stats = new int[3];
		stats[0] = 0; //average
		stats[1] = Integer.MAX_VALUE; //min
		stats[2] = Integer.MIN_VALUE; //max
		
		final int[] count = new int[1];
		count[0] = 0;
		milkWeights.forEach((date, weight) -> {
			if (date.getYear() == year) {
				++count[0];
				stats[0] += weight;
				if (weight < stats[1])
					stats[1] = weight;
				if (weight > stats[2])
					stats[2] = weight;
			}
		});
		if (count[0] == 0)
			return new int[] {0, 0, 0};
		
		stats[0] /= count[0];
		
		return stats;
	}

	@Override
	public void addMilkWeightForDay(LocalDate date, int weight) throws DuplicateAdditionException {
		if (milkWeights.containsKey(date) && milkWeights.get(date) != weight)
			throw new DuplicateAdditionException(milkWeights.get(date));
		
		milkWeights.put(date, weight);
	}

	@Override
	public void modifyMilkWeightForDay(LocalDate date, int weight) {
		milkWeights.put(date, weight);
	}

	@Override
	public Integer removeMilkWeightForDay(LocalDate date) {
		return milkWeights.remove(date);
	}

	@Override
	public int getMilkWeight(LocalDate start, LocalDate end) {
		
		@SuppressWarnings("unchecked")
		List<LocalDate> dates =  new ArrayList<>(milkWeights.keySet());
		Collections.sort(dates);
		
		int total = 0;
		for (LocalDate date : dates) {
			if (date.compareTo(end) > 0) {
				break;
			}
			if(date.compareTo(start) > 0) {
				total += milkWeights.get(date);
			}
		}
		
		return total;
	}
	
	/**
	 * Calculates the statistics [mean,min,max] of milkweights in a date range
	 * 
	 * @param start - the date to start calculating statistics from (inclusive)
	 * @param end - the date to stop calculating statistics from (inclusive)
	 * @return - the statistics [mean,min,max] of milkweights in a date range
	 */
	public int[] getMilkWeightStatistics(LocalDate start, LocalDate end) {
		
		@SuppressWarnings("unchecked")
		List<LocalDate> dates =  new ArrayList<>(milkWeights.keySet());
		Collections.sort(dates);
		
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		
		int total = 0, count = 0;
		int weight;
		for (LocalDate date : dates) {
			if (date.compareTo(end) > 0) {
				break;
			}
			if(date.compareTo(start) > 0) {
				++count;
				weight = milkWeights.get(date);
				total += weight;
				if (weight < min)
					min = weight;
				if (weight > max)
					max = weight;
			}
		}
		
		if (count == 0)
			return new int[] {0, 0, 0};
		int avg = total / count;
		
		return new int[] {avg,min,max};
	}

	@Override
	public int getMilkWeight(LocalDate date) {
		return milkWeights.get(date);
	}
	

}
