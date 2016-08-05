package shitstorm.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the evidence database table.
 * 
 */
@Entity
@Table(name="evidence")
@NamedQuery(name="EEvidence.findAll", query="SELECT e FROM EEvidence e")
public class EEvidence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEvidence;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private String value;

	//bi-directional many-to-one association to ENode
	@ManyToOne
	@JoinColumn(name="node_id")
	private ENode node;

	//bi-directional many-to-one association to EProcessinstance
	@ManyToOne
	@JoinColumn(name="processinstance_id")
	private EProcessinstance processinstance;

	//bi-directional one-to-one association to ETakenDecision
	@OneToOne(cascade= CascadeType.ALL, mappedBy="evidence", fetch=FetchType.EAGER)
	private ETakenDecision takenDecision;

	public EEvidence() {
	}

	public int getIdEvidence() {
		return this.idEvidence;
	}

	public void setIdEvidence(int idEvidence) {
		this.idEvidence = idEvidence;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
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

	public EProcessinstance getProcessinstance() {
		return this.processinstance;
	}

	public void setProcessinstance(EProcessinstance processinstance) {
		this.processinstance = processinstance;
	}

	public ETakenDecision getTakenDecision() {
		return this.takenDecision;
	}

	public void setTakenDecision(ETakenDecision takenDecision) {
		this.takenDecision = takenDecision;
	}

}