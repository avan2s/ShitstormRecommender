package recommender.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;

import kip.tools.exception.ValueNotReadableException;
import recommender.enums.ObservedEffect;
import recommender.enums.SequenceType;
import recommender.exceptions.CalculationFailedException;
import recommender.exceptions.GoalNotSupportedException;
import recommender.exceptions.ProcessInstanceNotSupportedException;
import recommender.exceptions.ProcessNotSupportedException;
import recommender.exceptions.TaskNotFoundException;
import recommender.interfaces.remote.IDecisionEffectRegistrator;
import recommender.interfaces.remote.IDecisionRegistrator;
import recommender.interfaces.remote.IRecommender;
import recommender.interfaces.webservices.IPrescriptiveKipService;
import recommender.pojos.dto.GoalRequest;
import recommender.pojos.dto.NextActionRecommendation;
import recommender.pojos.dto.ProcessvariableInformation;
import recommender.pojos.dto.SequenceRecommendation;
import recommender.pojos.dto.TaskInformation;

@Stateless
@Remote(IPrescriptiveKipService.class)
@WebService(targetNamespace = "http://ws.prescriptiverecommender.org", endpointInterface = "recommender.interfaces.webservices.IPrescriptiveKipService", serviceName = "PrescriptiveRecommenderService", portName = "PrescriptiveRecommenderPort")
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
			List<TaskInformation> taskInformation)
			throws ProcessNotSupportedException, ProcessInstanceNotSupportedException, TaskNotFoundException {
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
