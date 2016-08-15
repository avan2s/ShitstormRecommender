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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((decisionDate == null) ? 0 : decisionDate.hashCode());
		result = prime * result + ((decisionName == null) ? 0 : decisionName.hashCode());
		result = prime * result + ((evidence == null) ? 0 : evidence.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ETakenDecision other = (ETakenDecision) obj;
		if (decisionDate == null) {
			if (other.decisionDate != null)
				return false;
		} else if (!decisionDate.equals(other.decisionDate))
			return false;
		if (decisionName == null) {
			if (other.decisionName != null)
				return false;
		} else if (!decisionName.equals(other.decisionName))
			return false;
		if (evidence == null) {
			if (other.evidence != null)
				return false;
		} else if (!evidence.equals(other.evidence))
			return false;
		return true;
	}

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