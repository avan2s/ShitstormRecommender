package shitstorm.interfaces.remote;

import java.util.List;

import kip.tools.exception.ValueNotReadableException;
import shitstorm.enums.SequenceType;
import shitstorm.exceptions.CalculationFailedException;
import shitstorm.exceptions.GoalNotSupportedException;
import shitstorm.exceptions.ProcessInstanceNotSupportedException;
import shitstorm.exceptions.ProcessNotSupportedException;
import shitstorm.pojos.dto.GoalRequest;
import shitstorm.pojos.dto.NextActionRecommendation;
import shitstorm.pojos.dto.ProcessvariableInformation;
import shitstorm.pojos.dto.SequenceRecommendation;
import shitstorm.pojos.dto.TaskInformation;

public interface IRecommender {

	public NextActionRecommendation recommendNextAction(String refProcessInProcessEngine,
			String refProcessInstanceInProcessEngine, List<GoalRequest> goalRequests,
			List<ProcessvariableInformation> variableInformation, List<TaskInformation> taskInformation,
			boolean doNothingActionAllowed) throws ProcessNotSupportedException, ProcessInstanceNotSupportedException,
			ValueNotReadableException, GoalNotSupportedException, CalculationFailedException;

	public SequenceRecommendation recommendSequence(SequenceType type, int numberOfDecisions,
			String refProcessInProcessEngine, String refProcessInstanceInProcessEngine, List<GoalRequest> goalRequests,
			List<ProcessvariableInformation> variableInformation, List<TaskInformation> taskInformation,
			boolean doNothingActionAllowed) throws ProcessNotSupportedException, ProcessInstanceNotSupportedException,
			ValueNotReadableException, GoalNotSupportedException, CalculationFailedException;

}