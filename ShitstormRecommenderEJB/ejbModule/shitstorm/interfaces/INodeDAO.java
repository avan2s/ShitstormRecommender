package shitstorm.interfaces;

import shitstorm.persistence.entities.ENode;
import shitstorm.persistence.entities.ENodeGroup;
import shitstorm.persistence.entities.EProcess;

public interface INodeDAO extends IGenericDAO<ENode, Integer>{
	public ENode create(String nodeId, int period, ENodeGroup nodeGroup, EProcess process);
	public ENode findByNodeName(String nodeName);
	public ENode findByProcessAndVariableRef(String refProcess, String refVariable);
	public ENode findByProcessAndTaskRef(String refProcess, String refTask);
	public ENode find(String refProcess, String refTask, int period);
}
