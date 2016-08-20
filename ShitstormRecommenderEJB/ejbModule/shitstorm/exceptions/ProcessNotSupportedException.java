package shitstorm.exceptions;

public class ProcessNotSupportedException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProcessNotSupportedException(String processRef) {
		super(new StringBuilder("Process with reference ").append(processRef).append(" is not supported!").toString());
	}

}
