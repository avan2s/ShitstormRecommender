package recommender.exceptions;

public class ProcessNotSupportedException extends Exception {

	private static final long serialVersionUID = 1L;
	private String errorDetails;

	public ProcessNotSupportedException(String processRef) {
		super("Invalid Process");
		this.errorDetails = new StringBuilder("Process with reference ").append(processRef).append(" is not supported!")
				.toString();
	}

	public String getFaultInfo() {
		return this.errorDetails;
	}
}
