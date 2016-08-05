package shitstorm.persistence.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import shitstorm.enums.GoalEffect;


/**
 * The persistent class for the goal database table.
 * 
 */
@Entity
@Table(name="goal")
@NamedQuery(name="EGoal.findAll", query="SELECT e FROM EGoal e")
public class EGoal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idGoal;

	@Enumerated(EnumType.STRING)
	private GoalEffect goalEffect;

	private String goalFigure;

	//bi-directional many-to-one association to ENodeSet
//	@OneToMany(mappedBy="goal")
//	private List<ENodeSet> nodeSets;
	@OneToOne(cascade=CascadeType.ALL, mappedBy="goal", fetch=FetchType.EAGER)
	private ENodeSet nodeSet;

	//bi-directional many-to-many association to EProcess
	@ManyToMany(mappedBy="goals")
	private List<EProcess> processes;

	public EGoal() {
		this.processes = new ArrayList<>();
	}

	public int getIdGoal() {
		return this.idGoal;
	}

	public void setIdGoal(int idGoal) {
		this.idGoal = idGoal;
	}

	public GoalEffect getGoalEffect() {
		return this.goalEffect;
	}

	public void setGoalEffect(GoalEffect goalEffect) {
		this.goalEffect = goalEffect;
	}

	public String getGoalFigure() {
		return this.goalFigure;
	}

	public void setGoalFigure(String goalFigure) {
		this.goalFigure = goalFigure;
	}

//	public List<ENodeSet> getNodeSets() {
//		return this.nodeSets;
//	}
//
//	public void setNodeSets(List<ENodeSet> nodeSets) {
//		this.nodeSets = nodeSets;
//	}
	public ENodeSet getNodeSet() {
		return this.nodeSet;
	}

	public void setNodeSet(ENodeSet nodeSet) {
		this.nodeSet = nodeSet;
	}

//	public ENodeSet addNodeSet(ENodeSet nodeSet) {
//		getNodeSets().add(nodeSet);
//		nodeSet.setGoal(this);
//
//		return nodeSet;
//	}
//
//	public ENodeSet removeNodeSet(ENodeSet nodeSet) {
//		getNodeSets().remove(nodeSet);
//		nodeSet.setGoal(null);
//
//		return nodeSet;
//	}

	public List<EProcess> getProcesses() {
		return this.processes;
	}

	public void setProcesses(List<EProcess> processes) {
		this.processes = processes;
	}

}