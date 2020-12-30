package uoc.ded.practica.model;

import uoc.ei.tads.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class User implements Comparable<User>{
    public static final Comparator<String> CMP = new Comparator<String>() {
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    } ;

    private String id;
    private String name;
    private String surname;
    private Date birthday; /* UPGRADE: Atribut afegit per les noves especificacions indicades a la PAC2 */
    private char level; /* UPGRADE: Atribut afegit per les noves especificacions indicades a la PAC2 */
    private boolean active;
    private LlistaEncadenada<Answer> answers;
    private Trial trial;
    private Cua<Question> questions;
    private LlistaEncadenada<Sample> samples; /* UPGRADE: Afegim una LLISTA ENCADENADA DE MOSTRES per tal d'emmagatzemar les mostres de cada usuari */

    public User(String idUser, String name, String surname) {
        this.setId(idUser);
        this.setName(name);
        this.setSurname(surname);
        this.active = false;
        this.answers = new LlistaEncadenada<Answer>();
        this.trial = null;
        this.questions = new CuaVectorImpl<Question>();
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


    public int compareTo(User o) {
        return this.getId().compareTo(o.getId());
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

	public char getLevel() {
		return this.level;
	}

	/* Codi per a calcular la edat obtingut de -> https://www.baeldung.com/java-get-age */ 
	
	public int years(Date now) {
	    DateFormat formatter = new SimpleDateFormat("yyyyMMdd");                           
	    int d1 = Integer.parseInt(formatter.format(this.birthday));                            
	    int d2 = Integer.parseInt(formatter.format(now));                          
	    int age = (d2 - d1) / 10000;                                                       
	    return age; 
	}
}
