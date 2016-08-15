package shitstorm.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import kip.tools.InfluenceDiagramElementExtractor;
import kip.tools.InfluenceDiagramNetwork;
import shitstorm.constants.Constants;
import shitstorm.enums.NodeFocus;
import shitstorm.interfaces.INodeDAO;
import shitstorm.interfaces.INodeGroupDAO;
import shitstorm.interfaces.IProcessDAO;
import shitstorm.persistence.entities.ENode;
import shitstorm.persistence.entities.ENodeGroup;
import shitstorm.persistence.entities.EProcess;

@Stateless
public class RegistratorBean {
	@EJB
	IProcessDAO daoProcess;
	@EJB
	INodeDAO daoNode;
	@EJB
	INodeGroupDAO daoNodeGroup;

	public EProcess registerProcess(String processName, String refInProcessEngine, String influenceDiagramFilename,
			String periodSeperator, String instancePeriod, String decisionAbbreviation) throws FileNotFoundException, IOException {
		EProcess process = this.daoProcess.findByReferenceInProcessEngine(refInProcessEngine);
		if (process == null) {
			process = new EProcess();
			process.setProcessName(processName);
			process.setRefInProcessengine(refInProcessEngine);

			// Einflussdiagramm auf Existenz prüfen
			String filePath = "./" + Constants.INFLUENCE_DIAGRAM_DIR + processName + "/" + influenceDiagramFilename;
			File file = new File(filePath);
			if (!file.exists()) {
				throw new FileNotFoundException("No process found in:" + file.getCanonicalPath()
						+ "! Please make sure the diagram file is in this directory!");
			}
			process.setInfluenceDiagramFilename(influenceDiagramFilename);
			process.setInfluenceDiagramPath(file.getParent());
			process.setInfluenceDiagramPeriodSeperator(periodSeperator);
			process.setInfluenceDiagramInstancePeriod(instancePeriod);
			process.setInfluenceDiagramDecisionAbbreviation(decisionAbbreviation);
			process = this.daoProcess.create(process);
		}
		this.registerNodes(process.getRefInProcessengine());
		return process;
	}

	private EProcess registerNodes(String refInProcessEngine) throws NoResultException, IOException {
		// Finde den Prozess, dem die Knoten des Einflussdiagrammes zugeordnet
		// werden sollen
		EProcess process = this.daoProcess.findByReferenceInProcessEngine(refInProcessEngine);
		if (process == null) {
			throw new NoResultException("Process not found!");
		}

		// Einflussdiagramm auslesen
		InfluenceDiagramNetwork net = new InfluenceDiagramNetwork();
		net.setPeriodSeperator(process.getInfluenceDiagramPeriodSeperator());
		net.setInstancePeriod(process.getInfluenceDiagramInstancePeriod());

		// Einflussdiagramm auslesen
		String fullPath = process.getInfluenceDiagramPath() + File.separator + process.getInfluenceDiagramFilename();
		net.readFile(fullPath);

		// Jeden ausgelesenen Knoten mit zugehöriger Gruppe in Datenbank
		// speichern und dem Einflussdiagramm des Processes zuordnen
		this.importDiagramElementsIntoDatabase(net, process);

		return process;

	}

	private void importDiagramElementsIntoDatabase(InfluenceDiagramNetwork net, EProcess process) {
		InfluenceDiagramElementExtractor extractor = new InfluenceDiagramElementExtractor(net);
		String[] allNodeIds = net.getAllNodeIds();
		for (String nodeId : allNodeIds) {

			int period = extractor.extractPeriodFromNodeId(nodeId, false);
			if (period != -1) {
				String abbreviation = extractor.extractAbbreviation(nodeId);
				ENodeGroup nodeGroup = this.daoNodeGroup.findByAbbreviationInProcess(process.getRefInProcessengine(),
						abbreviation);
				if (nodeGroup == null) {
					nodeGroup = new ENodeGroup();
					nodeGroup.setNodeAbbreviation(abbreviation);
					if(abbreviation.contentEquals(process.getInfluenceDiagramDecisionAbbreviation())){
						nodeGroup.setNodeFocus(NodeFocus.DECISION);
					}
					else{
						nodeGroup.setNodeFocus(NodeFocus.UNKNOWN);
					}
					nodeGroup = this.daoNodeGroup.create(nodeGroup);
				}

				// Knoten des Einflussdiagramms anlegen
				ENode node = new ENode();
				node.setNodeName(nodeId);
				node.setPeriod(period);
				node.setNodeGroup(nodeGroup);
				process.addNode(node);
				this.daoNode.create(node);
			}
		}
	}

}
