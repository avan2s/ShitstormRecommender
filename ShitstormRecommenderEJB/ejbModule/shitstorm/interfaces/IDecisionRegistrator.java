package shitstorm.interfaces;

import shitstorm.exceptions.ProcessInstanceNotSupportedException;
import shitstorm.exceptions.TaskNotFoundException;
import shitstorm.persistence.entities.ETakenDecision;

public interface IDecisionRegistrator {
	public ETakenDecision registerTakenDecision(String refInstance, String taskRefForTakenDecision)
			throws ProcessInstanceNotSupportedException, TaskNotFoundException;
}
