package uoc.ded.practica;

import uoc.ded.practica.exceptions.*;
import uoc.ded.practica.model.*;
import uoc.ei.tads.Iterador;

import java.util.Date;

/**
 * Definició del TAD de gestió de la plataforma d'assajos clínics
 */
public interface Trial4C19 {


    /**
     * dimensió del contenidor d'usuaris
     */
    public static final int U = 560;

    /**
     * dimensió del contenidor d'assajos
     */
    public static final int T = 60;

    /**
     * dimensió del contenidor de grups de preguntas
     */
    public static final int G = 20;



    enum Type {
        TEXT_PLAIN,
        LIKERT
    }

    enum Priority {
        LOWER,
        MEDIUM,
        HIGH
    }

    /**
     * Mètode que afegeix un nou usuari al sistema
     *
     * @param idUser  identificador de l'usuari
     * @param name    nom de l'usuari
     * @param surname cognom de l'usuari
     * @pre cert
     * @post si el codi d’usuari és nou, els usuaris seran els mateixos
     * més un nou usuari amb les dades indicades. Sinó les dades
     * de l’usuari s’hauran actualitzat amb les noves.
     */
    public void addUser(String idUser, String name, String surname);


    /**
     * Mètode que afegeix un nou assaig clínic al sitema
     * @param idTrial identificador de l'assaig clínic
     * @param description descripció de l'assaig clínic
     *
     * @pre cert
     * @post Si el codi de l'assaig clínic no existeix els assajos clínics
     * seran els mateixos més un nou amb les dades indicades. Sinó caldrà informar de l'error
     */
    public void addTrial (int idTrial, String description) throws TrialAlreadyExistsException;

    /**
     * Mètode que afegeix un nou grup de preguntes al sistema
     * @param idQuestionGroup identificador del grup de preguntes
     * @param priority prioritat del grup de preguntes
     *
     * @pre cert
     * @post si el codi de grup de preguntes és nou, els grups de preguntes
     * seran els mateixos més un nou amb les dades indicades. Sinó les dades
     * del grup de preguntes s’hauran actualitzat amb les noves.
     */
    public void addQuestionGroup(String idQuestionGroup, Priority priority);

    /**
     *
     * @param idQuestion identificador de la pregunta
     * @param wording texto de la pregunta
     * @param type tipo de respuestas (texto libre o Likert)
     * @param choices opcions disponibles per la pregunta
     * @param idGroup identificador del grupo de preguntas
     *
     *  @pre cert
     *  @post Si el codi de pregunta és nou, les preguntes seran les mateixes més una nova.
     *  Sinó  les dades de la pregunta s'hauran actualitzat amb les noves. Si el grup de preguntes
     *  no existeix caldrà infomar de l'error
     *
     */
    public void addQuestion(String idQuestion, String wording, Type type, String[] choices, String idGroup)
            throws QuestionGroupNotFoundException;

    /**
     * Mètode que proporciona les preguntes d'un group de preguntes
     * @param idGroup grup de preguntes
     * @return retorna un iterador de preguntes
     *
     * @pre cert.
     * @post retorna un iterador per recòrrer les preguntes d'un grup de preguntes
     */
    public Iterador<Question> getQuestions(String idGroup) throws QuestionGroupNotFoundException;


    /**
     * Mètode que permet afegir un grup de preguntes a un assaig clínic
     * @param idGroup identificador del grup de preguntes
     * @param idTrial identificador de l'assaig clínic
     *
     * @pre cert
     * @post s'actualitza l'estructura de preguntes de l'assaig clínic. Si el grup o assaig
     * clínic no existeix, caldrà informar de l'error
     */
    public void assignQuestionGroup2Trial(String idGroup, int idTrial)
            throws QuestionGroupNotFoundException, TrialNotFoundException;

    /**
     * Mètode que permet assignar un usuari a un assaig clínic
     * @param idTrial identificador de l'assaig clínic
     * @param idUser identificador de l'usuari
     *
     * @pre l'usuari i l'assaig clínic existeixen
     * @post s'actualitza l'estructura d'usuaris d'un assaig clínic. Si l'usuari ja
     * pertany a un assaaig clínic caldrà informar de l'error
     */
    public void assignUser2Trial(int idTrial, String idUser)
            throws UserIsAlreadyInTrialException;

