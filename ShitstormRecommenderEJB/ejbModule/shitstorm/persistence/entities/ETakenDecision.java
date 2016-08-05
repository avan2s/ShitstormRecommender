package shitstorm.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the taken_decision database table.
 * 
 */
@Entity
@Table(name="taken_decision")
@NamedQuery(name="ETakenDecision.findAll", query="SELECT e FROM ETakenDecision e")
public class ETakenDecision implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
//	private int idTakenDecision;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="decision_date")
	private Date decisionDate;

	@Column(name="decision_name")
	private String decisionName;

	//bi-directional one-to-one association to EEvidence
	@OneToOne(fetch=FetchType.EAGER)
	@Id
	@JoinColumn(name="idTakenDecision")
	private EEvidence evidence;

	//bi-directional many-to-one association to ETakenDecisionEffect
	@ManyToOne
	@JoinColumn(name="decision_effect_id")
	private ETakenDecisionEffect takenDecisionEffect;

	public ETakenDecision() {
	}

//	public int getIdTakenDecision() {
//		return this.idTakenDecision;
//	}
//
//	public void setIdTakenDecision(int idTakenDecision) {
//		this.idTakenDecision = idTakenDecision;
//	}

	public Date getDecisionDate() {
		return this.decisionDate;
	}

	public void setDecisionDate(Date decisionDate) {
		this.decisionDate = decisionDate;
	}

	public String getDecisionName() {
		return this.decisionName;
	}

	public void setDecisionName(String decisionName) {
		this.decisionName = decisionName;
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

}