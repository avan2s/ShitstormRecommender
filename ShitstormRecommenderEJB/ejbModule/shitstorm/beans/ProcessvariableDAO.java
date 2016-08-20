package shitstorm.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import shitstorm.enums.VariableType;
import shitstorm.interfaces.IProcessvariableDAO;
import shitstorm.persistence.entities.ENodeGroup;
import shitstorm.persistence.entities.EProcess;
import shitstorm.persistence.entities.EProcessvariable;
import shitstorm.pojos.GenericDAOImpl;

@Stateless
@Local(IProcessvariableDAO.class)
public class ProcessvariableDAO extends GenericDAOImpl<EProcessvariable, Integer> implements IProcessvariableDAO {
	public EProcessvariable create(String variableName, String refInProcessengine, VariableType variableType,
			EProcess process, ENodeGroup nodeGroup) {
		EProcessvariable var = new EProcessvariable();
		var.setNodeGroup(nodeGroup);
		var.setProcess(process);
		var.setVariableName(variableName);
		var.setRefInProcessengine(refInProcessengine);
		var.setVariableType(variableType);
		return this.create(var);
	}
}
