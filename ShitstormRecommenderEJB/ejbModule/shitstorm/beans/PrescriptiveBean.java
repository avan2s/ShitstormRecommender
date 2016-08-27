package shitstorm.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;

import kip.tools.exception.ValueNotReadableException;
import shitstorm.enums.ObservedEffect;
import shitstorm.enums.SequenceType;
import shitstorm.exceptions.CalculationFailedException;
import shitstorm.exceptions.GoalNotSupportedException;
import shitstorm.exceptions.ProcessInstanceNotSupportedException;
import shitstorm.exceptions.ProcessNotSupportedException;
import shitstorm.exceptions.TaskNotFoundException;
import shitstorm.interfaces.remote.IDecisionEffectRegistrator;
import shitstorm.interfaces.remote.IDecisionRegistrator;
import shitstorm.interfaces.remote.IRecommender;
import shitstorm.interfaces.webservices.IPrescriptiveKipService;
import shitstorm.pojos.dto.GoalRequest;
import shitstorm.pojos.dto.NextActionRecommendation;
import shitstorm.pojos.dto.ProcessvariableInformation;
import shitstorm.pojos.dto.SequenceRecommendation;
import shitstorm.pojos.dto.TaskInformation;

@Stateless
@Remote(IPrescriptiveKipService.class)
@WebService(serviceName = "PrescriptiveKipService")
public class PrescriptiveBean implements IPrescriptiveKipService {

	@EJB
	private IRecommender recommenderBean;

	@EJB
	private IDecisionRegistrator decisionRegistrator;

	@EJB
	private IDecisionEffectRegistrator effectRegistrator;

	@Override
	public SequenceRecommendation recommendSequence(SequenceType type, int numberOfDecisions,
			String refProcessInProcessEngine, String refProcessInstanceInProcessEngine, List<GoalRequest> goalRequests,
			List<ProcessvariableInformation> variableInformation, List<TaskInformation> taskInformation,
			boolean doNothingActionAllowed) throws ProcessNotSupportedException, ProcessInstanceNotSupportedException,
			ValueNotReadableException, GoalNotSupportedException, CalculationFailedException {

		return this.recommenderBean.recommendSequence(type, numberOfDecisions, refProcessInProcessEngine,
				refProcessInstanceInProcessEngine, goalRequests, variableInformation, taskInformation,
				doNothingActionAllowed);
	}

	@Override
	public NextActionRecommendation recommendNextAction(String refProcessInProcessEngine,
			String refProcessInstanceInProcessEngine, List<GoalRequest> goalRequests,
			List<ProcessvariableInformation> variableInformation, List<TaskInformation> taskInformation,
			boolean doNothingActionAllowed) throws ProcessNotSupportedException, ProcessInstanceNotSupportedException,
			ValueNotReadableException, GoalNotSupportedException, CalculationFailedException {
		return this.recommenderBean.recommendNextAction(refProcessInProcessEngine, refProcessInstanceInProcessEngine,
				goalRequests, variableInformation, taskInformation, doNothingActionAllowed);
	}

	@Override
	public String registerDecision(String refProcessInProcessEngine, String refProcessInstanceInProcessEngine,
			String taskRefForTakenDecision, List<ProcessvariableInformation> variableInformation,
			List<TaskInformation> taskInformation) throws ProcessNotSupportedException,
			ProcessInstanceNotSupportedException, TaskNotFoundException {
		return this.decisionRegistrator.registerDecision(refProcessInProcessEngine, refProcessInstanceInProcessEngine,
				taskRefForTakenDecision, variableInformation, taskInformation);
	}

	@Override
	public String registerEffect(String refInstance, String taskReference, String goalFigure,
			ObservedEffect observedEffect) throws ProcessInstanceNotSupportedException {
		this.effectRegistrator.registerEffect(refInstance, goalFigure, observedEffect, taskReference);
		return "Registration successful";
	}

}
