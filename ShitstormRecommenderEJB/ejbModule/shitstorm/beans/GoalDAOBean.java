package shitstorm.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import shitstorm.interfaces.IGoalDAO;
import shitstorm.persistence.entities.EGoal;
import shitstorm.pojos.GenericDAOImpl;

@Stateless
@Local(IGoalDAO.class)
public class GoalDAOBean extends GenericDAOImpl<EGoal, Integer> implements IGoalDAO {

	@Override
	public EGoal findByGoalFigure(String goalFigure) {
		try {
			return this.em.createNamedQuery(EGoal.QUERY_GET_BY_GOAL_FIGURE, EGoal.class)
					.setParameter("figure", goalFigure).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
