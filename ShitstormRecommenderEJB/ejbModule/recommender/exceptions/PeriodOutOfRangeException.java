package recommender.exceptions;

public class PeriodOutOfRangeException extends Exception {

	private static final long serialVersionUID = 1L;
	private String errorDetails;

	public PeriodOutOfRangeException(String refProcess, int notSupportedPeriod, int maxSupportedPeriod) {
		super("Invalid period-range");
		this.errorDetails = new StringBuilder("Influencediagram for Process  ").append(refProcess).append(" has only ")
				.append(maxSupportedPeriod).append(" periods. It doesnt't support period ").append(notSupportedPeriod)
				.append("!").toString();
	}

	public String getFaultInfo() {
		return errorDetails;
	}

}
