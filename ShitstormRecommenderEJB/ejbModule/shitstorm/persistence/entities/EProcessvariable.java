package shitstorm.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import shitstorm.enums.VariableType;

/**
 * The persistent class for the processvariable database table.
 * 
 */
@Entity
@Table(name = "processvariable")
@NamedQuery(name = "EProcessvariable.findAll", query = "SELECT e FROM EProcessvariable e")
public class EProcessvariable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProcessvariable;

	@Column(name = "ref_in_processengine")
	private String refInProcessengine;

	@Column(name = "variable_name")
	private String variableName;

	@Column(name = "variable_type")
	@Enumerated(EnumType.STRING)
	private VariableType variableType;

	@OneToOne
	@JoinColumn(name="node_group_id")
	private ENodeGroup nodeGroup;

	// bi-directional many-to-one association to EProcess
	@ManyToOne
	@JoinColumn(name = "process_id")
	private EProcess process;

	public EProcessvariable() {
	}

	public int getIdProcessvariable() {
		return this.idProcessvariable;
	}

	public void setIdProcessvariable(int idProcessvariable) {
		this.idProcessvariable = idProcessvariable;
	}

	public String getRefInProcessengine() {
		return this.refInProcessengine;
	}

	public void setRefInProcessengine(String refInProcessengine) {
		this.refInProcessengine = refInProcessengine;
	}

	public String getVariableName() {
		return this.variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public ENodeGroup getNodeGroup() {
		return this.nodeGroup;
	}

	// public ENodeSet addNodeSet(ENodeSet nodeSet) {
	// getNodeSets().add(nodeSet);
	// nodeSet.setProcessvariable(this);
	//
	// return nodeSet;
	// }
	//
	// public ENodeSet removeNodeSet(ENodeSet nodeSet) {
	// getNodeSets().remove(nodeSet);
	// nodeSet.setProcessvariable(null);
	//
	// return nodeSet;
	// }

	public VariableType getVariableType() {
		return variableType;
	}

	public void setVariableType(VariableType variableType) {
		this.variableType = variableType;
	}

	public void setNodeGroup(ENodeGroup nodeGroup) {
		this.nodeGroup = nodeGroup;
	}

	public EProcess getProcess() {
		return this.process;
	}

	public void setProcess(EProcess process) {
		this.process = process;
	}

}