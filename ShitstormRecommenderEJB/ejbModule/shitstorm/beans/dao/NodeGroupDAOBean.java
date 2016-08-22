package shitstorm.beans.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import shitstorm.interfaces.local.INodeGroupDAO;
import shitstorm.persistence.entities.ENodeGroup;
import shitstorm.pojos.GenericDAOImpl;

@Stateless
@Local(INodeGroupDAO.class)
public class NodeGroupDAOBean extends GenericDAOImpl<ENodeGroup, Integer> implements INodeGroupDAO {

	@Override
	public ENodeGroup findByAbbreviationInProcess(String refProcess, String nodeAbbreviation) {
		try {
			return this.em.createNamedQuery(ENodeGroup.QUERY_GET_BY_ABBREVIATION_AND_PROCESS, ENodeGroup.class)
					.setParameter("abbreviation", nodeAbbreviation).setParameter("refProcess", refProcess)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}

}
