package shitstorm.interfaces.local;

import shitstorm.interfaces.IGenericDAO;
import shitstorm.persistence.entities.EProcessinstance;

public interface IProcessInstanceDAO extends IGenericDAO<EProcessinstance, Integer> {
	public EProcessinstance findByRefInProcessEngine(String refInstanceInProcessEngine);
}
