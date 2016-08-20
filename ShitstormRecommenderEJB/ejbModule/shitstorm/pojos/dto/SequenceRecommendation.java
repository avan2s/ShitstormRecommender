package shitstorm.pojos.dto;

import java.io.Serializable;
import java.util.List;

import kip.tools.model.NextBestAction;
import kip.tools.model.SimPeriod;

public class SequenceRecommendation implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<SimPeriod> simValuesInPeriods;
	private List<NextBestAction> nextBestActions;

	public SequenceRecommendation() {
	}

	public List<SimPeriod> getSimValuesInPeriods() {
		return simValuesInPeriods;
	}

	public void setSimValuesInPeriods(List<SimPeriod> simValuesInPeriods) {
		this.simValuesInPeriods = simValuesInPeriods;
	}

	public List<NextBestAction> getNextBestActions() {
		return nextBestActions;
	}

	public void setNextBestActions(List<NextBestAction> nextBestActions) {
		this.nextBestActions = nextBestActions;
	}

}
