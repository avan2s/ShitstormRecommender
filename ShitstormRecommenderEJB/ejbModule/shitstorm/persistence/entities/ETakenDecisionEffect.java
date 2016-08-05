package shitstorm.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the taken_decision_effect database table.
 * 
 */
@Entity
@Table(name="taken_decision_effect")
@NamedQuery(name="ETakenDecisionEffect.findAll", query="SELECT e FROM ETakenDecisionEffect e")
public class ETakenDecisionEffect implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTakenDecisionEffect;

	@Column(name="decision_effect")
	private String decisionEffect;

	@Column(name="goal_id")
	private int goalId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="observed_date")
	private Date observedDate;

	//bi-directional many-to-one association to ETakenDecision
	@OneToMany(mappedBy="takenDecisionEffect")
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

	public String getDecisionEffect() {
		return this.decisionEffect;
	}

	public void setDecisionEffect(String decisionEffect) {
		this.decisionEffect = decisionEffect;
	}

	public int getGoalId() {
		return this.goalId;
	}

	public void setGoalId(int goalId) {
		this.goalId = goalId;
	}

	public Date getObservedDate() {
		return this.observedDate;
	}

	public void setObservedDate(Date observedDate) {
		this.observedDate = observedDate;
	}

	public List<ETakenDecision> getTakenDecisions() {
		return this.takenDecisions;
	}

	public void setTakenDecisions(List<ETakenDecision> takenDecisions) {
		this.takenDecisions = takenDecisions;
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