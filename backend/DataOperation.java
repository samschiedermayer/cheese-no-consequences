package backend;

import java.time.LocalDate;

public class DataOperation {
	
	public enum Type {
		INSERT,MODIFY,REMOVE
	}
	
	public Type type;
	public LocalDate date;
	public String farm;
	public int milk, oldMilk;
	
	public DataOperation(Type type, LocalDate date, String farm, int milk) {
		this.type = type;
		this.date = date;
		this.farm = farm;
		this.milk = milk;
	}
	
	public String toString() {
		String result = "";
		switch (type) {
			case INSERT:
				result = "Inserted "+milk+"lb on "+date+" for farm: "+farm;
				break;
			case MODIFY:
				result = "Modified "+oldMilk+"lb to "+milk+"lb on "+date+" for farm: "+farm;
				break;
			case REMOVE:
				result = "Removed "+milk+"lb on "+date+" for farm: "+farm;
				break;
		}
		return result;
	}

}
