package shitstorm.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import shitstorm.interfaces.IProcessDAO;
import shitstorm.persistence.entities.EProcess;
import shitstorm.pojos.GenericDAOImpl;

@Stateless
@Local(IProcessDAO.class)
public class ProcessDAOBean extends GenericDAOImpl<EProcess, Integer> implements IProcessDAO{

	@Override
	public EProcess findByReferenceInProcessEngine(String reference) {
		try {
			return this.em.createNamedQuery(EProcess.QUERY_GET_BY_REF, EProcess.class).setParameter("ref", reference).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
