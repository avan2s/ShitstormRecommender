package shitstorm.interfaces.webservices;

import java.io.IOException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import shitstorm.enums.ObservedEffect;
import shitstorm.enums.SequenceType;
import shitstorm.exceptions.ProcessInstanceNotSupportedException;
import shitstorm.exceptions.ProcessNotSupportedException;
import shitstorm.exceptions.TaskNotFoundException;
import shitstorm.pojos.dto.GoalRequest;
import shitstorm.pojos.dto.NextActionRecommendation;
import shitstorm.pojos.dto.ProcessvariableInformation;
import shitstorm.pojos.dto.SequenceRecommendation;
import shitstorm.pojos.dto.TaskInformation;

@WebService(name = "PrescriptiveTool")
public interface IPrescriptiveKipService {
	@WebMethod
	@WebResult(name = "nextActionRecommendation")
	public NextActionRecommendation recommendNextAction(@WebParam(name = "refProcess") String refProcessInProcessEngine,
			@WebParam(name = "refInstance") String refProcessInstanceInProcessEngine,
			@WebParam(name = "goals") List<GoalRequest> goalRequests,
			@WebParam(name = "variableInformationList") List<ProcessvariableInformation> variableInformation,
			@WebParam(name = "taskInformationList") List<TaskInformation> taskInformation,
			@WebParam(name = "doNothingActionAllowed") boolean doNothingActionAllowed) throws Exception;

	@WebMethod
	@WebResult(name = "sequenceRecommendation")
	public SequenceRecommendation recommendSequence(@WebParam(name = "sequenceType") SequenceType type,
			@WebParam(name = "numberOfDecisions") int numberOfDecisions,
			@WebParam(name = "refProcess") String refProcessInProcessEngine,
			@WebParam(name = "refInstance") String refProcessInstanceInProcessEngine,
			@WebParam(name = "goals") List<GoalRequest> goalRequests,
			@WebParam(name = "variableInformationList") List<ProcessvariableInformation> variableInformation,
			@WebParam(name = "taskInformationList") List<TaskInformation> taskInformation,
			@WebParam(name = "doNothingActionAllowed") boolean doNothingActionAllowed) throws Exception;

	@WebMethod
	@WebResult(name = "responseMessage")
	public String registerDecision(@WebParam(name = "refProcess") String refProcessInProcessEngine,
			@WebParam(name = "refInstance") String refProcessInstanceInProcessEngine,
			@WebParam(name = "refTask") String taskRefForTakenDecision,
			@WebParam(name = "variableInformationList") List<ProcessvariableInformation> variableInformation,
			@WebParam(name = "taskInformationList") List<TaskInformation> taskInformation)
			throws ProcessNotSupportedException, IOException, ProcessInstanceNotSupportedException,
			TaskNotFoundException;

	@WebMethod
	@WebResult(name = "responseMessage")
	public String registerEffect(@WebParam(name = "refInstance") String refInstance,
			@WebParam(name = "refTask") String taskReference, @WebParam(name = "goalFigure") String goalFigure,
			@WebParam(name = "observedEffect") ObservedEffect observedEffect)
			throws ProcessInstanceNotSupportedException;
}
