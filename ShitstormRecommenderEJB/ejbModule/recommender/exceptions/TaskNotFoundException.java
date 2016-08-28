package recommender.exceptions;

public class TaskNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	private String errorDetails;

	public TaskNotFoundException(String taskRef, String processRef) {
		super("Invalid Task");
		this.errorDetails = new StringBuilder("Task with reference ").append(taskRef)
				.append(" was not found for process with reference ").append(processRef).append("!").toString();
	}

	public String getFaultInfo() {
		return this.errorDetails;
	}

}
