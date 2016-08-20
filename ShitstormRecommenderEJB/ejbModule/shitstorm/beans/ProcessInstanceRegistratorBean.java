package shitstorm.beans;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import shitstorm.constants.Constants;
import shitstorm.exceptions.ProcessNotSupportedException;
import shitstorm.interfaces.IProcessDAO;
import shitstorm.interfaces.IProcessInstanceDAO;
import shitstorm.persistence.entities.EProcess;
import shitstorm.persistence.entities.EProcessinstance;

@Stateless
@LocalBean
public class ProcessInstanceRegistratorBean {

	@EJB
	IProcessDAO daoProcess;

	@EJB
	IProcessInstanceDAO daoProcessInstance;

	public EProcessinstance registerProcessInstance(String refProcess, String refProcessInstance)
			throws IOException, ProcessNotSupportedException {
		EProcess process = this.daoProcess.findByReferenceInProcessEngine(refProcess);

		if (process != null) {
			EProcessinstance processInstanceInDb = this.daoProcessInstance.findByRefInProcessEngine(refProcessInstance);
			if (processInstanceInDb != null) {
				return processInstanceInDb;
			}

			// Beginne mit der Registrierung der Prozessinstanz
			// Sammle die Pfade für die Ordnerstruktur
			String instanceFilename = new StringBuilder(process.getProcessName()).append("-").append(refProcessInstance)
					.append(".xdsl").toString();

			String diagramForInstance = new StringBuilder(Constants.INFLUENCE_DIAGRAM_DIR_FOR_PROCESS)
					.append(process.getProcessName()).append(File.separator).append(refProcessInstance)
					.append(File.separator).append(instanceFilename).toString();

			// Einflussdiagramm des Prozesses von
			// influencedDiagrams/<processname> kopieren nach
			// influencedDiagrams/<processname>/<refProcessInstance>/<filename>
			File diagramInstanceFile = new File(diagramForInstance);
			if (!diagramInstanceFile.exists()) {
				diagramInstanceFile.getParentFile().mkdirs();
				Path sourcePath = Paths.get(
						process.getInfluenceDiagramPath() + File.separator + process.getInfluenceDiagramFilename());
				Path targetPath = Paths.get(diagramForInstance);
				Files.copy(sourcePath, targetPath);
			}

			// Prozessinstanzobjekt anlegen
			EProcessinstance processInstance = new EProcessinstance();
			processInstance.setRefInProcessengine(refProcessInstance);
			processInstance.setInfluenceDiagramFilename(instanceFilename);
			processInstance.setInfluenceDiagramPath(diagramInstanceFile.getParent());
			processInstance.setProcess(process);
			processInstance.setCurrentPeriod(0);
			this.daoProcessInstance.create(processInstance);
			return processInstance;
		}
		throw new ProcessNotSupportedException(refProcess);
	}
}
