/**
 *  DataOperation.java
 *	
 *	Authors:
 *	Sam Schiedermayer, LEC001, sschiedermay@wisc.edu
 *	Mya Schmitz, LEC001, mschmitz9@wisc.edu
 *	Mike Sexton, LEC001, msexton4@wisc.edu
 *	Maya Shoval, LEC001, shoval@wisc.edu
 *	Zachary Stange, LEC002, zstange@wisc.edu
 *	Date: 04/30/2019
 *	
 *	Course:		CS400
 *	Semester:	Spring 2020
 * 	
 * 	IDE: 		Eclipse for Java Developers
 *  Version:	2019-12 (4.14.0)
 * 	Build id: 	20191212-1212
 *  
 *  Due Date: 04/30/2019
 *	
 */
package application;

import java.time.LocalDate;

public class DataOperation {
	
	//represents the various types of operations that can take place
	public enum Type {
		INSERT,MODIFY,REMOVE
	}
	
	public Type type; //the type of operation that this is
	public LocalDate date; //the date that this operation was done on
	public String farm; //the farm on which this operation was done
	public int milk, oldMilk; //the milk value for this operation, the milk value before this operation
	
	/**
	 * Constructor for a DataOperation
	 */
	public DataOperation(Type type, LocalDate date, String farm, int milk) {
		this.type = type;
		this.date = date;
		this.farm = farm;
		this.milk = milk;
	}
	
	/**
	 * returns a String representation of this DataOperation
	 */
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
