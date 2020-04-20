package backend;

public class Date {
	public int day, month, year;
	
	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public String getDayVerbose() {
		return month + "/" + day + "/" + year;
	}
}
