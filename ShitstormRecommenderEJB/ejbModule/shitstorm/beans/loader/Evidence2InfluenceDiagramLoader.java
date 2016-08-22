package shitstorm.beans.loader;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import kip.tools.EvidenceSetter;
import kip.tools.InfluenceDiagramNetwork;
import kip.tools.exception.ValueNotReadableException;
import kip.tools.model.KipEvidence;
import shitstorm.interfaces.local.IEvidenceDAO;
import shitstorm.persistence.entities.EEvidence;

@Stateless
@LocalBean
public class Evidence2InfluenceDiagramLoader {
	@EJB
	IEvidenceDAO daoEvidence;

	private EvidenceSetter evidenceSetter;

	public InfluenceDiagramNetwork loadEvidencesIntoNetwork(String refInstanceInProcessEngine,
			InfluenceDiagramNetwork network) throws ValueNotReadableException {
		this.evidenceSetter = new EvidenceSetter(network);
		List<EEvidence> evidences = this.daoEvidence.loadEvidencesFromProcessInstance(refInstanceInProcessEngine);
		List<KipEvidence> kipEvidences = this.transformIntoKipEvidences(evidences);

		// Evidenzen
		this.evidenceSetter.setEvidences(kipEvidences, false, true);
		return this.evidenceSetter.getNet();
	}

	private List<KipEvidence> transformIntoKipEvidences(List<EEvidence> evidences) {
		List<KipEvidence> list = new ArrayList<>();
		for (EEvidence e : evidences) {
			list.add(this.transformIntoKipEvidence(e));
		}
		return list;
	}

	private KipEvidence transformIntoKipEvidence(EEvidence evidence) {
		KipEvidence kipEvidence = new KipEvidence();
		kipEvidence.setEvidenceValue(evidence.getValue());
		kipEvidence.setNodeName(evidence.getNode().getNodeName());
		return kipEvidence;
	}

}
