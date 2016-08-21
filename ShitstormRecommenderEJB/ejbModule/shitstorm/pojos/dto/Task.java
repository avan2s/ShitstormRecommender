package shitstorm.pojos.dto;

import java.io.Serializable;

public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	private String taskName;
	private String taskReference;

	public Task() {

	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskReference() {
		return taskReference;
	}

	public void setTaskReference(String taskReference) {
		this.taskReference = taskReference;
	}

}
