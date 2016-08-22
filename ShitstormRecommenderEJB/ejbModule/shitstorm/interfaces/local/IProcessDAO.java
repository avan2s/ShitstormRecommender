package shitstorm.interfaces.local;

import shitstorm.interfaces.IGenericDAO;
import shitstorm.persistence.entities.EProcess;

public interface IProcessDAO extends IGenericDAO<EProcess, Integer>{
	public EProcess findByReferenceInProcessEngine(String reference);
}
