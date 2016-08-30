package recommender.interfaces.local;

import kip.enums.KipGoalEffect;
import recommender.interfaces.IGenericDAO;
import recommender.persistence.entities.EGoal;
import recommender.persistence.entities.ENodeGroup;
import recommender.persistence.entities.EProcess;

public interface IGoalDAO extends IGenericDAO<EGoal, Integer> {
	public EGoal findByGoalFigure(String goalFigure);

	EGoal create(String goalFigure, String goalUnit, KipGoalEffect goalEffect, EProcess process, ENodeGroup nodeGroup);

	EGoal create(String goalFigure, String goalUnit, KipGoalEffect goalEffect, EProcess process);

	EGoal create(String goalFigure, String goalUnit, KipGoalEffect goalEffect);

}
