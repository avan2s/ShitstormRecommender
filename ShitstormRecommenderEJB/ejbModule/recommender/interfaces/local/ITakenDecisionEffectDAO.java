package recommender.interfaces.local;

import recommender.enums.ObservedEffect;
import recommender.interfaces.IGenericDAO;
import recommender.persistence.entities.EGoal;
import recommender.persistence.entities.ETakenDecision;
import recommender.persistence.entities.ETakenDecisionEffect;

public interface ITakenDecisionEffectDAO extends IGenericDAO<ETakenDecisionEffect, Integer> {
	public ETakenDecisionEffect create(ETakenDecision takenDecision, EGoal goal, ObservedEffect observedEffect);
}
