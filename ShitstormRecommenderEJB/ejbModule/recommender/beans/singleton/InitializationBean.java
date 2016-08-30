package recommender.beans.singleton;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.NoResultException;

import kip.enums.KipGoalEffect;
import recommender.beans.registrators.ProcessInstanceRegistratorBean;
import recommender.beans.registrators.ProcessRegistratorBean;
import recommender.enums.VariableType;
import recommender.exceptions.ProcessNotSupportedException;
import recommender.interfaces.local.IGoalDAO;
import recommender.interfaces.local.INodeGroupDAO;
import recommender.interfaces.local.IProcessDAO;
import recommender.interfaces.local.IProcessvariableDAO;
import recommender.interfaces.local.ITaskDAO;
import recommender.persistence.entities.ENodeGroup;
import recommender.persistence.entities.EProcess;

@Singleton
@Startup
public class InitializationBean {

	@EJB
	private IGoalDAO goalDAO;

	@EJB
	private IProcessDAO processDAO;

	@EJB
	private ITaskDAO taskDAO;

	@EJB
	private IProcessvariableDAO variableDAO;

	@EJB
	private INodeGroupDAO daoNodeGroup;

	@EJB
	private ProcessRegistratorBean processRegistrator;

	@EJB
	private ProcessInstanceRegistratorBean instanceRegistrator;

	@PostConstruct
	private void initialize() {
		String refInProcessEngine = "shitstorm";
		EProcess process = this.processDAO.findByReferenceInProcessEngine(refInProcessEngine);
		if (process == null) {
			try {
				process = this.processRegistrator.registerProcess("shitstorm", refInProcessEngine, "shitstorm.xdsl", "_",
						"I", "E", 3, true);
				String refProcess = process.getRefInProcessengine();

				// Ziele verlinken
				ENodeGroup ngKZ = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "KZ");
				this.goalDAO.create("Kundenzufriedenheit","KZE", KipGoalEffect.POSITIVE, process, ngKZ);

				ENodeGroup ngIG = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "IG");
				this.goalDAO.create("Informationsgewinn","IGE", KipGoalEffect.POSITIVE, process, ngIG);

				ENodeGroup ngZA = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "ZA");
				this.goalDAO.create("Zeitaufwand","min", KipGoalEffect.NEGATIVE, process, ngZA);

				ENodeGroup ngK = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "K");
				this.goalDAO.create("Kosten","Euro", KipGoalEffect.NEGATIVE, process, ngK);

				ENodeGroup ngSP = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "SP");
				this.goalDAO.create("Stakeholder-Power","SPE", KipGoalEffect.NEGATIVE, process, ngSP);

				// Tasks verlinken
				ENodeGroup ngSMTb = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "SMTb");
				this.taskDAO.create("Social-Media-Teilnehmer beruhigen", "SMTb", process, ngSMTb);

				ENodeGroup ngUf = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "Uf");
				this.taskDAO.create("Ursache finden", "Uf", process, ngUf);

				ENodeGroup ngVe = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "Ve");
				this.taskDAO.create("Verantwortlichen entlassen", "Ve", process, ngVe);

				ENodeGroup ngEv = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "Ev");
				this.taskDAO.create("Entgeltliche Entschaedigung veranlassen", "Ev", process, ngEv);

				// Prozessvariablen verlinken
				ENodeGroup ngVarU = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "varU");
				this.variableDAO.create("Gefundene Ursache", "vUrsache", VariableType.STRING, process, ngVarU);

				ENodeGroup ngVarKZ = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "varKZ");
				this.variableDAO.create("Ermittelte Kundenzufriedenheit", "vKundenzufriedenheit", VariableType.STRING,
						process, ngVarKZ);

				ENodeGroup ngVarSP = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "varSP");
				this.variableDAO.create("Ermittelte Stakeholder-Power", "vStakeholderPower", VariableType.STRING,
						process, ngVarSP);

				// Prozessinstanz registrieren
				this.instanceRegistrator.registerProcessInstance(refProcess, "shitstorm-instance-1");

			} catch (NoResultException | IOException | ProcessNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}

}
