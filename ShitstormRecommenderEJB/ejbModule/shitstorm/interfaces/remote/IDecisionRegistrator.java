package shitstorm.interfaces.remote;

import java.io.IOException;
import java.util.List;

import shitstorm.exceptions.ProcessInstanceNotSupportedException;
import shitstorm.exceptions.ProcessNotSupportedException;
import shitstorm.exceptions.TaskNotFoundException;
import shitstorm.pojos.dto.ProcessvariableInformation;
import shitstorm.pojos.dto.TaskInformation;

public interface IDecisionRegistrator {

	public String registerDecision(String refProcessInProcessEngine, String refProcessInstanceInProcessEngine,
			String taskRefForTakenDecision, List<ProcessvariableInformation> variableInformation,
			List<TaskInformation> taskInformation) throws ProcessNotSupportedException, IOException,
			ProcessInstanceNotSupportedException, TaskNotFoundException;

}