package recommender.beans.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import recommender.interfaces.local.INodeDAO;
import recommender.persistence.entities.ENode;
import recommender.persistence.entities.ENodeGroup;
import recommender.persistence.entities.EProcess;
import recommender.pojos.GenericDAOImpl;

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

	@Override
	public ENode findByProcessAndAbbreviationAndPeriod(String refProcess, String abbreviation, int period) {
		try {
			return this.em.createNamedQuery(ENode.QUERY_GET_BY_PROCESS_AND_ABBREVIATION_AND_PERIOD, ENode.class)
					.setParameter("refProcess", refProcess).setParameter("abbreviation", abbreviation)
					.setParameter("period", period).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public ENode findByProcessAndTaskRef(String refProcess, String refTask, int period) {
		try {
			return this.em.createNamedQuery(ENode.QUERY_GET_BY_PROCESS_AND_TASK_AND_PERIOD, ENode.class)
					.setParameter("refTask", refTask).setParameter("refProcess", refProcess)
					.setParameter("period", period).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
