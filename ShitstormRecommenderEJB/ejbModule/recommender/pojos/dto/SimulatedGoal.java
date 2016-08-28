package recommender.pojos.dto;

import java.io.Serializable;

public class SimulatedGoal implements Serializable {

	private static final long serialVersionUID = 1L;

	private String goalFigure;
	private double unitValue;
	private double uniformUtility;

	public SimulatedGoal() {

	}

	public String getGoalFigure() {
		return goalFigure;
	}

	public void setGoalFigure(String goalFigure) {
		this.goalFigure = goalFigure;
	}

	public double getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(double unitValue) {
		this.unitValue = unitValue;
	}

	public double getUniformUtility() {
		return uniformUtility;
	}

	public void setUniformUtility(double uniformUtility) {
		this.uniformUtility = uniformUtility;
	}

}
