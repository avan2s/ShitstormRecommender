package recommender.beans.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import recommender.enums.ObservedEffect;
import recommender.interfaces.local.ITakenDecisionEffectDAO;
import recommender.persistence.entities.EGoal;
import recommender.persistence.entities.ETakenDecision;
import recommender.persistence.entities.ETakenDecisionEffect;
import recommender.pojos.GenericDAOImpl;

@Stateless
@Local(ITakenDecisionEffectDAO.class)
public class TakenDecisionEffectDAOBean extends GenericDAOImpl<ETakenDecisionEffect, Integer>
		implements ITakenDecisionEffectDAO {
	@Override
	public ETakenDecisionEffect create(ETakenDecision takenDecision, EGoal goal, ObservedEffect observedEffect) {
		ETakenDecisionEffect decisionEffect = new ETakenDecisionEffect();
		decisionEffect.setGoal(goal);
		decisionEffect.setDecisionEffect(observedEffect);
		goal.getTakenDecisionEffects().add(decisionEffect);
		decisionEffect.getTakenDecisions().add(takenDecision);
		takenDecision.setTakenDecisionEffect(decisionEffect);
		return this.create(decisionEffect);
	}
}
