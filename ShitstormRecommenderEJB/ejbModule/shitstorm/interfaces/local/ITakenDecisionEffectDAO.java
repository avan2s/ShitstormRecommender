package shitstorm.interfaces.local;

import shitstorm.enums.ObservedEffect;
import shitstorm.interfaces.IGenericDAO;
import shitstorm.persistence.entities.EGoal;
import shitstorm.persistence.entities.ETakenDecision;
import shitstorm.persistence.entities.ETakenDecisionEffect;

public interface ITakenDecisionEffectDAO extends IGenericDAO<ETakenDecisionEffect, Integer> {
	public ETakenDecisionEffect create(ETakenDecision takenDecision, EGoal goal, ObservedEffect observedEffect);
}
