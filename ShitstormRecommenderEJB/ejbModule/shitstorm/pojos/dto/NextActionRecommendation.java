package shitstorm.pojos.dto;

import java.io.Serializable;

import kip.tools.model.NextBestAction;

public class NextActionRecommendation implements Serializable {

	private static final long serialVersionUID = 1L;

	private NextBestAction nextBestAction;
	private String additionalInformation;

	public NextActionRecommendation() {
		this.additionalInformation = "";
	}

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
