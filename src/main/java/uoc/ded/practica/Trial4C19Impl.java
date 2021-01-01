package uoc.ded.practica;

import uoc.ded.practica.exceptions.*;
import uoc.ded.practica.model.*;
import uoc.ded.practica.util.DiccionariOrderedVector;
import uoc.ded.practica.util.OrderedVector;
import uoc.ei.tads.*;

import java.util.Date;

public class Trial4C19Impl implements Trial4C19 {

    private DiccionariOrderedVector<String, User> users;
    private Trial[] trials;
    private OrderedVector<QuestionGroup> groups;
    private int numTrials;
    private int numLaboratories;
    private int nextLaboratory;
    private CuaAmbPrioritat<Sample> pendingSamples;
    private DiccionariAVLImpl<String, Sample> completedSamples;
    private TaulaDispersio<String, Clinician> clinicians;
    private Laboratory[] laboratories;
    private Trial mostActiveTrial;
    private Clinician mostActiveClinician;

    public Trial4C19Impl() {
        this.users = new DiccionariOrderedVector<String, User>(U, User.CMP);
        this.trials = new Trial[T];
        this.groups = new OrderedVector<QuestionGroup>(G, QuestionGroup.CMP);
        this.pendingSamples = new CuaAmbPrioritat<Sample>();
        this.completedSamples = new DiccionariAVLImpl<String, Sample>();
        this.clinicians = new TaulaDispersio<String, Clinician>();
        this.laboratories = new Laboratory[L];
        this.mostActiveTrial = null;
        this.mostActiveClinician = null;
        
        /* no cal inicialitzar numTrials, numLaboratories ni nextLaboratory ja 
         * que són variables membres de classe Java 
         * --> https://stackoverflow.com/questions/19131336/default-values-and-initialization-in-java
         */
    }
        
    public void addUser(String idUser, String name, String surname) {
        User u = this.getUser(idUser);
        if (u != null) {
            u.setName(name);
            u.setSurname(surname);
        } else {
            u = new User(idUser, name, surname);
            this.users.afegir(idUser, u);
        }
    }
    
    /* UPGRADE #1: Modifiquem el mètode d'afegir usuaris per a contemplar els nous requeriments (atributs) */ 
    
	@Override   
    public void addUser(String idUser, String name, String surname, Date birthday, Level level) {
        User u = this.getUser(idUser);
        if (u != null) {
            u.setName(name);
            u.setSurname(surname);
            u.setDateOfBirth(birthday);
            u.setLevel(level);
        } else {
            u = new User(idUser, name, surname, birthday, level);
            this.users.afegir(idUser, u);
        }
    }

    public void addTrial(int idTrial, String description) throws TrialAlreadyExistsException {
        if (this.trials[idTrial]!=null) throw new TrialAlreadyExistsException();
        else {
            this.trials[idTrial] = new Trial(idTrial, description);
            this.numTrials++;
        }
    }

    private QuestionGroup getQuestionGroup(String idQuestionGroup) {
        boolean found = false;

        Iterador<QuestionGroup> it = this.groups.elements();
        QuestionGroup qg = null;

        while (it.hiHaSeguent() && !found) {
            qg = it.seguent();
            found = qg.is(idQuestionGroup);
        }
        return (found? qg: null);
    }

    public void addQuestionGroup(String idQuestionGroup, Priority priority) {
        QuestionGroup group = this.getQuestionGroup(idQuestionGroup);
        if (group==null) {
            group = new QuestionGroup(idQuestionGroup, priority);
            this.groups.update(group);
        }
        else group.setPriority(priority);

    }

    public void addQuestion(String idQuestion, String wording, Type type, String[] choices, String idGroup)
            throws QuestionGroupNotFoundException {

        QuestionGroup qg = this.getQuestionGroup(idGroup);
        if (qg == null) throw new QuestionGroupNotFoundException();

        qg.addQuestion(idQuestion, wording, type, choices);

    }

    public Iterador<Question> getQuestions(String idGroup) throws QuestionGroupNotFoundException {
        QuestionGroup qg = this.getQuestionGroup(idGroup);
        if (qg == null) throw new QuestionGroupNotFoundException();
        return qg.questions();
    }

    public void assignQuestionGroup2Trial(String idGroup, int idTrial) throws QuestionGroupNotFoundException, TrialNotFoundException {
        QuestionGroup qg = this.getQuestionGroup(idGroup);
        if (qg == null) throw new QuestionGroupNotFoundException();

        Trial t = this.trials[idTrial];
        if (t==null) throw new TrialNotFoundException();

        t.addQuestionGroup(qg);
    }

