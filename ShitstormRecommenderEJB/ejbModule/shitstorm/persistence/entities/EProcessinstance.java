package shitstorm.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the processinstance database table.
 * 
 */
@Entity
@Table(name="processinstance")
@NamedQuery(name="EProcessinstance.findAll", query="SELECT e FROM EProcessinstance e")
public class EProcessinstance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idProcessinstance;

	@Column(name="current_period")
	private int currentPeriod;

	@Column(name="influence_diagram_filename")
	private String influenceDiagramFilename;

	@Column(name="influence_diagram_path")
	private String influenceDiagramPath;

	@Column(name="ref_in_processengine")
	private String refInProcessengine;

	//bi-directional many-to-one association to EEvidence
	@OneToMany(cascade=CascadeType.ALL, mappedBy="processinstance")
	private List<EEvidence> evidences;

	//bi-directional many-to-one association to EProcess
	@ManyToOne
	@JoinColumn(name="process_id")
	private EProcess process;

	public EProcessinstance() {
		this.evidences = new ArrayList<>();
	}

	public int getIdProcessinstance() {
		return this.idProcessinstance;
	}

	public void setIdProcessinstance(int idProcessinstance) {
		this.idProcessinstance = idProcessinstance;
	}

	public int getCurrentPeriod() {
		return this.currentPeriod;
	}

	public void setCurrentPeriod(int currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public String getInfluenceDiagramFilename() {
		return this.influenceDiagramFilename;
	}

	public void setInfluenceDiagramFilename(String influenceDiagramFilename) {
		this.influenceDiagramFilename = influenceDiagramFilename;
	}

	public String getInfluenceDiagramPath() {
		return this.influenceDiagramPath;
	}

	public void setInfluenceDiagramPath(String influenceDiagramPath) {
		this.influenceDiagramPath = influenceDiagramPath;
	}

	public String getRefInProcessengine() {
		return this.refInProcessengine;
	}

	public void setRefInProcessengine(String refInProcessengine) {
		this.refInProcessengine = refInProcessengine;
	}

	public List<EEvidence> getEvidences() {
		return this.evidences;
	}

	public void setEvidences(List<EEvidence> evidences) {
		this.evidences = evidences;
	}

	public EEvidence addEvidence(EEvidence evidence) {
		getEvidences().add(evidence);
		evidence.setProcessinstance(this);

		return evidence;
	}

	public EEvidence removeEvidence(EEvidence evidence) {
		getEvidences().remove(evidence);
		evidence.setProcessinstance(null);

		return evidence;
	}

	public EProcess getProcess() {
		return this.process;
	}

	public void setProcess(EProcess process) {
		this.process = process;
	}

}