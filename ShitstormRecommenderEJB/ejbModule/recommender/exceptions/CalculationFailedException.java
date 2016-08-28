package recommender.exceptions;

public class CalculationFailedException extends Exception {

	private static final long serialVersionUID = 1L;
	private String errorDetails;

	public CalculationFailedException(String errorDetails) {
		super("Calculation failed!");
		this.errorDetails = errorDetails;
	}

	public String getFaultInfo() {
		return this.errorDetails;
	}

}
