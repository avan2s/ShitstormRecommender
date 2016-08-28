package recommender.pojos.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


import kip.tools.model.NextBestAction;

@XmlRootElement(name="NextActionRecommendation")
public class NextActionRecommendation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private NextBestAction nextBestAction;
	private String additionalInformation;

	public NextActionRecommendation() {
		this.additionalInformation = "";
	}

	@XmlElement(name="NextRecommendedAction")
	public NextBestAction getNextBestAction() {
		return nextBestAction;
	}

	public void setNextBestAction(NextBestAction nextBestAction) {
		this.nextBestAction = nextBestAction;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

}
