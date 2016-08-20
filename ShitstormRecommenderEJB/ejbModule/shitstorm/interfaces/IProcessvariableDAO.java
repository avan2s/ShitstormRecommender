package shitstorm.interfaces;

import shitstorm.enums.VariableType;
import shitstorm.persistence.entities.ENodeGroup;
import shitstorm.persistence.entities.EProcess;
import shitstorm.persistence.entities.EProcessvariable;

public interface IProcessvariableDAO extends IGenericDAO<EProcessvariable, Integer> {
	public EProcessvariable create(String variableName, String refInProcessengine, VariableType variableType,
			EProcess process, ENodeGroup nodeGroup);
}
