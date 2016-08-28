package recommender.persistence.entities;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import kip.enums.KipGoalEffect;

/**
 * The persistent class for the goal database table.
 * 
 */
@Entity
@Table(name = "goal")
@NamedQueries({ @NamedQuery(name = EGoal.QUERY_GETALL, query = "SELECT e FROM EGoal e"),
		@NamedQuery(name = EGoal.QUERY_GET_BY_GOAL_FIGURE, query = "SELECT g FROM EGoal g WHERE g.goalFigure=:figure") })
public class EGoal implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_GETALL = "EGoal.findAll";
	public static final String QUERY_GET_BY_GOAL_FIGURE = "EGoal.GET_BY_GOAL_TARGET";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idGoal;

	@Enumerated(EnumType.STRING)
	private KipGoalEffect goalEffect;

	private String goalFigure;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "node_group_id")
	private ENodeGroup nodeGroup;

	@OneToMany(mappedBy = "goal")
	List<ETakenDecisionEffect> takenDecisionEffects;

	// bi-directional many-to-many association to EProcess
	@ManyToMany(mappedBy = "goals", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private List<EProcess> processes;

	public EGoal() {
		this.processes = new ArrayList<>();
		this.takenDecisionEffects = new ArrayList<>();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EGoal other = (EGoal) obj;
		if (idGoal != other.idGoal)
			return false;
		return true;
	}

	public boolean isValidForRecommendation() {
		if (this.goalEffect == null || this.goalFigure == null || this.nodeGroup == null || this.processes == null) {
			return false;
		}
		return true;
	}

	public int getIdGoal() {
		return this.idGoal;
	}

	public void setIdGoal(int idGoal) {
		this.idGoal = idGoal;
	}

	public KipGoalEffect getGoalEffect() {
		return this.goalEffect;
	}

	public void setGoalEffect(KipGoalEffect goalEffect) {
		this.goalEffect = goalEffect;
	}

	public String getGoalFigure() {
		return this.goalFigure;
	}

	public void setGoalFigure(String goalFigure) {
		this.goalFigure = goalFigure;
	}

	public ENodeGroup getNodeGroup() {
		return this.nodeGroup;
	}

	public void setNodeGroup(ENodeGroup nodeGroup) {
		this.nodeGroup = nodeGroup;
	}

	public List<EProcess> getProcesses() {
		return this.processes;
	}

	public void setProcesses(List<EProcess> processes) {
		this.processes = processes;
	}

	public List<ETakenDecisionEffect> getTakenDecisionEffects() {
		return takenDecisionEffects;
	}

	public void setTakenDecisionEffects(List<ETakenDecisionEffect> takenDecisionEffects) {
		this.takenDecisionEffects = takenDecisionEffects;
	}

}