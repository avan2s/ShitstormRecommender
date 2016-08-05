package shitstorm.persistence.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the node database table.
 * 
 */
@Entity
@Table(name="node")
@NamedQuery(name="ENode.findAll", query="SELECT e FROM ENode e")
public class ENode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idNode;

	@Column(name="node_name")
	private String nodeName;

	private int period;

	//bi-directional many-to-one association to EEvidence
	@OneToMany(mappedBy="node")
	private List<EEvidence> evidences;

	//bi-directional many-to-one association to ENodeSet
	@ManyToOne
	@JoinColumn(name="node_focus_id")
	private ENodeSet nodeSet;
	
	@ManyToOne
	@JoinColumn(name="processinstance_id")
	private EProcessinstance processinstance;

	public ENode() {
		this.evidences = new ArrayList<>();
	}

	public int getIdNode() {
		return this.idNode;
	}

	public void setIdNode(int idNode) {
		this.idNode = idNode;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public int getPeriod() {
		return this.period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public List<EEvidence> getEvidences() {
		return this.evidences;
	}

	public void setEvidences(List<EEvidence> evidences) {
		this.evidences = evidences;
	}

	public EEvidence addEvidence(EEvidence evidence) {
		getEvidences().add(evidence);
		evidence.setNode(this);

		return evidence;
	}

	public EEvidence removeEvidence(EEvidence evidence) {
		getEvidences().remove(evidence);
		evidence.setNode(null);

		return evidence;
	}

	public ENodeSet getNodeSet() {
		return this.nodeSet;
	}

	public void setNodeSet(ENodeSet nodeSet) {
		this.nodeSet = nodeSet;
	}

	public EProcessinstance getProcessinstance() {
		return processinstance;
	}

	public void setProcessinstance(EProcessinstance processinstance) {
		this.processinstance = processinstance;
	}

}