package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exceptions.DuplicateAdditionException;

public class CheeseFactory implements CheeseFactoryADT {

  private HashMap<String, Farm> farms;

  public CheeseFactory() {
    farms = new HashMap<>();
  }

  // maya
  @Override
  public List<String> getAllFarmNames() {
    // idk how else to get rid of this warning.. lmk if u have any ideas
    @SuppressWarnings("unchecked")
    // assuming that the hashmap keys are the farm names
    List<String> farmNames = (List<String>) farms.keySet();
    return farmNames;
  }

  // maya
  @Override
  public double[][] getFarmReport(String farmName, int year) {
    double[][] farmReport = new double[2][12];
    
    List<String> farmIDs = getAllFarmNames();
    double janTotalMilkWeightFactory = 0;
    for (int i = 0; i < farmIDs.size(); i++) {
//      totalMilkWeightOfFactory += farms.get(farmIDs.get(i)).getMilkWeight(year);
    }
    return null;
  }

  // zach
  @Override
  public HashMap<String, double[]> getAnnualReport(int year) {
    HashMap<String, double[]> annualReport = new HashMap<>();
    List<String> farmIDs = getAllFarmNames();
    int totalMilkWeightOfFactory = 0;
    
    //get total milk weight of factory to use for percentage
    for (int i = 0; i < farmIDs.size(); i++) {
      totalMilkWeightOfFactory += farms.get(farmIDs.get(i)).getMilkWeight(year);
    }
    
    //add to hash map for each farmID
    for (int i = 0; i < farmIDs.size(); i++) {
      String key = farmIDs.get(i);
      double[] value = new double[2];
      value[0] = (double) farms.get(farmIDs.get(i)).getMilkWeight(year);
      value[1] = (double) (value[0]/totalMilkWeightOfFactory) * 100;
      
      annualReport.put(key, value);
    }
    
    return annualReport;
  }
  
  //zach
  @Override
  public HashMap<String, double[]> getMonthlyReport(int year, int month){
    return null;
  }

  // zach
  @Override
  public HashMap<String, double[]> getDateRangeReport(Date start, Date end) {
    HashMap<String, double[]> dateRangeReport = new HashMap<>();
    
    List<String> farmIDs = getAllFarmNames();
    int totalMilkWeightOfFactory = 0;
    
    for (String key : farmIDs) {
      totalMilkWeightOfFactory += farms.get(key).getMilkWeight(start, end);
    }
    
    for (String key : farmIDs) {
      double[] value = new double[2];
      value[0] = (double) farms.get(key).getMilkWeight(start, end);
      value[1] = (double) (value[0]/totalMilkWeightOfFactory) * 100;
      
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
	  while ((line = reader.readLine()) != null)  {
		  System.out.println(line);
		  split = line.split(",");
		  
		  dateSplit = split[0].split("-");
		  Date date = new Date(
				  Integer.parseInt(dateSplit[2]),
				  Integer.parseInt(dateSplit[1]),
				  Integer.parseInt(dateSplit[0]));
		  
		  farmId = split[1];
		  
		  weight = Integer.parseInt(split[2]);
		  
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
	  FileWriter writer = new FileWriter(fileName);
	  
	  writer.append("date,farm_id,weight\n");
	  
  }



}
