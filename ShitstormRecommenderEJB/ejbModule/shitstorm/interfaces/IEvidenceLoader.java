package shitstorm.interfaces;

import java.util.List;

import shitstorm.persistence.entities.EEvidence;

public interface IEvidenceLoader {

	public List<EEvidence> loadEvidencesFromProcessInstance(String refInProcessEngine);

}