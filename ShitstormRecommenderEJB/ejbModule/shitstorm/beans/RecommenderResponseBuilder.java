package shitstorm.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import kip.tools.model.KipSequence;
import kip.tools.model.NextBestAction;
import kip.tools.model.SimAct;
import kip.tools.model.SimGoal;
import kip.tools.model.SimPeriod;
import shitstorm.interfaces.INodeGroupDAO;
import shitstorm.interfaces.ITaskDAO;
import shitstorm.persistence.entities.ENodeGroup;
import shitstorm.pojos.dto.SequenceRecommendation;

@Stateless
@LocalBean
public class RecommenderResponseBuilder {
	@EJB
	private ITaskDAO daoTask;

	@EJB
	private INodeGroupDAO daoGroup;

	public NextBestAction build(NextBestAction nextBestAction, String refProcess) {
		List<SimAct> simActs = nextBestAction.getSimPeriod().getSimActValues();
		String bestActionAbbreviation = nextBestAction.getAction();

		// Index der besten Aktion finden und Aktionen um zustäzliche
		// Taskinformationen ergänzen
		for (SimAct simAct : simActs) {
			ENodeGroup ngForAction = this.daoGroup.findByAbbreviationInProcess(refProcess, simAct.getAction());
			simAct.setTaskRefForAction(ngForAction.getTask().getRefInProcessengine());
			simAct.setTaskNameForAction(ngForAction.getTask().getTaskName());
			// Wenn die Simulationswerte sich auf die besteAktion beziehen
			if (simAct.getAction().equals(bestActionAbbreviation)) {
				nextBestAction.setTaskRefForAction(ngForAction.getTask().getRefInProcessengine());
				nextBestAction.setTaskNameForAction(ngForAction.getTask().getTaskName());
				break;
			}
		}
		return nextBestAction;
	}

	public SequenceRecommendation build(KipSequence kipSequence, String refProcess) {

		List<NextBestAction> nextBestActions = kipSequence.getSequence();
		for (NextBestAction nextAction : nextBestActions) {
			nextAction = this.build(nextAction, refProcess);
		}
		SequenceRecommendation sequenceRecommendation = new SequenceRecommendation();
		sequenceRecommendation.setNextBestActions(nextBestActions);

		NextBestAction lastBestAction = nextBestActions.get(nextBestActions.size() - 1);
		SimPeriod lastSimPeriod = lastBestAction.getSimPeriod();
		SimAct lastSimAct = lastSimPeriod.getSimActByAction(lastBestAction.getAction());
		List<SimGoal> simGoals = lastSimAct.getSimGoalValues();
		sequenceRecommendation.setSimGoals(simGoals);
		return sequenceRecommendation;
	}

//	private List<SimulatedGoal> buildGoalValues(SimAct simAct) {
//		// Simulationswerte für Zielgrößen überführen
//		List<SimulatedGoal> simulatedGoals = new ArrayList<>();
//		List<SimGoal> kipGoalsSim = simAct.getSimGoalValues();
//		for (SimGoal simGoal : kipGoalsSim) {
//			double uniformUtility = simGoal.getExpectedValue().getUniformUtility();
//			double unitValue = simGoal.getExpectedValue().getUnitValue();
//			SimulatedGoal simulatedGoal = new SimulatedGoal();
//			simulatedGoal.setUniformUtility(uniformUtility);
//			simulatedGoal.setUnitValue(unitValue);
//			simulatedGoals.add(simulatedGoal);
//		}
//		return simulatedGoals;
//	}
}
