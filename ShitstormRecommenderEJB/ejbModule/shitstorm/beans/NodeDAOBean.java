package shitstorm.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import shitstorm.interfaces.INodeDAO;
import shitstorm.persistence.entities.ENode;
import shitstorm.persistence.entities.ENodeGroup;
import shitstorm.persistence.entities.EProcess;
import shitstorm.pojos.GenericDAOImpl;

@Stateless
@Local(INodeDAO.class)
public class NodeDAOBean extends GenericDAOImpl<ENode, Integer> implements INodeDAO {

	@Override
	public ENode create(String nodeId, int period, ENodeGroup nodeGroup, EProcess process) {
		ENode node = new ENode();
		node.setNodeName(nodeId);
		node.setPeriod(period);
		node.setNodeGroup(nodeGroup);
		node.setProcess(process);
		return this.create(node);
	}

	@Override
	public ENode findByNodeName(String nodeName) {
		try {
			return this.em.createNamedQuery(ENode.QUERY_GET_BY_NODENAME, ENode.class).setParameter("nodeName", nodeName)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public ENode findByProcessAndVariableRef(String refProcess, String refVariable, int period) {
		try {
			return this.em.createNamedQuery(ENode.QUERY_GET_BY_PROCESS_AND_VARIABLE_AND_PERIOD, ENode.class)
					.setParameter("refVariable", refVariable).setParameter("refProcess", refProcess)
					.setParameter("period", period).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

//	@Override
//	public ENode findByProcessAndTaskRef(String refProcess, String refTask) {
//		try {
//			return this.em.createNamedQuery(ENode.QUERY_GET_BY_PROCESS_AND_TASK, ENode.class)
//					.setParameter("refTask", refTask).setParameter("refProcess", refProcess).getSingleResult();
//		} catch (NoResultException e) {
//			return null;
//		}
//	}

	@Override
	public ENode findByProcessAndTaskRef(String refProcess, String refTask, int period) {
		try {
			return this.em.createNamedQuery(ENode.QUERY_GET_BY_PROCESS_AND_TASK_AND_PERIOD, ENode.class)
					.setParameter("refTask ", refTask).setParameter("refProcess", refProcess)
					.setParameter("period", period).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
