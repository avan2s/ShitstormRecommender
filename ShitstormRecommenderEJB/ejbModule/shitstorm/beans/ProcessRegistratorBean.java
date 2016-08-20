package shitstorm.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import kip.tools.InfluenceDiagramElementExtractor;
import kip.tools.InfluenceDiagramNetwork;
import shitstorm.constants.Constants;
import shitstorm.enums.NodeFocus;
import shitstorm.exceptions.ProcessNotSupportedException;
import shitstorm.interfaces.INodeDAO;
import shitstorm.interfaces.INodeGroupDAO;
import shitstorm.interfaces.IProcessDAO;
import shitstorm.persistence.entities.ENode;
import shitstorm.persistence.entities.ENodeGroup;
import shitstorm.persistence.entities.EProcess;

@Stateless
public class ProcessRegistratorBean {

	private InfluenceDiagramElementExtractor extractor;
	private InfluenceDiagramNetwork network;

	@EJB
	IProcessDAO daoProcess;

	@EJB
	INodeGroupDAO daoNodeGroup;

	@EJB
	INodeDAO daoNode;

	@EJB
	InfluenceDiagramLoaderBean influenceDiagramLoader;

	public EProcess registerProcess(String processName, String refInProcessEngine, String influenceDiagramFilename,
			String periodSeperator, String instancePeriod, String decisionAbbreviation,
			boolean includeNodeUserProperties)
			throws FileNotFoundException, IOException, NoResultException, ProcessNotSupportedException {
		EProcess process = this.daoProcess.findByReferenceInProcessEngine(refInProcessEngine);
		if (process == null) {
			process = new EProcess();
			process.setProcessName(processName);
			process.setRefInProcessengine(refInProcessEngine);

			// Einflussdiagramm auf Existenz prüfen
			String filePath = Constants.INFLUENCE_DIAGRAM_DIR_FOR_PROCESS + processName + File.separator
					+ influenceDiagramFilename;
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
		process = this.registerNodes(process.getRefInProcessengine(), includeNodeUserProperties);
		return process;
	}

	private EProcess registerNodes(String refProcess, boolean includeNodeUserProperties)
			throws NoResultException, IOException, ProcessNotSupportedException {
		// Finde den Prozess, dem die Knoten des Einflussdiagrammes zugeordnet
		// werden sollen
		EProcess process = this.daoProcess.findByReferenceInProcessEngine(refProcess);
		if (process == null) {
			throw new ProcessNotSupportedException(refProcess);
		}
		//
		// // Einflussdiagramm auslesen
		// this.network = new InfluenceDiagramNetwork();
		// this.network.setPeriodSeperator(process.getInfluenceDiagramPeriodSeperator());
		// this.network.setInstancePeriod(process.getInfluenceDiagramInstancePeriod());
		//
		// // Einflussdiagramm auslesen
		// String fullPath = process.getInfluenceDiagramPath() + File.separator
		// + process.getInfluenceDiagramFilename();
		// this.network.readFile(fullPath);

		this.network = this.influenceDiagramLoader.loadInfluenceDiagramFromProcess(refProcess);

		// Jeden ausgelesenen Knoten mit zugehöriger Gruppe in Datenbank
		// speichern und dem Einflussdiagramm des Processes zuordnen
		this.importNodesIntoDatabase(process, includeNodeUserProperties);

		return process;

	}

	private List<ENode> importNodesIntoDatabase(EProcess process, boolean includeNodeUserProperties) {
		this.extractor = new InfluenceDiagramElementExtractor(this.network);
		List<ENode> nodes = new ArrayList<>();
		String[] allNodeIds = this.network.getAllNodeIds();
		for (String nodeId : allNodeIds) {
			int period = this.extractor.extractPeriodFromNodeId(nodeId, false);

			// Wenn es keine ungültige Periode ist...
			if (period != -1) {
				// extrahiere die Abkürzung des Knotens...
				String abbreviation = this.extractor.extractAbbreviation(nodeId);
				// und speichere sie in der Datenbank
				ENodeGroup nodeGroup = this.daoNodeGroup.findByAbbreviationInProcess(process.getRefInProcessengine(),
						abbreviation);
				if (nodeGroup == null) {
					nodeGroup = this.importNodeGroupIntoDatabase(abbreviation, nodeId, includeNodeUserProperties);
				}

				// Knoten des Einflussdiagramms anlegen, wenn eine Knotengruppe
				// existiert
				ENode node = this.daoNode.findByNodeName(nodeId);
				if (node == null && nodeGroup != null) {
					node = this.daoNode.create(nodeId, period, nodeGroup, process);
					nodes.add(node);
					process.addNode(node);
				}
			}
		}
		return nodes;
	}

	private ENodeGroup importNodeGroupIntoDatabase(String abbreviation, String nodeId,
			boolean includeNodeUserProperties) {
		ENodeGroup nodeGroup = new ENodeGroup();
		nodeGroup.setNodeAbbreviation(abbreviation);
		if (includeNodeUserProperties) {
			String nodeFocus = this.extractor.extractNodeUserProperty(nodeId, "nodefocus");
			if (nodeFocus != null) {
				if (NodeFocus.DECISION.toString().contentEquals(nodeFocus)) {
					nodeGroup.setNodeFocus(NodeFocus.DECISION);
				} else if (NodeFocus.GOAL.toString().contentEquals(nodeFocus)) {
					nodeGroup.setNodeFocus(NodeFocus.GOAL);
				} else if (NodeFocus.TASK.toString().contentEquals(nodeFocus)) {
					nodeGroup.setNodeFocus(NodeFocus.TASK);
				} else if (NodeFocus.VARIABLE.toString().contentEquals(nodeFocus)) {
					nodeGroup.setNodeFocus(NodeFocus.VARIABLE);
				} else {
					nodeGroup.setNodeFocus(NodeFocus.UNKNOWN);
				}
				return this.daoNodeGroup.create(nodeGroup);
			} else {
				return null;
			}
		} else {
			nodeGroup.setNodeFocus(NodeFocus.UNKNOWN);
		}
		return this.daoNodeGroup.create(nodeGroup);
	}
}
