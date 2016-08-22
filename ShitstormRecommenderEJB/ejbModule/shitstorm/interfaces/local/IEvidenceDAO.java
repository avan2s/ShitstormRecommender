package shitstorm.interfaces.local;

import java.util.List;

import shitstorm.interfaces.IGenericDAO;
import shitstorm.persistence.entities.EEvidence;

public interface IEvidenceDAO extends IGenericDAO<EEvidence, Integer> {

	public List<EEvidence> loadEvidencesFromProcessInstance(String refInstanceInProcessEngine);

	public List<EEvidence> findByProcessInstanceAndTaskReference(String refInstance, String taskRef);
}
