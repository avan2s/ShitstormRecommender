package shitstorm.exceptions;

public class PeriodOutOfRangeException extends Exception {

	private static final long serialVersionUID = 1L;

	public PeriodOutOfRangeException(String refProcess, int notSupportedPeriod, int maxSupportedPeriod) {
		super(new StringBuilder("Influencediagram for Process  ").append(refProcess).append(" has only ")
				.append(maxSupportedPeriod).append(" periods. It doesnt't support period ").append(notSupportedPeriod)
				.append("!").toString());
	}

}
