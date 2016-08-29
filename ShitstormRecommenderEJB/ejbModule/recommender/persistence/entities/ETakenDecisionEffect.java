package recommender.persistence.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import recommender.enums.ObservedEffect;

/**
 * The persistent class for the taken_decision_effect database table.
 * 
 */
@Entity
@Table(name = "taken_decision_effect")
@NamedQuery(name = "ETakenDecisionEffect.findAll", query = "SELECT e FROM ETakenDecisionEffect e")
public class ETakenDecisionEffect implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTakenDecisionEffect;

	@Column(name = "decision_effect")
	@Enumerated(EnumType.STRING)
	private ObservedEffect decisionEffect;

	@ManyToOne
	@JoinColumn(name = "goal_id")
	private EGoal goal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "observed_date", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Calendar observedDate;

	// bi-directional many-to-one association to ETakenDecision
	@OneToMany(mappedBy = "takenDecisionEffect", cascade={CascadeType.MERGE,CascadeType.PERSIST})
	private List<ETakenDecision> takenDecisions;

	public ETakenDecisionEffect() {
		this.takenDecisions = new ArrayList<>();
	}

	public int getIdTakenDecisionEffect() {
		return this.idTakenDecisionEffect;
	}

	public void setIdTakenDecisionEffect(int idTakenDecisionEffect) {
		this.idTakenDecisionEffect = idTakenDecisionEffect;
	}

	public ObservedEffect getDecisionEffect() {
		return this.decisionEffect;
	}

	public void setDecisionEffect(ObservedEffect decisionEffect) {
		this.decisionEffect = decisionEffect;
	}

	public Calendar getObservedDate() {
		return this.observedDate;
	}

	public void setObservedDate(Calendar observedDate) {
		this.observedDate = observedDate;
	}

	public List<ETakenDecision> getTakenDecisions() {
		return this.takenDecisions;
	}

	public void setTakenDecisions(List<ETakenDecision> takenDecisions) {
		this.takenDecisions = takenDecisions;
	}

	public EGoal getGoal() {
		return goal;
	}

	public void setGoal(EGoal goal) {
		this.goal = goal;
	}

	public ETakenDecision addTakenDecision(ETakenDecision takenDecision) {
		getTakenDecisions().add(takenDecision);
		takenDecision.setTakenDecisionEffect(this);

		return takenDecision;
	}

	public ETakenDecision removeTakenDecision(ETakenDecision takenDecision) {
		getTakenDecisions().remove(takenDecision);
		takenDecision.setTakenDecisionEffect(null);

		return takenDecision;
	}

}