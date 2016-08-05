package shitstorm.interfaces;

import shitstorm.persistence.entities.EProcess;

public interface IProcessDAO extends IGenericDAO<EProcess, Integer>{
	public EProcess findByReferenceInProcessEngine(String reference); 
}
