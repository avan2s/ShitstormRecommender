package recommender.beans.registrators;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import recommender.enums.ObservedEffect;
import recommender.exceptions.ProcessInstanceNotSupportedException;
import recommender.interfaces.local.IEvidenceDAO;
import recommender.interfaces.local.IGoalDAO;
import recommender.interfaces.local.IProcessInstanceDAO;
import recommender.interfaces.local.ITakenDecisionEffectDAO;
import recommender.interfaces.remote.IDecisionEffectRegistrator;
import recommender.persistence.entities.EEvidence;
import recommender.persistence.entities.EGoal;
import recommender.persistence.entities.ETakenDecision;
import recommender.persistence.entities.ETakenDecisionEffect;

@Stateless
@Remote(IDecisionEffectRegistrator.class)
public class DecisionEffectRegistratorBean implements IDecisionEffectRegistrator {
	@EJB
	private IGoalDAO daoGoal;

	@EJB
	private IProcessInstanceDAO daoInstance;

	@EJB
	private IEvidenceDAO daoEvidence;

	@EJB
	private ITakenDecisionEffectDAO daoDecisionEffect;

	public List<ETakenDecisionEffect> registerEffect(String refInstance, String goalFigure,
			ObservedEffect observedEffect, String taskReference) throws ProcessInstanceNotSupportedException {
		if (this.daoInstance.findByRefInProcessEngine(refInstance) == null) {
			throw new ProcessInstanceNotSupportedException(refInstance);
		}
		List<ETakenDecisionEffect> effects = new ArrayList<>();
		EGoal goal = this.daoGoal.findByGoalFigure(goalFigure);
		List<EEvidence> evidenceForTakenDecision = this.daoEvidence.findByProcessInstanceAndTaskReference(refInstance,
				taskReference);
		for (EEvidence eEvidence : evidenceForTakenDecision) {
			ETakenDecision takenDecision = eEvidence.getTakenDecision();
			ETakenDecisionEffect effect = this.daoDecisionEffect.create(takenDecision, goal, observedEffect);
			effects.add(effect);
		}
		return effects;
	}
}
