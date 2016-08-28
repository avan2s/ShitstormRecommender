package recommender.interfaces.local;

import recommender.enums.VariableType;
import recommender.interfaces.IGenericDAO;
import recommender.persistence.entities.ENodeGroup;
import recommender.persistence.entities.EProcess;
import recommender.persistence.entities.EProcessvariable;

public interface IProcessvariableDAO extends IGenericDAO<EProcessvariable, Integer> {
	public EProcessvariable create(String variableName, String refInProcessengine, VariableType variableType,
			EProcess process, ENodeGroup nodeGroup);
}
