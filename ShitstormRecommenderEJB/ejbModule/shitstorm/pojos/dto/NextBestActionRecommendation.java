package shitstorm.pojos.dto;

import java.io.Serializable;

import kip.tools.model.NextBestAction;
import kip.tools.model.SimPeriod;

public class NextBestActionRecommendation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SimPeriod simValues;
	private NextBestAction nextBestAction;

	public NextBestActionRecommendation() {
		this.simValues = new SimPeriod();
		this.nextBestAction = new NextBestAction();
	}

	public SimPeriod getSimValues() {
		return simValues;
	}

	public void setSimValues(SimPeriod simValues) {
		this.simValues = simValues;
	}

	public NextBestAction getNextBestAction() {
		return nextBestAction;
	}

	public void setNextBestAction(NextBestAction nextBestAction) {
		this.nextBestAction = nextBestAction;
	}

}
