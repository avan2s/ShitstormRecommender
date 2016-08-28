package recommender.interfaces.local;

import recommender.interfaces.IGenericDAO;
import recommender.persistence.entities.EProcessinstance;

public interface IProcessInstanceDAO extends IGenericDAO<EProcessinstance, Integer> {
	public EProcessinstance findByRefInProcessEngine(String refInstanceInProcessEngine);
}
