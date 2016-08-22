package shitstorm.pojos.dto;

import java.io.Serializable;
import java.util.List;

import kip.tools.model.NextBestAction;
import kip.tools.model.SimGoal;

public class SequenceRecommendation implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<NextBestAction> nextBestActions;
	private List<SimGoal> simGoals;
	private String additionalInformation;

	public SequenceRecommendation() {
		this.additionalInformation = "";
	}

	public List<NextBestAction> getNextBestActions() {
		return nextBestActions;
	}

	public void setNextBestActions(List<NextBestAction> nextBestActions) {
		this.nextBestActions = nextBestActions;
	}

	public List<SimGoal> getSimGoals() {
		return simGoals;
	}

	public void setSimGoals(List<SimGoal> simGoals) {
		this.simGoals = simGoals;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

}
