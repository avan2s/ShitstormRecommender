package shitstorm.beans;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.NoResultException;

import kip.enums.KipGoalEffect;
import shitstorm.enums.VariableType;
import shitstorm.exceptions.ProcessNotSupportedException;
import shitstorm.interfaces.IGoalDAO;
import shitstorm.interfaces.INodeGroupDAO;
import shitstorm.interfaces.IProcessDAO;
import shitstorm.interfaces.IProcessvariableDAO;
import shitstorm.interfaces.ITaskDAO;
import shitstorm.persistence.entities.ENodeGroup;
import shitstorm.persistence.entities.EProcess;

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
		EProcess process = this.processDAO.findByReferenceInProcessEngine("shitstorm-ref");
		if (process == null) {
			try {
				process = this.processRegistrator.registerProcess("shitstorm", "shitstorm-ref", "shitstorm.xdsl", "_",
						"I", "E", true);
				String refProcess = process.getRefInProcessengine();

				// Ziele verlinken
				ENodeGroup ngKZ = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "KZ");
				this.goalDAO.create("Kundenzufriedenheit", KipGoalEffect.POSITIVE, process, ngKZ);

				ENodeGroup ngIG = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "IG");
				this.goalDAO.create("Informationsgewinn", KipGoalEffect.POSITIVE, process, ngIG);

				ENodeGroup ngZA = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "ZA");
				this.goalDAO.create("Zeitaufwand", KipGoalEffect.NEGATIVE, process, ngZA);

				ENodeGroup ngK = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "K");
				this.goalDAO.create("Kosten", KipGoalEffect.NEGATIVE, process, ngK);

				ENodeGroup ngSP = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "SP");
				this.goalDAO.create("Stakeholder-Power", KipGoalEffect.NEGATIVE, process, ngSP);

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
				this.variableDAO.create("Gefundene Ursache", "ursache", VariableType.STRING, process, ngVarU);

				ENodeGroup ngVarKZ = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "varKZ");
				this.variableDAO.create("Ermittelte Kundenzufriedenheit", "kundenzufriedenheit", VariableType.STRING,
						process, ngVarKZ);

				ENodeGroup ngVarSP = this.daoNodeGroup.findByAbbreviationInProcess(refProcess, "varSP");
				this.variableDAO.create("Ermittelte Stakeholder-Power", "stakeholderPower", VariableType.STRING,
						process, ngVarSP);

				// Prozessinstanz registrieren
				this.instanceRegistrator.registerProcessInstance(refProcess, "shitstorm-instance-1");

			} catch (NoResultException | IOException | ProcessNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
