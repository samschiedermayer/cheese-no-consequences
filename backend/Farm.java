package backend;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import exceptions.DuplicateAdditionException;

public class Farm extends FarmADT {

	private HashMap<LocalDate,Integer> milkWeights;
	
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
	public void removeMilkWeightForDay(LocalDate date, int weight) {
		milkWeights.remove(date);
	}

	@Override
	public int getMilkWeight(LocalDate start, LocalDate end) {
		
		@SuppressWarnings("unchecked")
		List<LocalDate> dates = (List<LocalDate>) milkWeights.keySet();
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

	@Override
	public int getMilkWeight(LocalDate date) {
		return milkWeights.get(date);
	}
	

}
