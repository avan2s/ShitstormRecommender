package shitstorm.interfaces;

import shitstorm.persistence.entities.EGoal;

public interface IGoalDAO extends IGenericDAO<EGoal, Integer>{
	public EGoal findByGoalFigure(String goalFigure);
}
