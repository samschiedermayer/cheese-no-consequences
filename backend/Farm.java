package backend;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import exceptions.DuplicateAdditionException;

public class Farm extends FarmADT {

	private HashMap<Date,Integer> milkWeights;
	
	public Farm(String farmID) {
		this.farmID = farmID;
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

	@Override
	public int getMilkWeight(Date start, Date end) {
		
		@SuppressWarnings("unchecked")
		List<Date> dates = (List<Date>) milkWeights.keySet();
		Collections.sort(dates);
		
		int total = 0;
		for (Date date : dates) {
			if (date.compareTo(end) > 0) {
				break;
			}
			if(date.compareTo(start) > 0) {
				total += milkWeights.get(date);
			}
		}
		
		return total;
	}
	

}
