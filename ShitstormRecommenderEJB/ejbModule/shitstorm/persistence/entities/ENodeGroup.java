package shitstorm.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;

import shitstorm.enums.NodeFocus;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the node_set database table.
 * 
 */
@Entity
@Table(name="node_group")
@NamedQuery(name="ENodeSet.findAll", query="SELECT e FROM ENodeGroup e")
public class ENodeGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idNodeGroup;

	@Column(name="node_abbreviation")
	private String nodeAbbreviation;

	@Column(name="node_focus")
	@Enumerated(EnumType.STRING)
	private NodeFocus nodeFocus;

	//bi-directional many-to-one association to ENode
	@OneToMany(mappedBy="nodeGroup")
	private List<ENode> nodes;

	//bi-directional many-to-one association to EGoal
	//@ManyToOne
	@OneToOne
	@JoinColumn(name="goal_id")
	private EGoal goal;

	//bi-directional many-to-one association to EProcessvariable
	//@ManyToOne
	@OneToOne
	@JoinColumn(name="processvariable_id")
	private EProcessvariable processvariable;

	//bi-directional many-to-one association to ETask
	@OneToOne
	@JoinColumn(name="task_id")
	private ETask task;

	public ENodeGroup() {
		this.nodes = new ArrayList<>();
	}

	public int getIdNodeGroup() {
		return this.idNodeGroup;
	}

	public void setIdNodeGroup(int idNodeGroup) {
		this.idNodeGroup = idNodeGroup;
	}

	public String getNodeAbbreviation() {
		return this.nodeAbbreviation;
	}

	public void setNodeAbbreviation(String nodeAbbreviation) {
		this.nodeAbbreviation = nodeAbbreviation;
	}

	public NodeFocus getNodeFocus() {
		return this.nodeFocus;
	}

	public void setNodeFocus(NodeFocus nodeFocus) {
		this.nodeFocus = nodeFocus;
	}

	public List<ENode> getNodes() {
		return this.nodes;
	}

	public void setNodes(List<ENode> nodes) {
		this.nodes = nodes;
	}

	public ENode addNode(ENode node) {
		getNodes().add(node);
		node.setNodeGroup(this);

		return node;
	}

	public ENode removeNode(ENode node) {
		getNodes().remove(node);
		node.setNodeGroup(null);

		return node;
	}

	public EGoal getGoal() {
		return this.goal;
	}

	public void setGoal(EGoal goal) {
		this.goal = goal;
	}

	public EProcessvariable getProcessvariable() {
		return this.processvariable;
	}

	public void setProcessvariable(EProcessvariable processvariable) {
		this.processvariable = processvariable;
	}

	public ETask getTask() {
		return this.task;
	}

	public void setTask(ETask task) {
		this.task = task;
	}

}