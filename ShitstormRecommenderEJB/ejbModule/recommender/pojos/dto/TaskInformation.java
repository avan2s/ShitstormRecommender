package recommender.pojos.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TaskInformation")
public class TaskInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	private String refNameInProcessEngine;
	private String taskState;

	public TaskInformation() {

	}

	@XmlElement(name = "taskReferenceInProcessEngine")
	public String getRefNameInProcessEngine() {
		return refNameInProcessEngine;
	}

	public void setRefNameInProcessEngine(String refNameInProcessEngine) {
		this.refNameInProcessEngine = refNameInProcessEngine;
	}

	@XmlElement(required = true)
	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

}
