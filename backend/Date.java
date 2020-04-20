package backend;

public class Date implements Comparable<Date>{
	public int day, month, year;
	private int date;
	
	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
		
		this.date = day + (month << 5) + (year << 9);
	}
	
	public String getDayVerbose() {
		return month + "/" + day + "/" + year;
	}

	@Override
	public int compareTo(Date other) {
		if (this.date == other.date) return 0;
		else if (this.date > other.date) return 1;
		else return -1;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Date) {
			if (this.date == ((Date)o).date)return true;
		}	
		return false;
	}
}
