package shitstorm.beans;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebResult;

import shitstorm.exceptions.ProcessInstanceNotSupportedException;
import shitstorm.exceptions.ProcessNotSupportedException;
import shitstorm.exceptions.TaskNotFoundException;
import shitstorm.interfaces.IEvidenceDAO;
import shitstorm.interfaces.INodeDAO;
import shitstorm.interfaces.IProcessInstanceDAO;
import shitstorm.persistence.entities.EEvidence;
import shitstorm.persistence.entities.ENode;
import shitstorm.persistence.entities.EProcessinstance;
import shitstorm.persistence.entities.ETakenDecision;
import shitstorm.pojos.dto.ProcessvariableInformation;
import shitstorm.pojos.dto.TaskInformation;

@Stateless
@LocalBean
public class DecisionRegistratorBean {

	@EJB
	private INodeDAO daoNode;

	@EJB
	private IEvidenceDAO daoEvidence;

	@EJB
	private IProcessInstanceDAO daoProcessinstance;

	@EJB
	private EvidenceRegistratorBean evidenceRegistrator;

	@EJB
	private ProcessInstanceRegistratorBean processInstanceRegistrator;

	private EProcessinstance processinstance;

	@WebResult(name = "responseMessage")
	public String registerDecision(@WebParam(name = "refProcess") String refProcessInProcessEngine,
			@WebParam(name = "refInstance") String refProcessInstanceInProcessEngine,
			@WebParam(name = "refTask") String taskRefForTakenDecision,
			@WebParam(name = "variableInformationList") List<ProcessvariableInformation> variableInformation,
			@WebParam(name = "taskInformationList") List<TaskInformation> taskInformation)
			throws ProcessNotSupportedException, IOException, ProcessInstanceNotSupportedException,
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
