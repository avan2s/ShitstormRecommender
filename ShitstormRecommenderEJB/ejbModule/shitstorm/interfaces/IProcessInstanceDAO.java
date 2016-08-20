package shitstorm.interfaces;

import shitstorm.persistence.entities.EProcessinstance;

public interface IProcessInstanceDAO extends IGenericDAO<EProcessinstance, Integer> {
	public EProcessinstance findByRefInProcessEngine(String refInstanceInProcessEngine);
}
