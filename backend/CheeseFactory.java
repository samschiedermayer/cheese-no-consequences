package backend;

import java.util.HashMap;
import java.util.List;

public class CheeseFactory implements CheeseFactoryADT {
	
	private HashMap<String, FarmADT> farms;
	
	public CheeseFactory() {
		farms = new HashMap<>();
	}

	//maya
	@Override
	public List<String> getAllFarmNames() {
		// TODO Auto-generated method stub
		return null;
	}

	//maya
	@Override
	public double[][] getFarmReport(String farmName, int year) {
		// TODO Auto-generated method stub
		return null;
	}

	//zach
	@Override
	public HashMap<String, double[]> getAnnualReport(int year) {
		// TODO Auto-generated method stub
		return null;
	}

	//zach
	@Override
	public HashMap<String, double[]> getDateRangeReport(Date start, Date end) {
		// TODO Auto-generated method stub
		return null;
	}

	//sam
	@Override
	public void importFarmData(String fileName) {
		// TODO Auto-generated method stub
		
	}

	//sam
	@Override
	public void exportFarmData(String fileName) {
		// TODO Auto-generated method stub
		
	}
	
	

}
