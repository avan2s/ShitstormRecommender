package shitstorm.interfaces;

import java.util.List;

import kip.tools.InfluenceDiagramNetwork;
import kip.tools.exception.ValueNotReadableException;
import shitstorm.persistence.entities.EEvidence;

public interface IEvidenceLoader {

	public InfluenceDiagramNetwork loadEvidencesIntoNetwork(String refInstanceInProcessEngine, InfluenceDiagramNetwork network) throws ValueNotReadableException;
}