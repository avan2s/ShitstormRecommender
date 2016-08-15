package shitstorm.interfaces;

import shitstorm.persistence.entities.ENodeGroup;

public interface INodeGroupDAO extends IGenericDAO<ENodeGroup, Integer>{
	public ENodeGroup findByAbbreviationInProcess(String refProcess, String nodeAbbreviation);
}