    public void assignUser2Trial(int idTrial, String idUser) throws UserIsAlreadyInTrialException {
        Trial trial = this.trials[idTrial];
        User user = this.users.consultar(idUser);
        if (!user.isActive()) {
            trial.addUser(user);
            user.setActive();
            user.setTrial(trial);
        }
        else throw new UserIsAlreadyInTrialException();
    }

    public Question getCurrentQuestion(String idUser) throws UserNotFoundException {
        User user = this.users.consultar(idUser);
        if (user==null) throw new UserNotFoundException();
        Question q = user.currentQuestion();
        return q;
    }

    public void addAnswer(String idUser, Date date, String answer) throws UserNotFoundException, NoQuestionsException {
        User user = this.users.consultar(idUser);
        if (user==null) throw new UserNotFoundException();
        Question q = user.nextQuestion();
        if (q == null) throw new NoQuestionsException();
        user.addAnswer(q, answer);
        updateMostActiveTrial(user.getTrial());
    }

    private void updateMostActiveTrial(Trial trial) {
        if (this.mostActiveTrial==null) this.mostActiveTrial = trial;
        else if (this.mostActiveTrial.numAnswers()<trial.numAnswers()) this.mostActiveTrial = trial;
    }

    public Iterador<Answer> getAnswers(String idUser) throws UserNotFoundException, NoAnswersException {
        User user = this.users.consultar(idUser);
        if (user==null) throw new UserNotFoundException();

        Iterador<Answer> it = user.answers();
        if (!it.hiHaSeguent())  throw new NoAnswersException();

        return it;
    }

    public User mostActiveUser(int idTrial) {
        Trial trial = this.trials[idTrial];

        return trial.mostActiveUser();
    }

    public Trial mostActiveTrial() {
        return this.mostActiveTrial;
    }


    public int numUsers() {
        return this.users.nombreElems();
    }

    public int numTrials() {
        return this.numTrials;
    }

    public int numQuestionGroups() {
        return this.groups.nombreElems();
    }

    public int numQuestion4Group(String idGroup) {
        QuestionGroup qg = this.getQuestionGroup(idGroup);
        return (qg!=null?qg.numQuestions(): 0 );
    }

    public int numQuestionGroups4Trial(int idTrial) {
        Trial trial = this.trials[idTrial];

        return (trial!=null?trial.numQuestionGroups():0);
    }

    public int numUsers4Trial(int idTrial) {
        Trial trial = this.trials[idTrial];
        return (trial!=null?trial.numUsers():0);
    }

    public User getUser(String idUser) {
        return this.users.consultar(idUser);
    }

    public Iterador<QuestionGroup> getQuestionGroups() {
        return this.groups.elements();
    }

	@Override
	public void addClinician(String idClinician, String name, String surname, String knowledgeArea) {
        Clinician c = this.getClinician(idClinician);
        if (c != null) {
            c.setName(name);
            c.setSurname(surname);
            c.setKnowledgeArea(knowledgeArea);
        } else {
            c = new Clinician(idClinician, name, surname, knowledgeArea);
            this.clinicians.afegir(idClinician, c);
        }		
	}


	@Override
	public Clinician getClinician(String idClinician) {
		return this.clinicians.consultar(idClinician);
	}


	@Override
	public void addLaboratory(String idLaboratory, String name) {
		
		/* Primer hem de saber quina és la següent posició lliure del vector. 
		 * A priori ho podriem fer de dues maneres:
		 * 
		 * #1 - Amb eficiència temporal lineal fent un recorregut del vector, que 
		 * a la pràctica resulta ser insignificant ja que la quantitat de labs 
		 * no passa de les desenes (fet que també ens assegura que la quantitat 
		 * d'insercions será petita, és a dir, el recorregut de cost O(n) amb 
		 * n < 100 s'efectuarà molt poques vegades.
		 * 
		 *  O BÉ:
		 *  
		 *  #2 - Amb eficiència temporal constant O(1) Simplement afegint un nou 
		 *  atribut ""numLaboratories" al TAD Trial4C19 que mantindrem sempre 
		 *  actualitzat amb el nombre de laboratoris total i que ens servirà per 
		 *  a saber la posició del vector en la que hem d'afegir el laboratori 
		 *  (la següent a la darrera).
		 *  
		 *   Obviament, escollirem la opció #2: */
		
		Laboratory l = this.getLaboratory(idLaboratory);
        if (l != null) {
            l.setName(name);
        } else {
            l = new Laboratory(idLaboratory, name);
            
	        /* Afegim el laboratori a la darrera posició lliure del vector de 
	         * laboratoris (l'ordre d'inserció estableix l'ordre en el que 
	         * s'anirà rotant l'assignació del laboratori al que s'envien les 
	         * mostres). */
            
            this.laboratories[this.numLaboratories] = l;
            
            /* Actualitzem la quantitat de laboratoris total*/
            
            this.numLaboratories++;
        }			
	}

