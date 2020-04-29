package exceptions;

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
