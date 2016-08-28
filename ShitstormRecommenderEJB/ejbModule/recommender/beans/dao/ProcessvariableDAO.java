package recommender.beans.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import recommender.enums.VariableType;
import recommender.interfaces.local.IProcessvariableDAO;
import recommender.persistence.entities.ENodeGroup;
import recommender.persistence.entities.EProcess;
import recommender.persistence.entities.EProcessvariable;
import recommender.pojos.GenericDAOImpl;

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
