package shitstorm.beans.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import shitstorm.enums.ObservedEffect;
import shitstorm.interfaces.local.ITakenDecisionEffectDAO;
import shitstorm.persistence.entities.EGoal;
import shitstorm.persistence.entities.ETakenDecision;
import shitstorm.persistence.entities.ETakenDecisionEffect;
import shitstorm.pojos.GenericDAOImpl;

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
		return this.create(decisionEffect);
	}
}
