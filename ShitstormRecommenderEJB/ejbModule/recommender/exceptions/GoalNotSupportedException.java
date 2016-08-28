package recommender.exceptions;

public class GoalNotSupportedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorDetails;

	public GoalNotSupportedException(String goalFigure) {
		super("Invalid Goal");
		this.errorDetails = new StringBuilder("GoalFigure ").append(goalFigure)
				.append(" not supported! Recommendation cancelled!").toString();
	}

	public String getFaultInfo() {
		return errorDetails;
	}
}
