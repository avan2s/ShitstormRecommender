package recommender.interfaces.remote;

import java.util.List;

import kip.tools.exception.ValueNotReadableException;
import recommender.enums.SequenceType;
import recommender.exceptions.CalculationFailedException;
import recommender.exceptions.GoalNotSupportedException;
import recommender.exceptions.ProcessInstanceNotSupportedException;
import recommender.exceptions.ProcessNotSupportedException;
import recommender.pojos.dto.GoalRequest;
import recommender.pojos.dto.NextActionRecommendation;
import recommender.pojos.dto.ProcessvariableInformation;
import recommender.pojos.dto.SequenceRecommendation;
import recommender.pojos.dto.TaskInformation;

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