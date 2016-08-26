package shitstorm.exceptions;

public class GoalNotSupportedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GoalNotSupportedException(String goalFigure) {
		super(new StringBuilder("GoalFigure ").append(goalFigure).append(" not supported! Recommendation canceled!")
				.toString());
	}
}
