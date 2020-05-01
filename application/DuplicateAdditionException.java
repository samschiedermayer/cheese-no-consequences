/**
 *  DuplicateAdditionException.java 
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

/**
 * This exception will be called when there is already an entry for a
 * given farm and date, and will contain that entry's value
 * 
 * @author Sam Schiedermayer
 *
 */
@SuppressWarnings("serial")
public class DuplicateAdditionException extends Exception {
	private int weight;
	
	public DuplicateAdditionException(int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}
}
