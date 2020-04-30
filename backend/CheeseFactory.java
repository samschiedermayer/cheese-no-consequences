package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import exceptions.DuplicateAdditionException;

public class CheeseFactory implements CheeseFactoryADT {

	private HashMap<String, Farm> farms;

	public CheeseFactory() {
		farms = new HashMap<>();
	}

	// maya
	@Override
	public Set<String> getAllFarmNames() {
		@SuppressWarnings("unchecked")
		Set<String> farmNames = farms.keySet();
		return farmNames;
	}

	// maya
	@Override
	public double[][] getFarmReport(String farmName, int year) throws IllegalArgumentException {
		double[][] farmReport = new double[12][5];
		// get total milk weight per month
		
		Farm farm = farms.get(farmName);
		
		if (farm == null)
			throw new IllegalArgumentException();
		
		double totalMilkWeight = farm.getMilkWeight(year);
		int monthCounter = 1;
		// assumes month indexing starts at 1 (for January)
		while (monthCounter <= 12) {
			// get total milk weight from farm per month
			double farmMilkWeight = farm.getMilkWeight(year, monthCounter);
			int[] stats = farm.getMilkWeightStatistics(year, monthCounter);
			
			farmReport[monthCounter -1][2] = stats[0];
			farmReport[monthCounter -1][3] = stats[1];
			farmReport[monthCounter -1][4] = stats[2];

			farmReport[monthCounter - 1][0] = farmMilkWeight;
			double percentMilkWeight = (farmMilkWeight / totalMilkWeight) * 100;
			farmReport[monthCounter - 1][1] = percentMilkWeight;
			monthCounter++;
		}
		return farmReport;
	}

	// zach
	@Override
	public HashMap<String, double[]> getAnnualReport(int year) {
		HashMap<String, double[]> annualReport = new HashMap<>();
		Set<String> farmIDs = getAllFarmNames();
		int totalMilkWeightOfFactory = 0;

		// get total milk weight of factory to use for percentage
		for (String key : farmIDs) {
			totalMilkWeightOfFactory += farms.get(key).getMilkWeight(year);
		}

		// add to hash map for each farmID
		for (String key : farmIDs) {
			double[] value = new double[5];
			value[0] = (double) farms.get(key).getMilkWeight(year);
			value[1] = (double) (value[0] / totalMilkWeightOfFactory) * 100;
			
			int[] stats = farms.get(key).getMilkWeightStatistics(year);
			value[2] = stats[0];
			value[3] = stats[1];
			value[4] = stats[2];

			annualReport.put(key, value);
		}

		return annualReport;
	}

	// zach
	@Override
	public HashMap<String, double[]> getMonthlyReport(int year, int month) {
	  HashMap<String, double[]> monthlyReport = new HashMap<>();
      Set<String> farmIDs = getAllFarmNames();
      int totalMilkWeightOfFactory = 0;
      
	// get total milk weight of factory to use for percentage
      for (String key : farmIDs) {
          totalMilkWeightOfFactory += farms.get(key).getMilkWeight(year, month);
      }

      // add to hash map for each farmID
      for (String key : farmIDs) {
          double[] value = new double[5];
          value[0] = (double) farms.get(key).getMilkWeight(year);
          value[1] = (double) (value[0] / totalMilkWeightOfFactory) * 100;
          
          int[] stats = farms.get(key).getMilkWeightStatistics(year);
          value[2] = stats[0];
          value[3] = stats[1];
		  value[4] = stats[2];

          monthlyReport.put(key, value);
      }
		return monthlyReport;
	}

	// zach
	@Override
	public HashMap<String, double[]> getDateRangeReport(LocalDate start, LocalDate end) {
		HashMap<String, double[]> dateRangeReport = new HashMap<>();

		Set<String> farmIDs = getAllFarmNames();
		int totalMilkWeightOfFactory = 0;

		for (String key : farmIDs) {
			totalMilkWeightOfFactory += farms.get(key).getMilkWeight(start, end);
		}

		for (String key : farmIDs) {
			double[] value = new double[5];
			value[0] = (double) farms.get(key).getMilkWeight(start, end);
			value[1] = (double) (value[0] / totalMilkWeightOfFactory) * 100;
			
			int[] stats = farms.get(key).getMilkWeightStatistics(start, end);
			value[2] = stats[0];
			value[3] = stats[1];
			value[4] = stats[2];

			dateRangeReport.put(key, value);
		}

		return dateRangeReport;
	}

	// sam
	@Override
	public void importFarmData(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));

		reader.readLine();

		int weight;
		Farm farm;
		String line = null, farmId;
		String[] split, dateSplit;
		int year, month, day;
		while ((line = reader.readLine()) != null) {
			split = line.split(",");
			if (split.length != 3)
				continue;

			dateSplit = split[0].split("-");
			if (dateSplit.length != 3)
				continue;
			
			try {
				year = Integer.parseInt(dateSplit[0]);
				month = Integer.parseInt(dateSplit[1]);
				day = Integer.parseInt(dateSplit[2]);
				
				weight = Integer.parseInt(split[2]);
			} catch (NumberFormatException e) {
				continue;
			}
			
			LocalDate date = LocalDate.of(year, month, day);

			farmId = split[1];
			

			if (!farms.containsKey(farmId)) {
				farm = new Farm(farmId);
				farms.put(farmId, farm);
			} else {
				farm = farms.get(farmId);
			}
			
			try {
				farm.addMilkWeightForDay(date, weight);
			} catch (DuplicateAdditionException e) {
				farm.modifyMilkWeightForDay(date, weight);
			}
		}

		reader.close();
	}

	// sam
	@Override
	public void exportFarmData(String fileName) throws IOException {
		
		try (FileWriter writer = new FileWriter(fileName)){
			writer.append("date,farm_id,weight\n");
			farms.forEach((id,farm) -> {
				farm.getMilkWeightsHashMap().forEach((date,weight) -> {
					try {
						writer.append(date + "," + id + "," + weight + "\n");
					} catch (IOException e) {
						throw new UncheckedIOException(e.getMessage(),e);
					}
				});
			});
		} catch (UncheckedIOException e) {
			throw new IOException(e.getMessage());
		}
		
	}

	@Override
	public void addDataPoint(String farmId, int milkWeight, LocalDate date) throws DuplicateAdditionException {
		
		Farm farm = null;
		if (farms.containsKey(farmId)) {
			farm = farms.get(farmId);
		} else {
			farm = new Farm(farmId);
			farms.put(farmId, farm);
		}
		
		farm.addMilkWeightForDay(date, milkWeight);
	}

	@Override
	public void forceAddDataPoint(String farmId, int milkWeight, LocalDate date) {
		
		try {
			addDataPoint(farmId, milkWeight, date);
		} catch (DuplicateAdditionException e) {
			Farm farm = farms.get(farmId);
			farm.modifyMilkWeightForDay(date, milkWeight);
		}
		
	}

}
