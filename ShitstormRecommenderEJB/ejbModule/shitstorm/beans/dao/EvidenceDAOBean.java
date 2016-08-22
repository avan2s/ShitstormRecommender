package shitstorm.beans.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import shitstorm.interfaces.local.IEvidenceDAO;
import shitstorm.persistence.entities.EEvidence;
import shitstorm.pojos.GenericDAOImpl;

@Stateless
@Local(IEvidenceDAO.class)
public class EvidenceDAOBean extends GenericDAOImpl<EEvidence, Integer> implements IEvidenceDAO {

	@Override
	public List<EEvidence> loadEvidencesFromProcessInstance(String refInstanceInProcessEngine) {
		return this.em.createNamedQuery(EEvidence.QUERY_GET_BY_PROCESSINSTANCE_NEWEST, EEvidence.class)
				.setParameter("refInstance", refInstanceInProcessEngine).getResultList();

	}

	public List<EEvidence> findByProcessInstanceAndTaskReference(String refInstance, String taskRef) {
		return this.em.createNamedQuery(EEvidence.QUERY_GET_BY_PROCESSINSTANCE_AND_TASKREF, EEvidence.class)
				.setParameter("refInstance", refInstance).setParameter("refTask", taskRef).getResultList();
	}

}
