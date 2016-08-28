package recommender.beans.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import kip.enums.KipGoalEffect;
import recommender.interfaces.local.IGoalDAO;
import recommender.persistence.entities.EGoal;
import recommender.persistence.entities.ENodeGroup;
import recommender.persistence.entities.EProcess;
import recommender.pojos.GenericDAOImpl;

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

	@Override
	public EGoal create(String goalFigure, KipGoalEffect goalEffect) {
		EGoal goal = new EGoal();
		goal.setGoalEffect(goalEffect);
		goal.setGoalFigure(goalFigure);
		return this.create(goal);
	}

	@Override
	public EGoal create(String goalFigure, KipGoalEffect goalEffect, EProcess process) {
		EGoal goal = new EGoal();
		goal.setGoalEffect(goalEffect);
		goal.setGoalFigure(goalFigure);
		goal.getProcesses().add(process);
		return this.create(goal);
	}

	@Override
	public EGoal create(String goalFigure, KipGoalEffect goalEffect, EProcess process, ENodeGroup nodeGroup) {
		EGoal goal = new EGoal();
		goal.setGoalEffect(goalEffect);
		goal.setGoalFigure(goalFigure);
		goal.setNodeGroup(nodeGroup);
		goal.getProcesses().add(process);
		process.getGoals().add(goal);
		return this.create(goal);
	}

}
