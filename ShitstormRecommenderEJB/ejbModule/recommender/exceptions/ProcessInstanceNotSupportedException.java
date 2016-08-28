package recommender.exceptions;

public class ProcessInstanceNotSupportedException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorDetails;

	public ProcessInstanceNotSupportedException(String refProcessInstance) {
		super("Invalid Processinstance");
		this.errorDetails = new StringBuilder("Processinstance with reference ").append(refProcessInstance)
				.append(" is not supported!").toString();
	}

	public String getFaultInfo() {
		return this.errorDetails;
	}

}
