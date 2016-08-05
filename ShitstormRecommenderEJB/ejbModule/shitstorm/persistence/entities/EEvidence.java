package shitstorm.persistence.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the evidence database table.
 * 
 */
@Entity
@Table(name = "evidence")
@NamedQuery(name = "EEvidence.findAll", query = "SELECT e FROM EEvidence e")
public class EEvidence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEvidence;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar created;

	private String value;

	// bi-directional many-to-one association to ENode
	@ManyToOne
	@JoinColumn(name = "node_id")
	private ENode node;

	// bi-directional one-to-one association to ETakenDecision
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "evidence", fetch = FetchType.EAGER)
	private ETakenDecision takenDecision;

	public EEvidence() {
	}

	public int getIdEvidence() {
		return this.idEvidence;
	}

	public void setIdEvidence(int idEvidence) {
		this.idEvidence = idEvidence;
	}

	public Calendar getCreated() {
		return this.created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ENode getNode() {
		return this.node;
	}

	public void setNode(ENode node) {
		this.node = node;
	}

	public ETakenDecision getTakenDecision() {
		return this.takenDecision;
	}

	public void setTakenDecision(ETakenDecision takenDecision) {
		this.takenDecision = takenDecision;
	}

}