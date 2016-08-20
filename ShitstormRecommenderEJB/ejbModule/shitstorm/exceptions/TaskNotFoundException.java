package shitstorm.exceptions;

public class TaskNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public TaskNotFoundException(String taskRef, String processRef) {
		super(new StringBuilder("Task with reference ").append(taskRef)
				.append(" was not found for process with reference ").append(processRef).append("!").toString());
	}

}
