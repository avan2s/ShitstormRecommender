package shitstorm.pojos.dto;

import java.io.Serializable;

import shitstorm.enums.VariableType;

public class ProcessvariableInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	private String refNameInProcessEngine;
	private VariableType variableType;
	private String value;

	public ProcessvariableInformation() {
	}

	public String getRefNameInProcessEngine() {
		return refNameInProcessEngine;
	}

	public void setRefNameInProcessEngine(String refNameInProcessEngine) {
		this.refNameInProcessEngine = refNameInProcessEngine;
	}

	public VariableType getVariableType() {
		return variableType;
	}

	public void setVariableType(VariableType variableType) {
		this.variableType = variableType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
