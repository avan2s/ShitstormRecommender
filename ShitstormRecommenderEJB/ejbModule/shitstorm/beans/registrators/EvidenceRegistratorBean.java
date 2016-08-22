package shitstorm.beans.registrators;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import kip.tools.model.KipEvidence;
import shitstorm.exceptions.ProcessInstanceNotSupportedException;
import shitstorm.interfaces.local.IEvidenceDAO;
import shitstorm.interfaces.local.INodeDAO;
import shitstorm.interfaces.local.IProcessInstanceDAO;
import shitstorm.persistence.entities.EEvidence;
import shitstorm.persistence.entities.ENode;
import shitstorm.persistence.entities.EProcessinstance;
import shitstorm.pojos.dto.ProcessvariableInformation;
import shitstorm.pojos.dto.TaskInformation;

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
			ENode node = this.daoNode.findByProcessAndVariableRef(refProcess, refVar, processinstance.getCurrentPeriod());

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
		return evidences;
	}
}
