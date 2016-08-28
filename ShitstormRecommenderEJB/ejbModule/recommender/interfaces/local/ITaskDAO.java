package recommender.interfaces.local;

import recommender.interfaces.IGenericDAO;
import recommender.persistence.entities.ENodeGroup;
import recommender.persistence.entities.EProcess;
import recommender.persistence.entities.ETask;

public interface ITaskDAO extends IGenericDAO<ETask, Integer> {

	public ETask create(String taskName, String refInProcessEngine, EProcess process, ENodeGroup nodeGroup);

	public ETask findByProcessAndTaskReference(String refProcess, String taskRef);
}
