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
@Table(name = "processinstance")
@NamedQueries({
		@NamedQuery(name = EProcessinstance.GET_BY_PROCESSINSTANCE_REF, query = "SELECT i FROM EProcessinstance i WHERE i.refInProcessengine=:refInstance"),
		@NamedQuery(name = EProcessinstance.GETALL, query = "SELECT e FROM EProcessinstance e") })
public class EProcessinstance implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String GETALL = "EProcessinstance.findAll";
	public static final String GET_BY_PROCESSINSTANCE_REF = "EProcessinstance.GET_BY_PROCESSINSTANCE_REF";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProcessinstance;

	@Column(name = "current_period")
	private int currentPeriod;

	@Column(name = "influence_diagram_filename")
	private String influenceDiagramFilename;

	@Column(name = "influence_diagram_path")
	private String influenceDiagramPath;

	@Column(name = "ref_in_processengine")
	private String refInProcessengine;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "processinstance")
	private List<EEvidence> evidences;

	// bi-directional many-to-one association to EProcess
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "process_id")
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

	public EProcess getProcess() {
		return this.process;
	}

	public void setProcess(EProcess process) {
		this.process = process;
	}

}