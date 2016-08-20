package shitstorm.pojos.dto;

import java.io.Serializable;

public class GoalRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String goalFigure;
	private int goalStartPeriod;
	private int goalEndPeriod;
	private double goalValue;
	private double goalWeight;

	public double getGoalWeight() {
		return goalWeight;
	}

	public void setGoalWeight(double goalWeight) {
		this.goalWeight = goalWeight;
	}

	public GoalRequest() {

	}

	public String getGoalFigure() {
		return goalFigure;
	}

	public void setGoalFigure(String goalFigure) {
		this.goalFigure = goalFigure;
	}

	public int getGoalStartPeriod() {
		return goalStartPeriod;
	}

	public void setGoalStartPeriod(int goalStartPeriod) {
		this.goalStartPeriod = goalStartPeriod;
	}

	public int getGoalEndPeriod() {
		return goalEndPeriod;
	}

	public void setGoalEndPeriod(int goalEndPeriod) {
		this.goalEndPeriod = goalEndPeriod;
	}

	public double getGoalValue() {
		return goalValue;
	}

	public void setGoalValue(double goalValue) {
		this.goalValue = goalValue;
	}

}
