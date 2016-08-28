package recommender.interfaces.webservices;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import kip.tools.exception.ValueNotReadableException;
import recommender.enums.ObservedEffect;
import recommender.enums.SequenceType;
import recommender.exceptions.CalculationFailedException;
import recommender.exceptions.GoalNotSupportedException;
import recommender.exceptions.ProcessInstanceNotSupportedException;
import recommender.exceptions.ProcessNotSupportedException;
import recommender.exceptions.TaskNotFoundException;
import recommender.pojos.dto.GoalRequest;
import recommender.pojos.dto.NextActionRecommendation;
import recommender.pojos.dto.ProcessvariableInformation;
import recommender.pojos.dto.SequenceRecommendation;
import recommender.pojos.dto.TaskInformation;

@WebService(name = "PrescriptiveRecommender")
public interface IPrescriptiveKipService {
	@WebMethod
	@WebResult(name = "nextActionRecommendation")
	public NextActionRecommendation recommendNextAction(
			@WebParam(name = "refProcess") String refProcessInProcessEngine,
			@WebParam(name = "refInstance") String refProcessInstanceInProcessEngine,
			@WebParam(name = "goals") List<GoalRequest> goalRequests,
			@WebParam(name = "variableInformationList") List<ProcessvariableInformation> variableInformation,
			@WebParam(name = "taskInformationList") List<TaskInformation> taskInformation,
			@WebParam(name = "doNothingActionAllowed") boolean doNothingActionAllowed)
			throws ProcessNotSupportedException, ProcessInstanceNotSupportedException, ValueNotReadableException,
			GoalNotSupportedException, CalculationFailedException;

	@WebMethod
	@WebResult(name = "sequenceRecommendation")
	public SequenceRecommendation recommendSequence(@WebParam(name = "sequenceType") SequenceType type,
			@WebParam(name = "numberOfDecisions") int numberOfDecisions,
			@WebParam(name = "refProcess") String refProcessInProcessEngine,
			@WebParam(name = "refInstance") String refProcessInstanceInProcessEngine,
			@WebParam(name = "goals") List<GoalRequest> goalRequests,
			@WebParam(name = "variableInformationList") List<ProcessvariableInformation> variableInformation,
			@WebParam(name = "taskInformationList") List<TaskInformation> taskInformation,
			@WebParam(name = "doNothingActionAllowed") boolean doNothingActionAllowed)
			throws ProcessNotSupportedException, ProcessInstanceNotSupportedException, ValueNotReadableException,
			GoalNotSupportedException, CalculationFailedException;

	@WebMethod
	@WebResult(name = "registerDecisionResponseMessage")
	public String registerDecision(@WebParam(name = "refProcess") String refProcessInProcessEngine,
			@WebParam(name = "refInstance") String refProcessInstanceInProcessEngine,
			@WebParam(name = "refTask") String taskRefForTakenDecision,
			@WebParam(name = "variableInformationList") List<ProcessvariableInformation> variableInformation,
			@WebParam(name = "taskInformationList") List<TaskInformation> taskInformation)
			throws ProcessNotSupportedException, ProcessInstanceNotSupportedException, TaskNotFoundException;

	@WebMethod
	@WebResult(name = "registerEffectResponseMessage")
	public String registerEffect(@WebParam(name = "refInstance") String refInstance,
			@WebParam(name = "refTask") String taskReference, @WebParam(name = "goalFigure") String goalFigure,
			@WebParam(name = "observedEffect") ObservedEffect observedEffect)
			throws ProcessInstanceNotSupportedException;
}
