package recommender.beans.registrators;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import recommender.constants.Constants;
import recommender.exceptions.ProcessNotSupportedException;
import recommender.interfaces.local.IProcessDAO;
import recommender.interfaces.local.IProcessInstanceDAO;
import recommender.persistence.entities.EProcess;
import recommender.persistence.entities.EProcessinstance;

@Stateless
@LocalBean
public class ProcessInstanceRegistratorBean {

	@EJB
	IProcessDAO daoProcess;
	
	
	@EJB
	IProcessInstanceDAO daoProcessInstance;

	public EProcessinstance registerProcessInstance(String refProcess, String refProcessInstance)
			throws ProcessNotSupportedException {
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
				try {
					Files.copy(sourcePath, targetPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
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