	@Override
	public Laboratory getLaboratory(String idLaboratory) {
		
		/* Cerquem el lab fent un recorregut amb O(n) -> temps lineal. Com hem
		 * explicat en el cas de "addLaboratory" (opció 1), el cost realment 
		 * serà insignificant */
		
		int n;
		for (n=0;n<this.numLaboratories;n++) {
			if (this.laboratories[n].getIdLaboratory().equals(idLaboratory)) {
				return this.laboratories[n];
			}
		}
		return null;
	}

	@Override
	public void newSample(String idSample, String idUser, String idClinician, Date date)
			throws ClinicianNotFoundException, UserNotFoundException, TrialNotFoundException {
		
		/* Tal i com s'ha definit al contracte, el TAD serà responsable de 
		 * comprovar que l'usuari i l'especialista i que l'usuari està assignat 
		 * a un trial 
		 * 
		 * OBSERVACIÓ IMPORTANT: A La PAC2 "newSample" reb com a paràmetre "TrialID" 
		 * mentre que a l'enunciat de la PRAC no --> Preguntar al fòrum 
		 * */
		
		User user = getUser(idUser);
		if (user == null) {
			throw new UserNotFoundException();
		}
		
		Clinician clinician = getClinician(idClinician);
		if (clinician == null) {
			throw new ClinicianNotFoundException();
		}
		
		if (user.getTrial()==null) {
			throw new TrialNotFoundException();
		}
		
		/* Finalment podem procedir a afegir la nova mostra a la cua de mostres 
		 * pendents (en estat PENDING) */
		
		Sample sample = new Sample(idSample,user,clinician,date);
		this.pendingSamples.encuar(sample);
	}

	@Override
	public Sample sendSample(Date date) throws NOSAmplesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sample processSample(String idLaboratory, Date date, String report)
			throws LaboratoryNotFoundException, NOSAmplesException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Iterador<Sample> samplesByTrial(int idTrial) throws TrialNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Iterador<Sample> samplesByUser(String idUser) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Iterador<Sample> samplesByClinician(String idClinician) throws ClinicianNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Sample getSample(String idSample) throws SampleNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Clinician mostActiveClinician() throws NOClinicianException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int numClinician() {
		return this.clinicians.nombreElems();
	}


	@Override
	public int numSamples() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int numSamplesByClinician(String idClinician) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int numSamplesByUser(String idUser) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int numSamplesByTrial(int idTrial) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int numPendingSamplesByLaboratory(String idLaboratory) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int numLaboratories() {
		return this.numLaboratories;
	}
	

/****
 * 
 * 
 * 	AIXO POT SER EL MÈTODE DE SELECCIONAR SEGÜENT LAB PER A ENVIAR LA MOSTRA 
 * 
 *  --> Sample sendSample(date);
 *  
 *  On el paràmetre d'entrada laboratoryID correspon al valor rebut --> this.laboratories[nextLabo].getIdLabo()
 * 
 */
	
	public Object XXXXXXXXX(String XXXXXXXX) {
		
		/* Desem l'index (següent lab a enviar mostres) a la var auxiliar "n", 
		 * ja que hem d'actualitzar el valor de "nextLaboratory" abans de 
		 * retornar el valor */
		int n = this.nextLaboratory;
		if (this.nextLaboratory == this.numLaboratories) {
			/* Si el següent laboratori és el darrer del vector, actualitzem 
			 * l'index "nextLaboratory" per a que apunti al primer element del 
			 * vector (implementació de recorregut circular) */
			this.nextLaboratory = 0;
		}
		else {
			/* Si no, alehores incrementem el valor per a apuntar a la següent 
			 * posició del vector*/
			this.nextLaboratory++;
		}
		return this.laboratories[n];
	}
}
