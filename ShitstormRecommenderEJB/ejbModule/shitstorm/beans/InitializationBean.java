package shitstorm.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import shitstorm.interfaces.IGoalDAO;
import shitstorm.interfaces.IProcessDAO;
import smile.Network;

@Singleton
@Startup
public class InitializationBean {
	
//	static
//	{
//	    try {
//	        System.loadLibrary("JCoreImpl");
//	        System.out.println("JCoreImpl loaded");
//	        m_bLibraryLoaded = true;
//	    } catch (UnsatisfiedLinkError e) {
//	        m_bLibraryLoaded = false;
//	        System.out.println("JCoreImpl NOT loaded " + e);
//	    }
//	}

	@EJB
	private IGoalDAO goalDao;

	@EJB
	private IProcessDAO processDAO;

	@EJB
	private NodeRegistratorBean nodeRegistrator;

	@PostConstruct
	private void initialize() {
		Network network = new Network();
		// System.setProperty("java.library.path", "D:/Development/dlls");
		// System.loadLibrary("jsmile");
		// Network net = new Network();
		//this.nodeRegistrator.registerNodesForInfluenceDiagram("shitstorm.xdsl");

		//
		// if (goalDao.readAll().size() == 0) {
		// // Define Process
		// EProcess process = new EProcess();
		// process.setInfluenceDiagramFilename("prototype_shitstorm_part_final
		// unrolled_3");
		// process.setInfluenceDiagramPeriodSeperator("_");
		// process.setProcessName("Testprozess");
		// process.setRefInProcessengine("Ref(Tesprozess)");
		//
		// // Define Test-Instance
		// // // TODO Später entfernen, um dynamisch zu halten
		// EProcessinstance processInstance = new EProcessinstance();
		// processInstance.setCurrentPeriod(0);
		// processInstance.setRefInProcessengine("Ref-Testinstance");
		// processInstance.setInfluenceDiagramFilename(processInstance.getRefInProcessengine()
		// + ".xdsl");
		// process.addProcessinstance(processInstance);
		//
		// // Define Goals
		// EGoal gCost = new EGoal();
		// gCost.setGoalFigure("Kosten");
		// gCost.setGoalEffect(GoalEffect.NEGATIVE);
		//
		// EGoal gTime = new EGoal();
		// gTime.setGoalFigure("Zeitaufwand");
		// gTime.setGoalEffect(GoalEffect.NEGATIVE);
		//
		// EGoal gCustomerSatisfaction = new EGoal();
		// gCustomerSatisfaction.setGoalEffect(GoalEffect.POSITIVE);
		// gCustomerSatisfaction.setGoalFigure("Kundenzufriedenheit");
		//
		// // Define NodeSets
		// ENodeSet nsKosten = new ENodeSet();
		// nsKosten.setNodeAbbreviation("K");
		// nsKosten.setNodeFocus(NodeFocus.GOAL);
		//
		// ENodeSet nsZeitaufwand = new ENodeSet();
		// nsZeitaufwand.setNodeAbbreviation("ZA");
		// nsZeitaufwand.setNodeFocus(NodeFocus.GOAL);
		//
		// ENodeSet nsCustomerSatisfaction = new ENodeSet();
		// nsCustomerSatisfaction.setNodeAbbreviation("KZ");
		// nsCustomerSatisfaction.setNodeFocus(NodeFocus.GOAL);
		//
		// // Define
		//
		// }

		// nsDAO.cr
		// eate(nsKosten);
		// this.initializeDummy();
		// this.initializeDecisionFocus();
		// this.initializeGoals();
		// this.initializeTasks();
		// this.initializeProcessvariables();
	}

	// private void initializeDummy() {
	// // Create Dummy Focus - All incoming goals, variables can only
	// // be created with a NodeFocus, because of 1:1 relationship
	// // and NodeFocus as primary Key. All goals, variables and tasks, which
	// // have no abbreviation in the influence diagram until yet, will get the
	// // dummy as NodeFocus. The allocation can be updatet by the configurator
	// // of the
	// // prescritive tool
	// ENodeFocus nfDummy = new ENodeFocus();
	// nfDummy.setNodeFocus(NodeFocus.OTHER);
	// nfDao.create(nfDummy);
	// }
	//
	// private void initializeDecisionFocus() {
	// // Create Decision Focus - Nodes in Influencediagram are abbreviated
	// // with E
	// ENodeFocus nfDecision = new ENodeFocus();
	// nfDecision.setNodeFocus(NodeFocus.DECISION);
	// nfDecision.setNodeAbbreviation("E");
	// nfDao.create(nfDecision);
	// }
	//
	// private void initializeGoals() {
	// // Create Focus for cost-goals - are abbreviated in Influencediagram
	// // with K
	// ENodeFocus nfCost = new ENodeFocus();
	// nfCost.setNodeFocus(NodeFocus.GOAL);
	// nfCost.setNodeAbbreviation("K");
	// nfDao.create(nfCost);
	//
	// // Create Focus for time - are abbreviated in Influencediagram with ZA
	// // (Zeitaufwand)
	// ENodeFocus nfTime = new ENodeFocus();
	// nfTime.setNodeFocus(NodeFocus.GOAL);
	// nfTime.setNodeAbbreviation("ZA");
	// nfDao.create(nfTime);
	//
	// // Create Focus for Customer satisfaction - are abbreviated in
	// // Influencediagram with KZ (Kundenzufriedenheit)
	// ENodeFocus nfCustomerSatisfaction = new ENodeFocus();
	// nfCustomerSatisfaction.setNodeFocus(NodeFocus.GOAL);
	// nfCustomerSatisfaction.setNodeAbbreviation("ZA");
	// nfDao.create(nfCustomerSatisfaction);
	// }
	//
	// private void initializeProcessvariables() {
	//
	// }
	//

}
