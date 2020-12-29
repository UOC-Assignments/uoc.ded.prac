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
    private Trial mostActiveTrial;

    public Trial4C19Impl() {
        this.users = new DiccionariOrderedVector<String, User>(U, User.CMP);
        this.trials = new Trial[T];
        this.groups = new OrderedVector<QuestionGroup>(G, QuestionGroup.CMP);
        this.mostActiveTrial = null;
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


}
