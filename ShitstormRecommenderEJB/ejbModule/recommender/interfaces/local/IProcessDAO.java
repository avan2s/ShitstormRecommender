package recommender.interfaces.local;

import recommender.interfaces.IGenericDAO;
import recommender.persistence.entities.EProcess;

public interface IProcessDAO extends IGenericDAO<EProcess, Integer>{
	public EProcess findByReferenceInProcessEngine(String reference);
}
