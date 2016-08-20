package shitstorm.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import shitstorm.interfaces.ITaskDAO;
import shitstorm.persistence.entities.ENodeGroup;
import shitstorm.persistence.entities.EProcess;
import shitstorm.persistence.entities.ETask;
import shitstorm.pojos.GenericDAOImpl;

@Stateless
@Local(ITaskDAO.class)
public class TaskDAOBean extends GenericDAOImpl<ETask, Integer> implements ITaskDAO {

	@Override
	public ETask create(String taskName, String refInProcessEngine, EProcess process, ENodeGroup nodeGroup) {
		ETask task = new ETask();
		task.setNodeGroup(nodeGroup);
		task.setProcess(process);
		task.setRefInProcessengine(refInProcessEngine);
		task.setTaskName(taskName);
		return this.create(task);
	}

}
