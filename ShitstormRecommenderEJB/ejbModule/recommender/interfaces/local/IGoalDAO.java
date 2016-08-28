package recommender.interfaces.local;

import kip.enums.KipGoalEffect;
import recommender.interfaces.IGenericDAO;
import recommender.persistence.entities.EGoal;
import recommender.persistence.entities.ENodeGroup;
import recommender.persistence.entities.EProcess;

public interface IGoalDAO extends IGenericDAO<EGoal, Integer>{
	public EGoal findByGoalFigure(String goalFigure);
	public EGoal create(String goalFigure, KipGoalEffect goalEffect);
	public EGoal create(String goalFigure, KipGoalEffect goalEffect, EProcess process);
	public EGoal create(String goalFigure, KipGoalEffect goalEffect, EProcess process, ENodeGroup nodeGroup);
}
