package shitstorm.beans;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import shitstorm.exceptions.ProcessInstanceNotSupportedException;
import shitstorm.exceptions.TaskNotFoundException;
import shitstorm.interfaces.IEvidenceDAO;
import shitstorm.interfaces.INodeDAO;
import shitstorm.interfaces.IProcessInstanceDAO;
import shitstorm.persistence.entities.EEvidence;
import shitstorm.persistence.entities.ENode;
import shitstorm.persistence.entities.EProcessinstance;
import shitstorm.persistence.entities.ETakenDecision;

@Stateless
@LocalBean
public class DecisionRegistratorBean {

	@EJB
	INodeDAO daoNode;

	@EJB
	IEvidenceDAO daoEvidence;

	@EJB
	IProcessInstanceDAO daoProcessinstance;

	public ETakenDecision registerTakenDecision(String refInstance, String taskRefForTakenDecision)
			throws ProcessInstanceNotSupportedException, TaskNotFoundException {
		EProcessinstance processinstance = this.daoProcessinstance.findByRefInProcessEngine(refInstance);
		if (processinstance == null) {
			throw new ProcessInstanceNotSupportedException(refInstance);
		}
		String refProcess = processinstance.getProcess().getRefInProcessengine();
		int period = processinstance.getCurrentPeriod();
		ENode node = this.daoNode.findByProcessAndTaskRef(refProcess, taskRefForTakenDecision, period);
		if (node == null) {
			throw new TaskNotFoundException(taskRefForTakenDecision, refProcess);
		}
		String taskName = node.getNodeGroup().getTask().getTaskName();
		ETakenDecision takenDecision = new ETakenDecision();
		takenDecision.setDecisionName(taskName);

		// In den Zufallsentscheidungsknoten sind die Aktivitäten mit den
		// gleichen Abürzungen benannt, wie die Knoten, die die
		// Aktivitätszustände halten
		String decision = node.getNodeGroup().getNodeAbbreviation();

		// Evidenz setzen
		EEvidence evidence = new EEvidence();
		evidence.setProcessinstance(processinstance);
		evidence.setValue(decision);
		evidence.setNode(node);
		evidence.setTakenDecision(takenDecision);
		evidence = this.daoEvidence.create(evidence);

		// Entscheidungsperiode um eins erhöhen
		processinstance.setCurrentPeriod(period + 1);
		this.daoProcessinstance.update(processinstance);

		return evidence.getTakenDecision();
	}

}
