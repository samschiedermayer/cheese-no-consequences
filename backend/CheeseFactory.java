package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    List<String> farmIDs = getAllFarmNames();
    double totalMilkWeightOfFactory = 0;
    for (int i = 0; i < farmIDs.size(); i++) {
      totalMilkWeightOfFactory += farms.get(farmIDs.get(i)).getMilkWeight(year);
    }
    
    
    return null;
  }

  // zach
  @Override
  public HashMap<String, double[]> getDateRangeReport(Date start, Date end) {
    // TODO Auto-generated method stub
    return null;
  }

  // sam
  @Override
  public void importFarmData(String fileName) {
    // TODO Auto-generated method stub

  }

  // sam
  @Override
  public void exportFarmData(String fileName) {
    // TODO Auto-generated method stub

  }



}
