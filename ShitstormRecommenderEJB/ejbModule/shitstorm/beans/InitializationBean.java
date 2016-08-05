package shitstorm.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class InitializationBean {

	@PostConstruct
	public void initialize() {
		
		int x = 0;
		x=x+1;
//		this.initializeDummy();
//		this.initializeDecisionFocus();
//		this.initializeGoals();
//		this.initializeTasks();
//		this.initializeProcessvariables();
	}

//	private void initializeDummy() {
//		// Create Dummy Focus - All incoming goals, variables can only
//		// be created with a NodeFocus, because of 1:1 relationship
//		// and NodeFocus as primary Key. All goals, variables and tasks, which
//		// have no abbreviation in the influence diagram until yet, will get the
//		// dummy as NodeFocus. The allocation can be updatet by the configurator
//		// of the
//		// prescritive tool
//		ENodeFocus nfDummy = new ENodeFocus();
//		nfDummy.setNodeFocus(NodeFocus.OTHER);
//		nfDao.create(nfDummy);
//	}
//
//	private void initializeDecisionFocus() {
//		// Create Decision Focus - Nodes in Influencediagram are abbreviated
//		// with E
//		ENodeFocus nfDecision = new ENodeFocus();
//		nfDecision.setNodeFocus(NodeFocus.DECISION);
//		nfDecision.setNodeAbbreviation("E");
//		nfDao.create(nfDecision);
//	}
//
//	private void initializeGoals() {
//		// Create Focus for cost-goals - are abbreviated in Influencediagram
//		// with K
//		ENodeFocus nfCost = new ENodeFocus();
//		nfCost.setNodeFocus(NodeFocus.GOAL);
//		nfCost.setNodeAbbreviation("K");
//		nfDao.create(nfCost);
//
//		// Create Focus for time - are abbreviated in Influencediagram with ZA
//		// (Zeitaufwand)
//		ENodeFocus nfTime = new ENodeFocus();
//		nfTime.setNodeFocus(NodeFocus.GOAL);
//		nfTime.setNodeAbbreviation("ZA");
//		nfDao.create(nfTime);
//
//		// Create Focus for Customer satisfaction - are abbreviated in
//		// Influencediagram with KZ (Kundenzufriedenheit)
//		ENodeFocus nfCustomerSatisfaction = new ENodeFocus();
//		nfCustomerSatisfaction.setNodeFocus(NodeFocus.GOAL);
//		nfCustomerSatisfaction.setNodeAbbreviation("ZA");
//		nfDao.create(nfCustomerSatisfaction);
//	}
//
//	private void initializeProcessvariables() {
//
//	}
//
//	private void initializeTasks() {
//		// TODO Auto-generated method stub
//	}

}
