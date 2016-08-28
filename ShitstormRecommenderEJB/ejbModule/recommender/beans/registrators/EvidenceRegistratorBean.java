package recommender.beans.registrators;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import kip.tools.model.KipEvidence;
import recommender.exceptions.ProcessInstanceNotSupportedException;
import recommender.interfaces.local.IEvidenceDAO;
import recommender.interfaces.local.INodeDAO;
import recommender.interfaces.local.IProcessInstanceDAO;
import recommender.persistence.entities.EEvidence;
import recommender.persistence.entities.ENode;
import recommender.persistence.entities.EProcessinstance;
import recommender.pojos.dto.ProcessvariableInformation;
import recommender.pojos.dto.TaskInformation;

@Stateless
@LocalBean
public class EvidenceRegistratorBean {

	@EJB
	INodeDAO daoNode;

	@EJB
	IEvidenceDAO daoEvidence;

	@EJB
	IProcessInstanceDAO daoProcessInstance;

	public List<KipEvidence> registerNewEvidences(String refProcessInstance,
			List<ProcessvariableInformation> variableInformation, List<TaskInformation> taskInformation)
			throws ProcessInstanceNotSupportedException {
		List<KipEvidence> evidences = new ArrayList<>();
		evidences.addAll(this.registerEvidenceByVariableInformation(refProcessInstance, variableInformation));
		evidences.addAll(this.registerEvidenceByTaskInformation(refProcessInstance, taskInformation));
		return evidences;
	}

	public List<KipEvidence> registerEvidenceByVariableInformation(String refProcessInstance,
			List<ProcessvariableInformation> variableInformation) throws ProcessInstanceNotSupportedException {
		List<KipEvidence> evidences = new ArrayList<>();
		for (ProcessvariableInformation varInf : variableInformation) {
			String refVar = varInf.getRefNameInProcessEngine();
			EProcessinstance processinstance = this.daoProcessInstance.findByRefInProcessEngine(refProcessInstance);
			if (processinstance == null) {
				throw new ProcessInstanceNotSupportedException(refProcessInstance);
			}
			String refProcess = processinstance.getProcess().getRefInProcessengine();
			ENode node = this.daoNode.findByProcessAndVariableRef(refProcess, refVar,
					processinstance.getCurrentPeriod());

			// Nur als Evidenz aufnehmen, wenn Knoten existiert (node darf nicht
			// null sein)
			if (node != null) {
				EEvidence evidence = new EEvidence();
				evidence.setNode(node);
				evidence.setProcessinstance(processinstance);
				evidence.setValue(varInf.getValue());
				this.daoEvidence.create(evidence);
				KipEvidence kipEvidence = new KipEvidence();
				kipEvidence.setNodeName(node.getNodeName());
				kipEvidence.setEvidenceValue(varInf.getValue());
				evidences.add(kipEvidence);
			}
		}
		return evidences;
	}

	public List<KipEvidence> registerEvidenceByTaskInformation(String refProcessInstance,
			List<TaskInformation> taskInformation) throws ProcessInstanceNotSupportedException {
		List<KipEvidence> evidences = new ArrayList<>();
		for (TaskInformation taskInf : taskInformation) {
			String refTask = taskInf.getRefNameInProcessEngine();
			EProcessinstance processinstance = this.daoProcessInstance.findByRefInProcessEngine(refProcessInstance);
			if (processinstance == null) {
				throw new ProcessInstanceNotSupportedException(refProcessInstance);
			}

			String refProcess = processinstance.getProcess().getRefInProcessengine();
			ENode node = this.daoNode.findByProcessAndTaskRef(refProcess, refTask, processinstance.getCurrentPeriod());

			// Nur wenn ein zugehöriger Knoten gefunden wurde
			if (node != null) {
				EEvidence evidence = new EEvidence();
				evidence.setNode(node);
				evidence.setProcessinstance(processinstance);
				evidence.setValue(taskInf.getTaskState());

				this.daoEvidence.create(evidence);

				KipEvidence kipEvidence = new KipEvidence();
				kipEvidence.setNodeName(node.getNodeName());
				kipEvidence.setEvidenceValue(taskInf.getTaskState());
				evidences.add(kipEvidence);
			}
		}
		return evidences;
	}
}
