package recommender.interfaces.local;

import recommender.interfaces.IGenericDAO;
import recommender.persistence.entities.ENode;
import recommender.persistence.entities.ENodeGroup;
import recommender.persistence.entities.EProcess;

public interface INodeDAO extends IGenericDAO<ENode, Integer> {
	public ENode create(String nodeId, int period, ENodeGroup nodeGroup, EProcess process);

	public ENode findByNodeName(String nodeName);

	public ENode findByProcessAndVariableRef(String refProcess, String refVariable, int period);

	public ENode findByProcessAndTaskRef(String refProcess, String refTask, int period);

	public ENode findByProcessAndAbbreviationAndPeriod(String refProcess, String abbreviation, int period);
}
