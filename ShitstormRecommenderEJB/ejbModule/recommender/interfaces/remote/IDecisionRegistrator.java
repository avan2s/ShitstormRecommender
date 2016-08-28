package recommender.interfaces.remote;

import java.util.List;

import recommender.exceptions.ProcessInstanceNotSupportedException;
import recommender.exceptions.ProcessNotSupportedException;
import recommender.exceptions.TaskNotFoundException;
import recommender.pojos.dto.ProcessvariableInformation;
import recommender.pojos.dto.TaskInformation;

public interface IDecisionRegistrator {

	public String registerDecision(String refProcessInProcessEngine, String refProcessInstanceInProcessEngine,
			String taskRefForTakenDecision, List<ProcessvariableInformation> variableInformation,
			List<TaskInformation> taskInformation) throws ProcessNotSupportedException,	
			ProcessInstanceNotSupportedException, TaskNotFoundException;

}