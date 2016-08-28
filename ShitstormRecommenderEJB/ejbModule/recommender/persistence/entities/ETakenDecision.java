package recommender.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the taken_decision database table.
 * 
 */
@Entity
@Table(name = "taken_decision")
@NamedQuery(name = "ETakenDecision.findAll", query = "SELECT e FROM ETakenDecision e")
public class ETakenDecision implements Serializable {
	private static final long serialVersionUID = 1L;

	// bi-directional one-to-one association to EEvidence
	@OneToOne(fetch = FetchType.EAGER)
	@Id
	@JoinColumn(name = "idTakenDecision")
	private EEvidence evidence;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "task_id")
	private ETask task;

	// bi-directional many-to-one association to ETakenDecisionEffect
	@ManyToOne
	@JoinColumn(name = "decision_effect_id")
	private ETakenDecisionEffect takenDecisionEffect;

	public ETakenDecision() {
	}

	public EEvidence getEvidence() {
		return this.evidence;
	}

	public void setEvidence(EEvidence evidence) {
		this.evidence = evidence;
	}

	public ETakenDecisionEffect getTakenDecisionEffect() {
		return this.takenDecisionEffect;
	}

	public void setTakenDecisionEffect(ETakenDecisionEffect takenDecisionEffect) {
		this.takenDecisionEffect = takenDecisionEffect;
	}

	public ETask getTask() {
		return task;
	}

	public void setTask(ETask task) {
		this.task = task;
	}

}