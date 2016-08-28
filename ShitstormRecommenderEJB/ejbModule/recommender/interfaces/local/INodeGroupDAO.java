package recommender.interfaces.local;

import recommender.interfaces.IGenericDAO;
import recommender.persistence.entities.ENodeGroup;

public interface INodeGroupDAO extends IGenericDAO<ENodeGroup, Integer>{
	public ENodeGroup findByAbbreviationInProcess(String refProcess, String nodeAbbreviation);
}
