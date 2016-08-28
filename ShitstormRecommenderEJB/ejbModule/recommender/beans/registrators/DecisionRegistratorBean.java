package recommender.beans.registrators;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebResult;

import recommender.exceptions.ProcessInstanceNotSupportedException;
import recommender.exceptions.ProcessNotSupportedException;
import recommender.exceptions.TaskNotFoundException;
import recommender.interfaces.local.IEvidenceDAO;
import recommender.interfaces.local.INodeDAO;
import recommender.interfaces.local.IProcessInstanceDAO;
import recommender.interfaces.local.ITaskDAO;
import recommender.interfaces.remote.IDecisionRegistrator;
import recommender.persistence.entities.EEvidence;
import recommender.persistence.entities.ENode;
import recommender.persistence.entities.EProcess;
import recommender.persistence.entities.EProcessinstance;
import recommender.persistence.entities.ETakenDecision;
import recommender.persistence.entities.ETask;
import recommender.pojos.dto.ProcessvariableInformation;
import recommender.pojos.dto.TaskInformation;

@Stateless
@Remote(IDecisionRegistrator.class)
public class DecisionRegistratorBean implements IDecisionRegistrator {

	@EJB
	private INodeDAO daoNode;

	@EJB
	private ITaskDAO daoTask;

	@EJB
	private IEvidenceDAO daoEvidence;

	@EJB
	private IProcessInstanceDAO daoProcessinstance;

	@EJB
	private EvidenceRegistratorBean evidenceRegistrator;

	@EJB
	private ProcessInstanceRegistratorBean processInstanceRegistrator;

	private EProcessinstance processinstance;

	@Override
	@WebResult(name = "responseMessage")
	public String registerDecision(@WebParam(name = "refProcess") String refProcessInProcessEngine,
			String refProcessInstanceInProcessEngine, String taskRefForTakenDecision,
			List<ProcessvariableInformation> variableInformation, List<TaskInformation> taskInformation)
			throws ProcessNotSupportedException,ProcessInstanceNotSupportedException,
			TaskNotFoundException {

		// Prozessinstanzobjekt erhalten (neu registrieren falls noch nicht
		// angelegt)
		this.processinstance = this.processInstanceRegistrator.registerProcessInstance(refProcessInProcessEngine,
				refProcessInstanceInProcessEngine);

		// Neue Evidenzen in die Datenbank überführen
		this.evidenceRegistrator.registerNewEvidences(this.processinstance.getRefInProcessengine(), variableInformation,
				taskInformation);

		// Find node By Process Period and abbreviation
		this.registerTakenDecision(refProcessInstanceInProcessEngine, taskRefForTakenDecision);

		return "Decision registered successfully!";
	}

	private ETakenDecision registerTakenDecision(String refInstance, String taskRefForTakenDecision)
			throws ProcessInstanceNotSupportedException, TaskNotFoundException {
		EProcessinstance processinstance = this.daoProcessinstance.findByRefInProcessEngine(refInstance);
		if (processinstance == null) {
			throw new ProcessInstanceNotSupportedException(refInstance);
		}
		EProcess process = processinstance.getProcess();
		String refProcess = process.getRefInProcessengine();
		String decisionAbbreviation = process.getInfluenceDiagramDecisionAbbreviation();

		int period = processinstance.getCurrentPeriod();
		ETask task = this.daoTask.findByProcessAndTaskReference(refProcess, taskRefForTakenDecision);
		if (task == null) {
			throw new TaskNotFoundException(taskRefForTakenDecision, refProcess);
		}

		ETakenDecision takenDecision = new ETakenDecision();
		takenDecision.setTask(task);
		// In den Zufallsentscheidungsknoten sind die Aktivitäten mit den
		// gleichen Abkürzungen benannt, wie die Knoten, die die
		// Aktivitätszustände halten
		// String decision = node.getNodeGroup().getNodeAbbreviation();
		String takenDecisionTaskAbbreviation = task.getNodeGroup().getNodeAbbreviation();

		ENode decisionNode = this.daoNode.findByProcessAndAbbreviationAndPeriod(refProcess, decisionAbbreviation,
				period);

		// Evidenz setzen
		EEvidence evidence = new EEvidence();
		evidence.setProcessinstance(processinstance);
		evidence.setValue(takenDecisionTaskAbbreviation);
		evidence.setNode(decisionNode);
		evidence.setTakenDecision(takenDecision);
		takenDecision.setEvidence(evidence);
		evidence = this.daoEvidence.create(evidence);

		// Entscheidungsperiode um eins erhöhen
		processinstance.setCurrentPeriod(period + 1);
		this.daoProcessinstance.update(processinstance);

		return evidence.getTakenDecision();
	}

}
