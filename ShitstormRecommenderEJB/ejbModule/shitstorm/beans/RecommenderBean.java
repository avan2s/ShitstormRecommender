package shitstorm.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import kip.tools.InfluenceDiagramNetwork;
import kip.tools.MaxBenefitSequenceCalculator;
import kip.tools.MaxProbabilitySequenceCalculator;
import kip.tools.NextActionCalculator;
import kip.tools.SequenceCalculator;
import kip.tools.exception.ValueNotReadableException;
import kip.tools.model.KipGoal;
import kip.tools.model.KipSequence;
import shitstorm.enums.SequenceType;
import shitstorm.exceptions.ProcessInstanceNotSupportedException;
import shitstorm.exceptions.ProcessNotSupportedException;
import shitstorm.exceptions.TaskNotFoundException;
import shitstorm.interfaces.IEvidenceLoader;
import shitstorm.interfaces.IGoalDAO;
import shitstorm.interfaces.INodeDAO;
import shitstorm.interfaces.IRecommendation;
import shitstorm.persistence.entities.EGoal;
import shitstorm.persistence.entities.EProcess;
import shitstorm.persistence.entities.EProcessinstance;
import shitstorm.pojos.dto.GoalRequest;
import shitstorm.pojos.dto.NextBestActionRecommendation;
import shitstorm.pojos.dto.ProcessvariableInformation;
import shitstorm.pojos.dto.SequenceRecommendation;
import shitstorm.pojos.dto.TaskInformation;

@Stateless
@Remote(IRecommendation.class)
@WebService(serviceName = "RecommendationService")
public class RecommenderBean implements IRecommendation {

	private InfluenceDiagramNetwork network;
	private EProcessinstance processinstance;

	@PersistenceContext
	EntityManager em;

	@EJB
	ProcessInstanceRegistratorBean processInstanceRegistrator;

	@EJB
	IGoalDAO daoGoal;

	@EJB
	INodeDAO daoNode;

	@EJB
	IEvidenceLoader evidenceLoader;

	@EJB
	EvidenceRegistratorBean evidenceRegistrator;

	@EJB
	InfluenceDiagramLoaderBean influenceDiagramLoader;

	@EJB
	DecisionRegistratorBean decisionRegistrator;

	@Override
	public String registerDecision(String refProcessInProcessEngine, String refProcessInstanceInProcessEngine,
			String taskRefForTakenDecision, List<ProcessvariableInformation> variableInformation,
			List<TaskInformation> taskInformation) throws ProcessNotSupportedException, IOException,
			ProcessInstanceNotSupportedException, TaskNotFoundException {

		// Prozessinstanzobjekt erhalten (neu registrieren falls noch nicht
		// angelegt)
		this.processinstance = this.processInstanceRegistrator.registerProcessInstance(refProcessInProcessEngine,
				refProcessInstanceInProcessEngine);

		// Neue Evidenzen in die Datenbank überführen
		this.evidenceRegistrator.registerNewEvidences(this.processinstance.getRefInProcessengine(), variableInformation,
				taskInformation);

		// Find node By Process Period and abbreviation
		this.decisionRegistrator.registerTakenDecision(refProcessInstanceInProcessEngine, taskRefForTakenDecision);

		return "Decision registered successfully!";
	}

	@Override
	public NextBestActionRecommendation recommendNextAction(String refProcessInProcessEngine,
			String refProcessInstanceInProcessEngine, List<GoalRequest> goalRequests,
			List<ProcessvariableInformation> variableInformation, List<TaskInformation> taskInformation,
			boolean doNothingActionAllowed) throws Exception {

		// Prozessinstanz registrieren falls nötig
		this.registerInstanceAndEvidences(refProcessInProcessEngine, refProcessInstanceInProcessEngine,
				variableInformation, taskInformation);

		// Einflussdiagramm der Instanz auslesen und mit Evidenzen belegen
		this.buildInfluenceDiagram();

		// Im Prototyp soll stets eine Handlungsempfehlung für die jetzige
		// Entscheidungsperiode gemacht werden
		int currentPeriod = this.processinstance.getCurrentPeriod();
		int periodForRecommendation = currentPeriod;

		// Die GoalRequests auf Kip-Goal-Objekte für die Berechnung der
		// Handlungsempfehlung transformieren
		List<KipGoal> kipGoals = this.constructKipGoalsByGoalRequests(goalRequests);

		// Kalulation der nächst besten Aktion durchführen
		NextActionCalculator nextActionCalculator = new NextActionCalculator(this.network);
		nextActionCalculator.setNothingActionAllowed(doNothingActionAllowed);
		nextActionCalculator.calculateNextBestAction(currentPeriod, periodForRecommendation, kipGoals);

		// Response konstruieren
		NextBestActionRecommendation response = new NextBestActionRecommendation();
		response.setNextBestAction(nextActionCalculator.getNextBestAction());
		response.setSimValues(nextActionCalculator.getSimPeriod());

		return response;
	}

