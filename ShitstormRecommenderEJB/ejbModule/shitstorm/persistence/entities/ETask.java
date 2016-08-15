package shitstorm.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the task database table.
 * 
 */
@Entity
@Table(name="task")
@NamedQuery(name="ETask.findAll", query="SELECT e FROM ETask e")
public class ETask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTask;

	@Column(name="ref_in_processengine")
	private String refInProcessengine;

	@Column(name="task_name")
	private String taskName;

	//bi-directional many-to-one association to ENodeSet
	@OneToOne
	@JoinColumn(name="node_group_id")
	private ENodeGroup nodeGroup;

	//bi-directional many-to-one association to EProcess
	@ManyToOne
	@JoinColumn(name="process_id")
	private EProcess process;

	//bi-directional many-to-one association to ETaskOptimizationmodel
	@OneToMany(mappedBy="task")
	private List<ETaskOptimizationmodel> taskOptimizationmodels;

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