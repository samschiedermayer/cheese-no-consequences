package backend;

import java.util.HashMap;

import exceptions.DuplicateAdditionException;

public class Farm extends FarmADT {

	private HashMap<Date,Integer> milkWeights;
	
	public Farm() {
		milkWeights = new HashMap<>();
	}
	
	@Override
	public int getMilkWeight(int year) {
		final int[] total = new int[1];
		
		milkWeights.forEach((date, weight) -> {
			if (date.year == year)
				total[0] += weight;
		});
		
		return total[0];
	}

	@Override
	public int getMilkWeight(int year, int month) {
		final int[] total = new int[1];
		
		milkWeights.forEach((date, weight) -> {
			if (date.year == year && date.month == month)
				total[0] += weight;
		});
		
		return total[0];
	}

	@Override
	public void addMilkWeightForDay(Date date, int weight) throws DuplicateAdditionException {
		if (milkWeights.containsKey(date))
			throw new DuplicateAdditionException();
		
		milkWeights.put(date, weight);
	}

	@Override
	public void modifyMilkWeightForDay(Date date, int weight) {
		milkWeights.put(date, weight);
	}

	@Override
	public void removeMilkWeightForDay(Date date, int weight) {
		milkWeights.remove(date);
	}
	

}
