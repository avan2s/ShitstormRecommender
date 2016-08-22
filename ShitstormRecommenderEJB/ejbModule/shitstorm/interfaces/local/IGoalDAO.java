package shitstorm.interfaces.local;

import kip.enums.KipGoalEffect;
import shitstorm.interfaces.IGenericDAO;
import shitstorm.persistence.entities.EGoal;
import shitstorm.persistence.entities.ENodeGroup;
import shitstorm.persistence.entities.EProcess;

public interface IGoalDAO extends IGenericDAO<EGoal, Integer>{
	public EGoal findByGoalFigure(String goalFigure);
	public EGoal create(String goalFigure, KipGoalEffect goalEffect);
	public EGoal create(String goalFigure, KipGoalEffect goalEffect, EProcess process);
	public EGoal create(String goalFigure, KipGoalEffect goalEffect, EProcess process, ENodeGroup nodeGroup);
}
