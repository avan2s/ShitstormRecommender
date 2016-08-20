package shitstorm.interfaces;

import shitstorm.persistence.entities.ENodeGroup;
import shitstorm.persistence.entities.EProcess;
import shitstorm.persistence.entities.ETask;

public interface ITaskDAO extends IGenericDAO<ETask, Integer>{
	public ETask create(String taskName, String refInProcessEngine, EProcess process, ENodeGroup nodeGroup);
}
