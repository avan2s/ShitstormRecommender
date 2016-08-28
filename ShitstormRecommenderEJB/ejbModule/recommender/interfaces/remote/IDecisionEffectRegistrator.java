package recommender.interfaces.remote;

import java.util.List;

import recommender.enums.ObservedEffect;
import recommender.exceptions.ProcessInstanceNotSupportedException;
import recommender.persistence.entities.ETakenDecisionEffect;

public interface IDecisionEffectRegistrator {

	public List<ETakenDecisionEffect> registerEffect(String refInstance, String goalFigure,
			ObservedEffect observedEffect, String taskReference) throws ProcessInstanceNotSupportedException;
}