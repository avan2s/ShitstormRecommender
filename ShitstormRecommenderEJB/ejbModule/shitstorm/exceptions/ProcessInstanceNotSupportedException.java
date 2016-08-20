package shitstorm.exceptions;

public class ProcessInstanceNotSupportedException extends Exception {
	private static final long serialVersionUID = 1L;

	public ProcessInstanceNotSupportedException(String refProcessInstance) {
		super(new StringBuilder("Processinstance with reference ").append(refProcessInstance).append(" is not supported!")
				.toString());
	}
}
