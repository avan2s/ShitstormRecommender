package recommender.interfaces.local;

import java.util.List;

import recommender.interfaces.IGenericDAO;
import recommender.persistence.entities.EEvidence;

public interface IEvidenceDAO extends IGenericDAO<EEvidence, Integer> {

	public List<EEvidence> loadEvidencesFromProcessInstance(String refInstanceInProcessEngine);

	public List<EEvidence> findByProcessInstanceAndTaskReference(String refInstance, String taskRef);
}
