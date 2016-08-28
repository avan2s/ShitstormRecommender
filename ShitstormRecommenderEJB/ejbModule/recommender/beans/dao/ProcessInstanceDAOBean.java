package recommender.beans.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import recommender.interfaces.local.IProcessInstanceDAO;
import recommender.persistence.entities.EProcessinstance;
import recommender.pojos.GenericDAOImpl;

@Stateless
@Local(IProcessInstanceDAO.class)
public class ProcessInstanceDAOBean extends GenericDAOImpl<EProcessinstance, Integer> implements IProcessInstanceDAO {

	@Override
	public EProcessinstance findByRefInProcessEngine(String refInstanceInProcessEngine) {
		try {
			return this.em.createNamedQuery(EProcessinstance.GET_BY_PROCESSINSTANCE_REF, EProcessinstance.class)
					.setParameter("refInstance", refInstanceInProcessEngine).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
