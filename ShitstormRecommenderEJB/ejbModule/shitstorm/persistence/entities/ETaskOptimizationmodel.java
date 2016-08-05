package shitstorm.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the task_optimizationmodel database table.
 * 
 */
@Entity
@Table(name="task_optimizationmodel")
@NamedQuery(name="ETaskOptimizationmodel.findAll", query="SELECT e FROM ETaskOptimizationmodel e")
public class ETaskOptimizationmodel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTaskOptimizationmodel;

	private String filename;

	private String filepath;

	//bi-directional many-to-one association to ETask
	@ManyToOne
	@JoinColumn(name="task_id")
	private ETask task;

	public ETaskOptimizationmodel() {
	}

	public int getIdTaskOptimizationmodel() {
		return this.idTaskOptimizationmodel;
	}

	public void setIdTaskOptimizationmodel(int idTaskOptimizationmodel) {
		this.idTaskOptimizationmodel = idTaskOptimizationmodel;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public ETask getTask() {
		return this.task;
	}

	public void setTask(ETask task) {
		this.task = task;
	}

}