package shitstorm.beans;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import shitstorm.interfaces.IEvidenceLoader;
import shitstorm.persistence.entities.EEvidence;
import shitstorm.pojos.GenericDAOImpl;

@Stateless
@Local(IEvidenceLoader.class)
public class EvidenceLoader extends GenericDAOImpl<EEvidence, Integer> implements IEvidenceLoader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * shitstorm.beans.IEvidenceLoader#loadEvidencesFromProcessInstance(java.
	 * lang.String)
	 */
	@Override
	public List<EEvidence> loadEvidencesFromProcessInstance(String refInProcessEngine) {
		return this.em.createNamedQuery(EEvidence.QUERY_GET_BY_PROCESSINSTANCE_NEWEST, EEvidence.class)
				.setParameter("refInstance", refInProcessEngine).getResultList();

	}
}
