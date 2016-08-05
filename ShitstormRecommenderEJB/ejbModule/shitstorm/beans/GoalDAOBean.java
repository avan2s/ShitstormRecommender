package shitstorm.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import shitstorm.interfaces.IGoalDAO;
import shitstorm.persistence.entities.EGoal;
import shitstorm.pojos.GenericDAOImpl;

@Stateless
@Local(IGoalDAO.class)
public class GoalDAOBean extends GenericDAOImpl<EGoal, Integer> implements IGoalDAO {

	@Override
	public EGoal findByGoal() {
		// TODO Auto-generated method stub
		return null;
	}

}
