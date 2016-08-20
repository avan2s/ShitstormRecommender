package shitstorm.interfaces;

import java.util.List;

import shitstorm.persistence.entities.EEvidence;

public interface IEvidenceDAO extends IGenericDAO<EEvidence, Integer>{
	public List<EEvidence> loadEvidencesFromProcessInstance(String refInstanceInProcessEngine);
}
