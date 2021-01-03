package uoc.ded.practica.model;

import uoc.ei.tads.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import uoc.ded.practica.Trial4C19.Level;

public class User implements Comparable<User>{
    public static final Comparator<String> CMP = new Comparator<String>() {
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    } ;

    private String id;
    private String name;
    private String surname;
    private Date birthday; /* UPGRADE #1: Atribut afegit per les noves especificacions indicades a la PAC2 */
    private Level level; /* UPGRADE #2: Atribut afegit per les noves especificacions indicades a la PAC2 */
    private boolean active;
    private LlistaEncadenada<Answer> answers;
    private Trial trial;
    private Cua<Question> questions;
    private LlistaEncadenada<Sample> samples; /* UPGRADE #3: Afegim una LLISTA ENCADENADA DE MOSTRES per tal d'emmagatzemar les mostres de cada usuari */

    public User(String idUser, String name, String surname) {
        this.setId(idUser);
        this.setName(name);
        this.setSurname(surname);
        this.active = false;
        this.answers = new LlistaEncadenada<Answer>();
        this.trial = null;
        this.questions = new CuaVectorImpl<Question>();
    }
    
	/* UPGRADE #4: Implementem un constructor nou tenint en compte el nous atributs (UPGRADES #1 a #3) */
    
    public User(String idUser, String name, String surname, Date birthday, Level level) {
        this.setId(idUser);
        this.setName(name);
        this.setSurname(surname);
        this.setDateOfBirth(birthday); /* REF -> UPGRADE #1 */
        this.setLevel(level); /* REF -> UPGRADE #2 */
        this.active = false;
        this.answers = new LlistaEncadenada<Answer>();
        this.trial = null;
        this.questions = new CuaVectorImpl<Question>();
        this.samples = new LlistaEncadenada<Sample>(); /* UPGRADE #3 */
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }


    public void setActive() {
        this.active = true;
    }

    public boolean isActive() {
        return this.active;
    }

    public void addAnswer(Question q, String answer) {
        Answer theAnswer = new Answer(q, answer);
        this.answers.afegirAlFinal(theAnswer);
        this.trial.incNumAnswers();
        this.trial.updateMostActiveUser(this);
    }

    public void setTrial(Trial trial) {
        this.trial = trial;
        Iterador<QuestionGroup> itQG =  trial.questionGroups();
        Iterador<Question> itQ = null;
        while (itQG.hiHaSeguent()) {
            itQ = itQG.seguent().questions();
            while (itQ.hiHaSeguent()) {
                this.questions.encuar(itQ.seguent());
            }
        }
    }

    public Iterador<Answer> answers() {
        return this.answers.elements();
    }

    public Question nextQuestion() {
        Question q = this.questions.desencuar();
        return q;
    }

    public Question currentQuestion() {
        return this.questions.primer();
    }

    public Trial getTrial() {
        return this.trial;
    }

    public int numAnswers()  {
        return this.answers.nombreElems();
    }
    
    /* REF -> UPGRADE #1 */
    
	public void setDateOfBirth(Date birthday) {
		this.birthday = birthday;		
	}
	
    /* REF -> UPGRADE #2 */ 
	
    public void setLevel(Level level) {
		this.level = level;
	}

	public Level getLevel() {
		return this.level;
	}

	/* UPGRADE #5: Mètode per a calcular la edat, obtingut de -> https://www.baeldung.com/java-get-age */ 
	
	public int years(Date now) {
	    DateFormat formatter = new SimpleDateFormat("yyyyMMdd");                           
	    int d1 = Integer.parseInt(formatter.format(this.birthday));                            
	    int d2 = Integer.parseInt(formatter.format(now));                          
	    int age = (d2 - d1) / 10000;                                                       
	    return age; 
	}
	
	/* UPGRADE #6: Mètode per a afegir una nova mostra extreta a l'usuari a la llista
	 * encadenada de mostres de l'usuari */
	
	public void addSample(Sample s) {
		this.samples.afegirAlFinal(s);
	}

	public int getNumSamples() {
		return this.samples.nombreElems();
	}
	
    public int compareTo(User o) {
        return this.getId().compareTo(o.getId());
    }
    
    /* UPGRADE #7: Mètode que retorna les mostres d'un usuari */

	public Iterador<Sample> getSamples() {
		return this.samples.elements();
	}
}
