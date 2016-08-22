package shitstorm.beans.registrators;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import shitstorm.enums.ObservedEffect;
import shitstorm.exceptions.ProcessInstanceNotSupportedException;
import shitstorm.interfaces.local.IEvidenceDAO;
import shitstorm.interfaces.local.IGoalDAO;
import shitstorm.interfaces.local.IProcessInstanceDAO;
import shitstorm.interfaces.local.ITakenDecisionEffectDAO;
import shitstorm.interfaces.remote.IDecisionEffectRegistrator;
import shitstorm.persistence.entities.EEvidence;
import shitstorm.persistence.entities.EGoal;
import shitstorm.persistence.entities.ETakenDecision;
import shitstorm.persistence.entities.ETakenDecisionEffect;

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
