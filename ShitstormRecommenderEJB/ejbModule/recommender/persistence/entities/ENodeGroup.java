package recommender.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;

import recommender.enums.NodeFocus;

import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the node_set database table.
 * 
 */
@Entity
@Table(name = "node_group")
@NamedQueries({ @NamedQuery(name = ENodeGroup.QUERY_GETALL, query = "SELECT e FROM ENodeGroup e"),
		@NamedQuery(name = ENodeGroup.QUERY_GET_BY_ABBREVIATION_AND_PROCESS, query = "SELECT ng FROM ENodeGroup ng JOIN ng.nodes n JOIN n.process p WHERE ng.nodeAbbreviation=:abbreviation AND p.refInProcessengine=:refProcess") })

public class ENodeGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String QUERY_GETALL = "ENodeGroup.findAll";
	public static final String QUERY_GET_BY_ABBREVIATION_AND_PROCESS = "ENodeGroup.GET_BY_ABBREVIATION_AND_PROCESS";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idNodeGroup;

	@Column(name = "node_abbreviation")
	private String nodeAbbreviation;

	@Column(name = "node_focus")
	@Enumerated(EnumType.STRING)
	private NodeFocus nodeFocus;

	// bi-directional many-to-one association to ENode
	@OneToMany(mappedBy = "nodeGroup")
	private List<ENode> nodes;

	@OneToOne(mappedBy = "nodeGroup", fetch = FetchType.EAGER)
	private EGoal goal;

	@OneToOne(mappedBy = "nodeGroup", fetch = FetchType.EAGER)
	private EProcessvariable processvariable;

	// bi-directional many-to-one association to ETask
	@OneToOne(mappedBy = "nodeGroup", fetch = FetchType.EAGER)
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

	@Override
	public String toString() {
		return this.idNodeGroup + ", " + nodeAbbreviation + ", " + nodeFocus;
	}

}