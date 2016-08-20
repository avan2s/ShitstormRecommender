package shitstorm.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import shitstorm.interfaces.IGoalDAO;
import shitstorm.interfaces.IProcessDAO;

@Singleton
@Startup
public class InitializationBean {

	@EJB 
	IGoalDAO goalDAO; 
	
	@EJB
	private IProcessDAO processDAO;

	@EJB
	private ProcessInstanceRegistratorBean registrator;

	@PostConstruct
	private void initialize() {
		//this.goalDAO.findByGoalFigure("Kundenzufriedenheit");
		
//		try {
//			EProcess process =  this.processDAO.findByReferenceInProcessEngine("shitstorm-ref");
//			EGoal goal = this.goalDao.findByGoalFigure("Kundenzufriedenheit");
//			if(goal!= null){
//				//process.getGoals().remove(goal);
//				process.getGoals().add(goal);
//			}
//			
////			goal.setGoalEffect(GoalEffect.POSITIVE);
////			goal.setGoalFigure("Informationsgewinn");
////			process.getGoals().remove(goal);
//			//goal.getProcesses().add(process);
//			this.processDAO.update(process);
//			
//			//this.registrator.registerProcess("shitstorm", "shitstorm-ref", "shitstorm.xdsl", "_", "I","E",true);
//		} catch (NoResultException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		// System.setProperty("java.library.path", "D:/Development/dlls");
		// System.loadLibrary("jsmile");
		// Network net = new Network();
		

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

}
