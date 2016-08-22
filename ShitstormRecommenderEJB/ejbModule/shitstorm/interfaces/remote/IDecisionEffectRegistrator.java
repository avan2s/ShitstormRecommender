package shitstorm.interfaces.remote;

import java.util.List;

import shitstorm.enums.ObservedEffect;
import shitstorm.exceptions.ProcessInstanceNotSupportedException;
import shitstorm.persistence.entities.ETakenDecisionEffect;

public interface IDecisionEffectRegistrator {

	public List<ETakenDecisionEffect> registerEffect(String refInstance, String goalFigure,
			ObservedEffect observedEffect, String taskReference) throws ProcessInstanceNotSupportedException;
}