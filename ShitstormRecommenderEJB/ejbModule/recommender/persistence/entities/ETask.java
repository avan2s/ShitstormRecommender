package recommender.persistence.entities;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the task database table.
 * 
 */
@Entity
@Table(name = "task")
@NamedQueries({
@NamedQuery(name = ETask.QUERY_GETALL, query = "SELECT e FROM ETask e"),
@NamedQuery(name = ETask.QUERY_GET_BY_PROCESS_AND_TASKREF, query="SELECT t FROM ETask t JOIN t.nodeGroup ng JOIN t.process p WHERE p.refInProcessengine=:refProcess AND t.refInProcessengine=:taskRef")
})
public class ETask implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String QUERY_GETALL ="ETask.findAll";
	public static final String QUERY_GET_BY_PROCESS_AND_TASKREF ="ETask.GET_BY_PROCESS_AND_TASKREF";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTask;

	@Column(name = "ref_in_processengine")
	private String refInProcessengine;

	@Column(name = "task_name")
	private String taskName;

	// bi-directional many-to-one association to ENodeSet
	@OneToOne
	@JoinColumn(name = "node_group_id")
	private ENodeGroup nodeGroup;

	// bi-directional many-to-one association to EProcess
	@ManyToOne
	@JoinColumn(name = "process_id")
	private EProcess process;

	// bi-directional many-to-one association to ETaskOptimizationmodel
	@OneToMany(mappedBy = "task")
	private List<ETaskOptimizationmodel> taskOptimizationmodels;

	@OneToMany(mappedBy = "task")
	private List<ETakenDecision> takenDecisions;

	public ETask() {
		this.taskOptimizationmodels = new ArrayList<>();
	}

	public int getIdTask() {
		return this.idTask;
	}

	public void setIdTask(int idTask) {
		this.idTask = idTask;
	}

	public String getRefInProcessengine() {
		return this.refInProcessengine;
	}

	public void setRefInProcessengine(String refInProcessengine) {
		this.refInProcessengine = refInProcessengine;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public ENodeGroup getNodeGroup() {
		return this.nodeGroup;
	}

	public void setNodeGroup(ENodeGroup nodeGroup) {
		this.nodeGroup = nodeGroup;
	}

	public EProcess getProcess() {
		return this.process;
	}

	public void setProcess(EProcess process) {
		this.process = process;
	}

	public List<ETaskOptimizationmodel> getTaskOptimizationmodels() {
		return this.taskOptimizationmodels;
	}

	public List<ETakenDecision> getTakenDecisions() {
		return takenDecisions;
	}

	public void setTakenDecisions(List<ETakenDecision> takenDecisions) {
		this.takenDecisions = takenDecisions;
	}

	public void setTaskOptimizationmodels(List<ETaskOptimizationmodel> taskOptimizationmodels) {
		this.taskOptimizationmodels = taskOptimizationmodels;
	}

	public ETaskOptimizationmodel addTaskOptimizationmodel(ETaskOptimizationmodel taskOptimizationmodel) {
		getTaskOptimizationmodels().add(taskOptimizationmodel);
		taskOptimizationmodel.setTask(this);

		return taskOptimizationmodel;
	}

	public ETaskOptimizationmodel removeTaskOptimizationmodel(ETaskOptimizationmodel taskOptimizationmodel) {
		getTaskOptimizationmodels().remove(taskOptimizationmodel);
		taskOptimizationmodel.setTask(null);

		return taskOptimizationmodel;
	}

}