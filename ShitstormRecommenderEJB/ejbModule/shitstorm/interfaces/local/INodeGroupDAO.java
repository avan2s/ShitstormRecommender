package shitstorm.interfaces.local;

import shitstorm.interfaces.IGenericDAO;
import shitstorm.persistence.entities.ENodeGroup;

public interface INodeGroupDAO extends IGenericDAO<ENodeGroup, Integer>{
	public ENodeGroup findByAbbreviationInProcess(String refProcess, String nodeAbbreviation);
}
