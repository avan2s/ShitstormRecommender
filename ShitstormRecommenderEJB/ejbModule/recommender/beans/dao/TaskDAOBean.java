package recommender.beans.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import recommender.interfaces.local.ITaskDAO;
import recommender.persistence.entities.ENodeGroup;
import recommender.persistence.entities.EProcess;
import recommender.persistence.entities.ETask;
import recommender.pojos.GenericDAOImpl;

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

	@Override
	public ETask findByProcessAndTaskReference(String refProcess, String taskRef) {
		try {
			return this.em.createNamedQuery(ETask.QUERY_GET_BY_PROCESS_AND_TASKREF, ETask.class)
					.setParameter("refProcess", refProcess).setParameter("taskRef", taskRef).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
