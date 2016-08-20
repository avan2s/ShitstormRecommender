package shitstorm.interfaces;

import java.io.IOException;
import java.util.List;

import shitstorm.enums.SequenceType;
import shitstorm.exceptions.ProcessInstanceNotSupportedException;
import shitstorm.exceptions.ProcessNotSupportedException;
import shitstorm.exceptions.TaskNotFoundException;
import shitstorm.pojos.dto.GoalRequest;
import shitstorm.pojos.dto.NextBestActionRecommendation;
import shitstorm.pojos.dto.ProcessvariableInformation;
import shitstorm.pojos.dto.SequenceRecommendation;
import shitstorm.pojos.dto.TaskInformation;

public interface IRecommendation {
	public NextBestActionRecommendation recommendNextAction(String refProcessnameInProcessEngine,
			String refProcessInstanceInProcessEngine, List<GoalRequest> goalRequests,
			List<ProcessvariableInformation> variableInformation, List<TaskInformation> taskInformation,
			boolean doNothingActionAllowed) throws Exception;

	public SequenceRecommendation recommendSequence(SequenceType type, int lastRecommendationPeriod,
			String refProcessInProcessEngine, String refProcessInstanceInProcessEngine, List<GoalRequest> goalRequests,
			List<ProcessvariableInformation> variableInformation, List<TaskInformation> taskInformation,
			boolean doNothingActionAllowed) throws IOException, ProcessNotSupportedException, Exception;

	public String registerDecision(String refProcessInProcessEngine, String refProcessInstanceInProcessEngine,
			String decision, List<ProcessvariableInformation> variableInformation,
			List<TaskInformation> taskInformation) throws ProcessNotSupportedException, IOException,
			ProcessInstanceNotSupportedException, TaskNotFoundException;
}
