package shitstorm.pojos.dto;

import java.io.Serializable;

public class TaskInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	private String refNameInProcessEngine;
	private String taskState;

	public TaskInformation() {

	}

	public String getRefNameInProcessEngine() {
		return refNameInProcessEngine;
	}

	public void setRefNameInProcessEngine(String refNameInProcessEngine) {
		this.refNameInProcessEngine = refNameInProcessEngine;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

}