    /**
     * Mètode que proporciona la següent pregunta a respondre
     * @param idUser identificador de l'usuari
     * @return retorna la següent pregunta a respondre
     *
     * @pre cierto
     * @post retorna la següent pregunta a respondre. En cas que l'usuari no existeixi
     * caldrà mostrar l'error. En cas que no hi hagi preguntes retorna un null
     */
    public Question getCurrentQuestion(String idUser) throws UserNotFoundException;

    /**
     * Mètode que permet respondre a preguntes d'un assaig clínic
     * @param idUser identificador de l'usuari
     * @param date data en la que es produeix la resposta
     * @param answer la resposta
     *
     * @pre cert
     * @post el nombre de respostes d'un usuari serà el mateix més la nova resposta. Si
     * l'usuari no existeix caldrà mostrar l'error.
     */
    public void addAnswer(String idUser, Date date, String answer)
        throws UserNotFoundException, NoQuestionsException;


    /**
     * Mètode que proporciona les respostes d'un usuari ordenades per data
     * @param idUser identificador de l'usuari
     * @return retorna un iterador de les respostes d'un usuari
     *
     * @pre cert
     * @post retora un iterador ordenat de respostes. En cas que l'usuari no existei o
     * no hi hagi preguntes caldrà informar de l'error
     */
    public Iterador<Answer> getAnswers(String idUser)
            throws UserNotFoundException, NoAnswersException;


    /**
     * Mètode que proporciona l'usuari que ha realitzat més respostes d'un assaig clínic
     * @param idTrial identificador de l'assaig
     * @return retorna l'usuari més actiu dins un assaig clínic
     *
     * @pre l'assaig clínic existeix
     * @post retorna l'usuari més actiu d'un assaig
     */
    public User mostActiveUser(int idTrial);

    /**
     * Mètode que proporciona l'assaig clínic amb més participació
     *
     * @pre cert
     * @post retorna l'assaig que més respostes ha generat
     *
     * @return retorna l'assaig clínic amb més participació
     */
    public Trial mostActiveTrial();

    /**
     * Mètode que proporciona el nombre d'usuaris
     * @return retorna el total d'usuaris
     */
    public int numUsers();


    /**
     * Mètode que proporciona el nombre d'assajos clínics
     * @return retorna el total d'assajos clínics
     */
    public int numTrials();


    /**
     * Mètode que proporciona el nombre de grups de preguntes
     * @return retorna el total de nombre de preguntes
     */
    public int numQuestionGroups();

    /**
     * Mètode que proporciona el nombre de preguntes d'un grup de preguntes
     * @param idGroup identificador del grup
     * @return retorna el nombre de preguntes d'un grup. En cas que el grup no existeixi retorna 0
     */
    public int numQuestion4Group(String idGroup);

    /**
     * Mètode que proporciona el nombre de grups de preguntes d'un assaig
     * @param idTrial identificador de l'assaig clínic
     * @return retorna el nombre de grups o 0 i no existeix l'assaig clínic
     */
    public int numQuestionGroups4Trial(int idTrial);


    /**
     * Mètode que proporciona el nombre d'usuaris d'un assaig clínic
     * @param idTrial identificador de l'assaig clínic
     * @return retorna el nombre d'usuaris d'un assaig clínic o 0 si no existeix l'assaig
     */
    public int numUsers4Trial(int idTrial);

    /**
     * Mètode que proporciona un usuari identificat
     *
     * @param idUser identificador de l'usuari
     * @return retorna l'usuari identificcat per idUser o null en cas que no existeixi
     */
    public User getUser(String idUser);

    /**
     * Mètode que proporciona els grups de preguntes existents en el sistema
     * @return retorna un iterador amb els grups de preguntes
     */
    public Iterador<QuestionGroup> getQuestionGroups();

}


