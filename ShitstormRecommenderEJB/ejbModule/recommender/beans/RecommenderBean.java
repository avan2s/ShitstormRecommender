package recommender.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import kip.tools.InfluenceDiagramNetwork;
import kip.tools.MaxBenefitSequenceCalculator;
import kip.tools.MaxProbabilitySequenceCalculator;
import kip.tools.NextActionCalculator;
import kip.tools.SequenceCalculator;
import kip.tools.exception.ValueNotReadableException;
import kip.tools.model.KipGoal;
import kip.tools.model.KipSequence;
import kip.tools.model.NextBestAction;
import recommender.beans.loader.Evidence2InfluenceDiagramLoader;
import recommender.beans.loader.InfluenceDiagramLoaderBean;
import recommender.beans.registrators.EvidenceRegistratorBean;
import recommender.beans.registrators.ProcessInstanceRegistratorBean;
import recommender.enums.SequenceType;
import recommender.exceptions.CalculationFailedException;
import recommender.exceptions.GoalNotSupportedException;
import recommender.exceptions.PeriodOutOfRangeException;
import recommender.exceptions.ProcessInstanceNotSupportedException;
import recommender.exceptions.ProcessNotSupportedException;
import recommender.interfaces.local.IGoalDAO;
import recommender.interfaces.remote.IDecisionRegistrator;
import recommender.interfaces.remote.IRecommender;
import recommender.persistence.entities.EGoal;
import recommender.persistence.entities.EProcessinstance;
import recommender.pojos.dto.GoalRequest;
import recommender.pojos.dto.NextActionRecommendation;
import recommender.pojos.dto.ProcessvariableInformation;
import recommender.pojos.dto.SequenceRecommendation;
import recommender.pojos.dto.TaskInformation;

@Stateless
@Remote(IRecommender.class)
public class RecommenderBean implements IRecommender {

	private InfluenceDiagramNetwork network;
	private EProcessinstance processinstance;
	

	@EJB
	private ProcessInstanceRegistratorBean processInstanceRegistrator;

	@EJB
	private IGoalDAO daoGoal;

	@EJB
	private Evidence2InfluenceDiagramLoader evidenceLoader;

	@EJB
	private EvidenceRegistratorBean evidenceRegistrator;

	@EJB
	private InfluenceDiagramLoaderBean influenceDiagramLoader;

	@EJB
	private IDecisionRegistrator decisionRegistrator;

	@EJB
	private RecommenderResponseBuilder responseBuilder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see shitstorm.beans.IRecommender#recommendNextAction(java.lang.String,
	 * java.lang.String, java.util.List, java.util.List, java.util.List,
	 * boolean)
	 */
	@Override
	public NextActionRecommendation recommendNextAction(String refProcessInProcessEngine,
			String refProcessInstanceInProcessEngine, List<GoalRequest> goalRequests,
			List<ProcessvariableInformation> variableInformation, List<TaskInformation> taskInformation,
			boolean doNothingActionAllowed) throws ProcessNotSupportedException, ProcessInstanceNotSupportedException,
			ValueNotReadableException, GoalNotSupportedException, CalculationFailedException {
		this.normalizeGoalRequests(goalRequests);
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
		try {
			nextActionCalculator.calculateNextBestAction(currentPeriod, periodForRecommendation, kipGoals);
		} catch (Exception e) {
			throw new CalculationFailedException(e.getMessage());
		}

		// Response konstruieren
		NextBestAction nextBestAction = nextActionCalculator.getNextBestAction();
		String refProcess = this.processinstance.getProcess().getRefInProcessengine();
		NextActionRecommendation response = this.responseBuilder.build(nextBestAction, refProcess);
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shitstorm.beans.IRecommender#recommendSequence(shitstorm.enums.
	 * SequenceType, int, java.lang.String, java.lang.String, java.util.List,
	 * java.util.List, java.util.List, boolean)
	 */
	@Override
	public SequenceRecommendation recommendSequence(SequenceType type, int numberOfDecisions,
			String refProcessInProcessEngine, String refProcessInstanceInProcessEngine, List<GoalRequest> goalRequests,
			List<ProcessvariableInformation> variableInformation, List<TaskInformation> taskInformation,
			boolean doNothingActionAllowed) throws ValueNotReadableException, GoalNotSupportedException,
			ProcessNotSupportedException, ProcessInstanceNotSupportedException, CalculationFailedException {
		this.normalizeGoalRequests(goalRequests);
		String additionalInformation = "";

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

		// Letzte Periode für eine Entscheidung ermitteln. Bei einer
		// Entscheidung enspricht die aktuelle Periode der letzten Periode für
		// eine Handlungsempfehlung
		int lastPeriod = currentPeriod + numberOfDecisions - 1;

		int lastDecisionPeriodInInfluenceDiagram = processinstance.getProcess().getLastDecisionPeriod();

		// Für den Prototyp
		if (lastPeriod > lastDecisionPeriodInInfluenceDiagram) {
			PeriodOutOfRangeException exception = new PeriodOutOfRangeException(refProcessInProcessEngine, lastPeriod,
					lastDecisionPeriodInInfluenceDiagram);
			additionalInformation = exception.getMessage()
					+ " Calculation was performed until the last supported Decision period of the diagram!";
		}

		// Sequenzcalculator auswählen und konfigurieren
		SequenceCalculator sequenceCalculator = null;
		if (type == SequenceType.MAX_BENEFIT) {
			sequenceCalculator = new MaxBenefitSequenceCalculator(this.network);
		} else {
			sequenceCalculator = new MaxProbabilitySequenceCalculator(this.network);
		}
		sequenceCalculator.setNothingActionAllowed(doNothingActionAllowed);

		// Sequenz berechnen
		try {
			sequenceCalculator.calculate(currentPeriod, lastPeriod, kipGoals);
		} catch (Exception e) {
			throw new CalculationFailedException(e.getMessage());
		}

		// Antwort aufbereiten
		KipSequence kipSequence = sequenceCalculator.getKipSequence();
		SequenceRecommendation recommendation = this.responseBuilder.build(kipSequence, refProcessInProcessEngine,
				additionalInformation);
		return recommendation;
	}

	private void registerInstanceAndEvidences(String refProcessInProcessEngine,
			String refProcessInstanceInProcessEngine, List<ProcessvariableInformation> variableInformation,
			List<TaskInformation> taskInformation)
			throws ProcessNotSupportedException, ProcessInstanceNotSupportedException {
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

	/**
	 * Normalize the goal weight, so that they will be in sum = 1
	 * 
	 * @param goalRequests
	 *            list of goalRequests, where the weight needs to be normalized
	 * @return goalRequests with normal weight
	 */
	private List<GoalRequest> normalizeGoalRequests(List<GoalRequest> goalRequests) {
		double sum = 0;
		for (GoalRequest goalRequest : goalRequests) {
			sum = sum + goalRequest.getGoalWeight();
		}
		for (GoalRequest goalRequest : goalRequests) {
			goalRequest.setGoalWeight(goalRequest.getGoalWeight() / sum);
		}
		return goalRequests;
	}

	private List<KipGoal> constructKipGoalsByGoalRequests(List<GoalRequest> goalRequests)
			throws GoalNotSupportedException {
		List<KipGoal> kipGoals = new ArrayList<>();
		for (GoalRequest goalRequest : goalRequests) {
			String goalFigure = goalRequest.getGoalFigure();
			EGoal goal = this.daoGoal.findByGoalFigure(goalFigure);
			if (goal == null || !goal.isValidForRecommendation()) {
				throw new GoalNotSupportedException(goalFigure);
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
}
