package shitstorm.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the processvariable database table.
 * 
 */
@Entity
@Table(name="processvariable")
@NamedQuery(name="EProcessvariable.findAll", query="SELECT e FROM EProcessvariable e")
public class EProcessvariable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idProcessvariable;

	@Column(name="ref_in_processengine")
	private String refInProcessengine;

	@Column(name="variable_name")
	private String variableName;

	//bi-directional many-to-one association to ENodeSet
	//@OneToMany(mappedBy="processvariable")
	@OneToOne(mappedBy="processvariable")
	private ENodeSet nodeSet;

	//bi-directional many-to-one association to EProcess
	@ManyToOne
	@JoinColumn(name="process_id")
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

	public ENodeSet getNodeSet() {
		return this.nodeSet;
	}

	public void setNodeSets(ENodeSet nodeSet) {
		this.nodeSet = nodeSet;
	}

//	public ENodeSet addNodeSet(ENodeSet nodeSet) {
//		getNodeSets().add(nodeSet);
//		nodeSet.setProcessvariable(this);
//
//		return nodeSet;
//	}
//
//	public ENodeSet removeNodeSet(ENodeSet nodeSet) {
//		getNodeSets().remove(nodeSet);
//		nodeSet.setProcessvariable(null);
//
//		return nodeSet;
//	}

	public EProcess getProcess() {
		return this.process;
	}

	public void setProcess(EProcess process) {
		this.process = process;
	}

}