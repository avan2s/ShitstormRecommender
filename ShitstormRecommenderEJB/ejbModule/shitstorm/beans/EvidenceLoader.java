package shitstorm.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import kip.tools.EvidenceSetter;
import kip.tools.InfluenceDiagramNetwork;
import kip.tools.exception.ValueNotReadableException;
import kip.tools.model.KipEvidence;
import shitstorm.interfaces.IEvidenceLoader;
import shitstorm.persistence.entities.EEvidence;
import shitstorm.pojos.GenericDAOImpl;

@Stateless
@Local(IEvidenceLoader.class)
public class EvidenceLoader extends GenericDAOImpl<EEvidence, Integer> implements IEvidenceLoader {
	@EJB
	EvidenceDAOBean daoEvidence;
	
	private EvidenceSetter evidenceSetter;
	

	@Override
	public InfluenceDiagramNetwork loadEvidencesIntoNetwork(String refInstanceInProcessEngine, InfluenceDiagramNetwork network) throws ValueNotReadableException {
		this.evidenceSetter = new EvidenceSetter(network);
		List<EEvidence> evidences = this.daoEvidence.loadEvidencesFromProcessInstance(refInstanceInProcessEngine);
		List<KipEvidence> kipEvidences = this.transformIntoKipEvidences(evidences);
		
		// Evidenzen 
		this.evidenceSetter.setEvidences(kipEvidences, false, true);
		return this.evidenceSetter.getNet();
	}
	
	private List<KipEvidence> transformIntoKipEvidences(List<EEvidence> evidences){
		List<KipEvidence> list = new ArrayList<>();
		for (EEvidence e : evidences) {
			list.add(this.transformIntoKipEvidence(e));
		}
		return list;
	}
	
	private KipEvidence transformIntoKipEvidence(EEvidence evidence){
		KipEvidence kipEvidence = new KipEvidence();
		kipEvidence.setEvidenceValue(evidence.getValue());
		kipEvidence.setNodeName(evidence.getNode().getNodeName());
		return kipEvidence;
	}
	
	
	
}
