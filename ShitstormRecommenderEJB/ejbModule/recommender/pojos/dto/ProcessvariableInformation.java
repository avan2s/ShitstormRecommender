package recommender.pojos.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import recommender.enums.VariableType;

@XmlRootElement(name="ProcessvariableInformation")
public class ProcessvariableInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	private String refNameInProcessEngine;
	private VariableType variableType;
	private String value;

	public ProcessvariableInformation() {
	}

	@XmlElement(name="variableReferenceInProcessEngine")
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
	
	@XmlElement(name="variableValue")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
