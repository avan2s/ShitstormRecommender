package shitstorm.beans.loader;

import java.io.File;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import kip.tools.InfluenceDiagramNetwork;
import shitstorm.beans.registrators.ProcessInstanceRegistratorBean;
import shitstorm.exceptions.ProcessInstanceNotSupportedException;
import shitstorm.exceptions.ProcessNotSupportedException;
import shitstorm.interfaces.local.IProcessDAO;
import shitstorm.interfaces.local.IProcessInstanceDAO;
import shitstorm.persistence.entities.EProcess;
import shitstorm.persistence.entities.EProcessinstance;

@Stateless
@LocalBean
public class InfluenceDiagramLoaderBean{
	@EJB
	IProcessDAO daoProcess;

	@EJB
	IProcessInstanceDAO processInstanceDAO;

	@EJB
	ProcessInstanceRegistratorBean processInstanceRegistrator;

	public InfluenceDiagramNetwork loadInfluenceDiagramFromInstance(String refInstanceInProcessEngine)
			throws ProcessInstanceNotSupportedException {
		EProcessinstance processinstance = this.processInstanceDAO.findByRefInProcessEngine(refInstanceInProcessEngine);
		if (processinstance == null) {
			throw new ProcessInstanceNotSupportedException(refInstanceInProcessEngine);
		}

		EProcess process = processinstance.getProcess();

		String periodSeperator = process.getInfluenceDiagramPeriodSeperator();
		String instancePeriod = process.getInfluenceDiagramInstancePeriod();
		String decisionAbbreviation = process.getInfluenceDiagramDecisionAbbreviation();

		InfluenceDiagramNetwork network = new InfluenceDiagramNetwork(periodSeperator, decisionAbbreviation,
				instancePeriod);

		String fullPathToDiagram = processinstance.getInfluenceDiagramPath() + File.separator
				+ processinstance.getInfluenceDiagramFilename();
		network.readFile(fullPathToDiagram);
		network.updateBeliefs();
		return network;
	}

	public InfluenceDiagramNetwork loadInfluenceDiagramFromProcess(String refInProcessEngine)
			throws ProcessNotSupportedException {
		// Finde den Prozess, dem die Knoten des Einflussdiagrammes zugeordnet
		// werden sollen
		EProcess process = this.daoProcess.findByReferenceInProcessEngine(refInProcessEngine);
		if (process == null) {
			throw new ProcessNotSupportedException(refInProcessEngine);
		}

		// Einflussdiagramm auslesen
		InfluenceDiagramNetwork network = new InfluenceDiagramNetwork();
		network.setPeriodSeperator(process.getInfluenceDiagramPeriodSeperator());
		network.setInstancePeriod(process.getInfluenceDiagramInstancePeriod());

		// Einflussdiagramm auslesen
		String fullPath = process.getInfluenceDiagramPath() + File.separator + process.getInfluenceDiagramFilename();
		network.readFile(fullPath);
		network.updateBeliefs();
		return network;
	}
}
