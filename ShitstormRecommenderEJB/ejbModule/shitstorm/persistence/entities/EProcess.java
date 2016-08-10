package shitstorm.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the process database table.
 * 
 */
@Entity
@Table(name="process")
@NamedQuery(name="EProcess.findAll", query="SELECT e FROM EProcess e")
public class EProcess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idProcess;

	@Column(name="influence_diagram_filename")
	private String influenceDiagramFilename;

	@Column(name="influence_diagram_path")
	private String influenceDiagramPath;

	@Column(name="influence_diagram_period_seperator")
	private String influenceDiagramPeriodSeperator;

	@Column(name="process_name")
	private String processName;

	@Column(name="ref_in_processengine")
	private String refInProcessengine;

	//bi-directional many-to-many association to EGoal
	@ManyToMany
	@JoinTable(
		name="process_has_goal"
		, joinColumns={
			@JoinColumn(name="process_idProcess")
			}
		, inverseJoinColumns={
			@JoinColumn(name="goal_idGoal")
			}
		)
	private List<EGoal> goals;

	//bi-directional many-to-one association to EProcessinstance
	@OneToMany(cascade=CascadeType.ALL, mappedBy="process")
	private List<EProcessinstance> processinstances;

	//bi-directional many-to-one association to EProcessvariable
	@OneToMany(mappedBy="process")
	private List<EProcessvariable> processvariables;

	//bi-directional many-to-one association to ETask
	@OneToMany(mappedBy="process")
	private List<ETask> tasks;
	
	@OneToMany(mappedBy="process")
	private List<ENode> nodes;

	public EProcess() {
		this.goals = new ArrayList<>();
		this.processinstances = new ArrayList<>();
		this.processvariables = new ArrayList<>();
		this.tasks = new ArrayList<>();
	}

	public int getIdProcess() {
		return this.idProcess;
	}

	public void setIdProcess(int idProcess) {
		this.idProcess = idProcess;
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

	public String getInfluenceDiagramPeriodSeperator() {
		return this.influenceDiagramPeriodSeperator;
	}

	public void setInfluenceDiagramPeriodSeperator(String influenceDiagramPeriodSeperator) {
		this.influenceDiagramPeriodSeperator = influenceDiagramPeriodSeperator;
	}

	public String getProcessName() {
		return this.processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getRefInProcessengine() {
		return this.refInProcessengine;
	}

	public void setRefInProcessengine(String refInProcessengine) {
		this.refInProcessengine = refInProcessengine;
	}

	public List<EGoal> getGoals() {
		return this.goals;
	}

	public void setGoals(List<EGoal> goals) {
		this.goals = goals;
	}

	public List<EProcessinstance> getProcessinstances() {
		return this.processinstances;
	}

	public void setProcessinstances(List<EProcessinstance> processinstances) {
		this.processinstances = processinstances;
	}

	public EProcessinstance addProcessinstance(EProcessinstance processinstance) {
		getProcessinstances().add(processinstance);
		processinstance.setProcess(this);

		return processinstance;
	}

	public EProcessinstance removeProcessinstance(EProcessinstance processinstance) {
		getProcessinstances().remove(processinstance);
		processinstance.setProcess(null);

		return processinstance;
	}

	public List<EProcessvariable> getProcessvariables() {
		return this.processvariables;
	}

	public void setProcessvariables(List<EProcessvariable> processvariables) {
		this.processvariables = processvariables;
	}

	public EProcessvariable addProcessvariable(EProcessvariable processvariable) {
		getProcessvariables().add(processvariable);
		processvariable.setProcess(this);

		return processvariable;
	}

	public EProcessvariable removeProcessvariable(EProcessvariable processvariable) {
		getProcessvariables().remove(processvariable);
		processvariable.setProcess(null);

		return processvariable;
	}

	public List<ETask> getTasks() {
		return this.tasks;
	}

	public List<ENode> getNodes() {
		return nodes;
	}

	public void setNodes(List<ENode> nodes) {
		this.nodes = nodes;
	}

	public void setTasks(List<ETask> tasks) {
		this.tasks = tasks;
	}

	public ETask addTask(ETask task) {
		getTasks().add(task);
		task.setProcess(this);

		return task;
	}

	public ETask removeTask(ETask task) {
		getTasks().remove(task);
		task.setProcess(null);

		return task;
	}

}