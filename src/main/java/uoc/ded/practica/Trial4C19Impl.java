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
    private DiccionariAVLImpl<String, Sample> samples;
    private CuaAmbPrioritat<Sample> pendingSamples;
    private TaulaDispersio<String, Clinician> clinicians;
    private Laboratory[] laboratories;
    private Trial mostActiveTrial;
    private Clinician mostActiveClinician;

    public Trial4C19Impl() {
        this.users = new DiccionariOrderedVector<String, User>(U, User.CMP);
        this.trials = new Trial[T];
        this.groups = new OrderedVector<QuestionGroup>(G, QuestionGroup.CMP);
        /*
         * Per a la cua amb prioritat on desarem les mostres pendents, del TAD 
         * "CuaAmbPrioritat()" de la biblioteca utilitzarem el Constructor amb un 
         * paràmetre (capacitat màxima, per defecte) i elements d'una classe 
         * comparable amb el comparador donat.
         * */
        this.samples = new DiccionariAVLImpl<String, Sample>();
        this.pendingSamples = new CuaAmbPrioritat<Sample>(Sample.CMP);
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
		 *  atribut "numLaboratories" al TAD Trial4C19 que mantindrem sempre 
		 *  actualitzat amb el nombre de laboratoris total i que ens servirà per 
		 *  a saber la posició del vector en la que hem d'afegir el laboratori 
		 *  (la següent a la darrera, ja que no eliminarem mai laboratoris i les 
		 *  posicions del vector utilitzades són contigues).
		 *  
		 *  Obviament, escollirem la opció #2 ja que el cost espacial d'emmagatzemar
		 *  numLaboratory és insignificant i la millora en eficiència temporal és 
		 *  substancial respecte de la obtinguda amb la opció #1. */
		
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
		 * explicat en el cas de "addLaboratory" (opció #1), el cost realment 
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
		 * OBSERVACIÓ: A La PAC2 "newSample" reb com a paràmetre "TrialID" 
		 * mentre que a l'enunciat de la PRAC no --> Preguntar al fòrum?
		 * */
		
		User user = this.getUser(idUser);
		if (user == null) {
			throw new UserNotFoundException();
		}
		
		Clinician clinician = this.getClinician(idClinician);
		if (clinician == null) {
			throw new ClinicianNotFoundException();
		}
		
		Trial trial = user.getTrial();
		if (trial==null) {
			throw new TrialNotFoundException();
		}
		
		/* Ara podem procedir a afegir la nova mostra a l'arbre AVL de mostres global,
		 * a la cua de mostres en estat PENDING, així com a les estructures 
		 * corresponents pel que respecta als usuaris, especialistes i assaigs. */
		
		Sample s = new Sample(idSample,user,clinician,date);
		this.samples.afegir(idSample, s);
		this.pendingSamples.encuar(s);
		clinician.addSample(s);
		user.addSample(s);
		trial.addSample(s);
		
		/* Finalment no hem d'oblidar comprovar si cal actualitzar l'especialista més 
		 * actiu. Utilitzarem el mètode auxiliar "updateMostActiveClinician()" 
		 * implementat més avall */
		
		updateMostActiveClinician(clinician);
		
	}
	
	/* CUSTOM / AUX METHOD */
    public void updateMostActiveClinician(Clinician c) {
        if (this.mostActiveClinician == null) this.mostActiveClinician = c;
        else if ( this.mostActiveClinician.getNumSamples() < c.getNumSamples() ) this.mostActiveClinician = c;
    }

	@Override
	public Sample sendSample(Date date) throws NOSAmplesException {
		
		/* En primer lloc obtenim (desencuem) la mostra més prioritària de la cua de 
		 * mostres pendents d'enviar i la desem a una variable local */
		
		Sample s = this.pendingSamples.desencuar();
		
		/* Si no hi ha cap mostra a la cua hem d'informar de l'error */
		
		if (s == null) {
			throw new NOSAmplesException();
		}
		
		/* Si hem pogut obtenir la següent mostra a enviar, la hem d'assignar a la 
		 * cua FIFO de mostres enviades (estat "SENDED") del següent laboratori en el 
		 * circuit de rotacions. Abans però, caldrà establir el nou estat de la mostra 
		 * i afegir la data d'enviment de la mateixa */
		
		s.setStatus(Status.SENDED);
		s.setDateSended(date);
		this.laboratories[this.nextLaboratory].addSample(s);
				
		/* Finalment caldrà actualitzar l'apuntador al següent laboratory */
				
		if (this.nextLaboratory == this.numLaboratories-1) {
			/* Si el laboratori actual és el darrer del vector, actualitzem 
			 * l'index "nextLaboratory" per a que apunti al primer element del 
			 * vector (implementació de recorregut circular) */
			this.nextLaboratory = 0;
		}
		else {
			/* Si no, aleshores incrementem el valor per a apuntar a la següent 
			 * posició del vector*/
			this.nextLaboratory++;
		}
		
		/* No hem d'oblidar que el mètode ha de retornar la mostra enviada */
		
		return s;
	}

	@Override
	public Sample processSample(String idLaboratory, Date date, String report)
			throws LaboratoryNotFoundException, NOSAmplesException {
		
        /* En primer lloc cerquem el laboratori i si el trobem desem la posició del 
         * vector en la que es troba. Si no, informem de l'error */
		
		int pos = findLabIndex(idLaboratory);
		if (pos == -1) throw new LaboratoryNotFoundException();
		
		/* Seguidament desencuem de la cua FIFO de mostres enviades pendents de 
		 * processar als laboratoris la següent mostra a processar. Si no hi ha 
		 * mostres a la cua de mostres del lab, aleshores informem de l'error. */
		
		Sample s = this.laboratories[pos].getNextSample();
		if (s==null) throw new NOSAmplesException();
		
		/* Finalment li hem de canviar l'estat a la mostra processada, així com
		 * afegir-hi la data de processament i el report corresponent*/
		
		s.setDateCompleted(date);
		s.setStatus(Status.COMPLETED);
		s.setReport(report);
		
		return s;
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
		Sample s = this.samples.consultar(idSample);
		if (s == null) throw new SampleNotFoundException();
		else return s;
	}


	@Override
	public Clinician mostActiveClinician() throws NOClinicianException {
		if (this.mostActiveClinician==null) {
			throw new NOClinicianException();
		}
		else {
			return this.mostActiveClinician;
		}
	}


	@Override
	public int numClinician() {
		return this.clinicians.nombreElems();
	}


	@Override
	public int numSamples() {
		return this.pendingSamples.nombreElems();
	}


	@Override
	public int numSamplesByClinician(String idClinician) {
		return this.clinicians.consultar(idClinician).getNumSamples();
	}


	@Override
	public int numSamplesByUser(String idUser) {
		return this.users.consultar(idUser).getNumSamples();
	}


	@Override
	public int numSamplesByTrial(int idTrial) {
		return this.trials[idTrial].getNumSamples();
	}


	@Override
	public int numPendingSamplesByLaboratory(String idLaboratory) {
		
		/* Com que hi ha pocs elements al vector, no considerem el cost de fer el 
		 * recorregut del vector de labs (tal i com hem indicat repetides vegades) */
		
		return this.laboratories[findLabIndex(idLaboratory)].getNumSamples();
	}


	@Override
	public int numLaboratories() {
		return this.numLaboratories;
	}
	
	
	/* AUX Methods */ 
	
	
	private int findLabIndex(String idLaboratory) {
		int i = 0;
		while (this.laboratories[i] != null && i < this.laboratories.length) {
			if ( this.laboratories[i].getIdLaboratory().equals(idLaboratory) ){
				return i;
			}
			else i++;
		}
		return -1;
	}

}