	@Override
	public SequenceRecommendation recommendSequence(SequenceType type, int lastRecommendationPeriod,
			String refProcessInProcessEngine, String refProcessInstanceInProcessEngine, List<GoalRequest> goalRequests,
			List<ProcessvariableInformation> variableInformation, List<TaskInformation> taskInformation,
			boolean doNothingActionAllowed) throws Exception {

		// Prozessinstanz registrieren falls nötig
		this.registerInstanceAndEvidences(refProcessInProcessEngine, refProcessInstanceInProcessEngine,
				variableInformation, taskInformation);

		// Einflussdiagramm der Instanz auslesen und mit Evidenzen belegen
		this.buildInfluenceDiagram();

		// Aktuelle Periode bestimmen
		int currentPeriod = this.processinstance.getCurrentPeriod();

		// Die GoalRequests auf Kip-Goal-Objekte für die Berechnung der
		// Handlungsempfehlung transformieren
		List<KipGoal> kipGoals = this.constructKipGoalsByGoalRequests(goalRequests);

		// E3 ist die letzte Entscheidungsperiode im Einflussdiagramm. Der
		// Prototyp ermittelt immer eine Handlungsempfehlungsreihenfolge für
		// alle zu treffenden Entscheidungen
		int lastPeriod = lastRecommendationPeriod;

		// Sequenzcalculator auswählen und konfigurieren
		SequenceCalculator sequenceCalculator = null;
		if (type == SequenceType.MAX_BENEFIT) {
			sequenceCalculator = new MaxBenefitSequenceCalculator(this.network);
		} else {
			sequenceCalculator = new MaxProbabilitySequenceCalculator(this.network);
		}
		sequenceCalculator.setNothingActionAllowed(doNothingActionAllowed);

		// Sequenz berechnen
		sequenceCalculator.calculate(currentPeriod, lastPeriod, kipGoals);

		// Antwort aufbereiten
		KipSequence kipSequence = sequenceCalculator.getKipSequence();
		SequenceRecommendation recommendation = new SequenceRecommendation();
		recommendation.setNextBestActions(kipSequence.getSequence());
		recommendation.setSimValuesInPeriods(sequenceCalculator.getSimPeriods());
		return recommendation;
	}

	private void registerInstanceAndEvidences(String refProcessInProcessEngine,
			String refProcessInstanceInProcessEngine, List<ProcessvariableInformation> variableInformation,
			List<TaskInformation> taskInformation)
			throws IOException, ProcessNotSupportedException, ProcessInstanceNotSupportedException {
		// Registriere Prozessinstanz und gebe diese zurück. Wenn keine
		// existiert, wird eine neue angelegt
		this.processinstance = this.processInstanceRegistrator.registerProcessInstance(refProcessInProcessEngine,
				refProcessInstanceInProcessEngine);

		// Neue Evidenzen in der Datenbank registrieren
		this.evidenceRegistrator.registerNewEvidences(this.processinstance.getRefInProcessengine(), variableInformation,
				taskInformation);
	}

	private InfluenceDiagramNetwork buildInfluenceDiagram()
			throws ProcessInstanceNotSupportedException, ValueNotReadableException {
		// Lade das Netzwerk zu der Prozessinstanz
		this.network = this.influenceDiagramLoader
				.loadInfluenceDiagramFromInstance(this.processinstance.getRefInProcessengine());

		// Evidenden in das Netzwerk laden
		this.network = this.evidenceLoader.loadEvidencesIntoNetwork(this.processinstance.getRefInProcessengine(),
				network);
		return this.network;
	}

	private List<KipGoal> constructKipGoalsByGoalRequests(List<GoalRequest> goalRequests) throws Exception {
		List<KipGoal> kipGoals = new ArrayList<>();
		for (GoalRequest goalRequest : goalRequests) {
			String goalFigure = goalRequest.getGoalFigure();
			EGoal goal = this.daoGoal.findByGoalFigure(goalFigure);
			if (goal == null || !goal.isValidForRecommendation()) {
				throw new Exception("GoalFigure " + goalFigure + " is not supported. Recommendation canceled!");
			}
			String abbreviation = goal.getNodeGroup().getNodeAbbreviation();
			KipGoal kipGoal = new KipGoal();
			kipGoal.setAbbreviation(abbreviation);
			kipGoal.setGoalEffect(goal.getGoalEffect());
			kipGoal.setGoalstart_period(goalRequest.getGoalStartPeriod());
			kipGoal.setGoalend_period(goalRequest.getGoalEndPeriod());
			kipGoal.setGoalValue(goalRequest.getGoalValue());
			kipGoal.setGoalTarget(goalFigure);
			kipGoal.setGoalWeight(goalRequest.getGoalWeight());
			kipGoals.add(kipGoal);
		}
		return kipGoals;
	}

	// private int getLastPeriodWithEvidence(String refInstanceInProcessEngine)
	// {
	// String sql = "SELECT max(n.period) FROM ENode n JOIN n.evidences e JOIN
	// e.processinstance pi WHERE pi.refInProcessengine=:refInstance";
	// TypedQuery<Integer> query = this.em.createQuery(sql,
	// Integer.class).setParameter("refInstance",
	// refInstanceInProcessEngine);
	// List<Integer> results = query.getResultList();
	// if (results.size() > 0) {
	// return results.get(0);
	// }
	// return -1;
	// }
}
