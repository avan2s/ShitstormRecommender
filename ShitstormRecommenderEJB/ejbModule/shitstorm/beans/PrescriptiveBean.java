package shitstorm.beans;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;

import kip.tools.model.NextBestAction;
import shitstorm.enums.SequenceType;
import shitstorm.exceptions.ProcessInstanceNotSupportedException;
import shitstorm.exceptions.ProcessNotSupportedException;
import shitstorm.exceptions.TaskNotFoundException;
import shitstorm.interfaces.IPrescriptiveKipService;
import shitstorm.pojos.dto.GoalRequest;
import shitstorm.pojos.dto.ProcessvariableInformation;
import shitstorm.pojos.dto.SequenceRecommendation;
import shitstorm.pojos.dto.TaskInformation;

@Stateless
@Remote(IPrescriptiveKipService.class)
@WebService(serviceName = "PrescriptiveKipService")
public class PrescriptiveBean implements IPrescriptiveKipService {

	@EJB
	private RecommenderBean recommenderBean;

	@EJB
	private DecisionRegistratorBean decisionRegistrator;

	@Override
	public SequenceRecommendation recommendSequence(SequenceType type, int numberOfDecisions,
			String refProcessInProcessEngine, String refProcessInstanceInProcessEngine, List<GoalRequest> goalRequests,
			List<ProcessvariableInformation> variableInformation, List<TaskInformation> taskInformation,
			boolean doNothingActionAllowed) throws Exception {

		return this.recommenderBean.recommendSequence(type, numberOfDecisions, refProcessInProcessEngine,
				refProcessInstanceInProcessEngine, goalRequests, variableInformation, taskInformation,
				doNothingActionAllowed);
	}

	@Override
	public NextBestAction recommendNextAction(String refProcessInProcessEngine,
			String refProcessInstanceInProcessEngine, List<GoalRequest> goalRequests,
			List<ProcessvariableInformation> variableInformation, List<TaskInformation> taskInformation,
			boolean doNothingActionAllowed) throws Exception {
		return this.recommenderBean.recommendNextAction(refProcessInProcessEngine, refProcessInstanceInProcessEngine,
				goalRequests, variableInformation, taskInformation, doNothingActionAllowed);
	}

	@Override
	public String registerDecision(String refProcessInProcessEngine, String refProcessInstanceInProcessEngine,
			String taskRefForTakenDecision, List<ProcessvariableInformation> variableInformation,
			List<TaskInformation> taskInformation) throws ProcessNotSupportedException, IOException,
			ProcessInstanceNotSupportedException, TaskNotFoundException {
		return this.decisionRegistrator.registerDecision(refProcessInProcessEngine, refProcessInstanceInProcessEngine,
				taskRefForTakenDecision, variableInformation, taskInformation);
	}

}
