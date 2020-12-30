package uoc.ded.practica.model;

import uoc.ded.practica.Trial4C19;
import uoc.ded.practica.util.OrderedVector;
import uoc.ei.tads.Iterador;
import uoc.ei.tads.LlistaEncadenada;

public class Trial {
    private int idTrial;
    private String description;
    private LlistaEncadenada<User> users;
    private OrderedVector<QuestionGroup> groups;
    private LlistaEncadenada<Sample> samples;
    private User mostActiveUser;
    private int numAnswers;

    public Trial(int idTrial, String description) {
        this.idTrial = idTrial;
        this.description = description;
        this.users = new LlistaEncadenada<User>();
        this.groups = new OrderedVector<QuestionGroup>(Trial4C19.G, QuestionGroup.CMP);
        this.samples = new LlistaEncadenada<Sample>();
        this.mostActiveUser = null;
    }

    public void addQuestionGroup(QuestionGroup qg) {
        this.groups.update(qg);
    }

    public int numQuestionGroups() {
        return this.groups.nombreElems();
    }

    public void addUser(User user) {
        this.users.afegirAlFinal(user);
    }

    public int numUsers() {
        return this.users.nombreElems();
    }

    public User mostActiveUser() {
        return this.mostActiveUser;
    }

    /*public Question firstQuestion() {
        Question q = null;
        QuestionGroup qg = this.groups.elementAt(0);
        if (qg != null) q= qg.firstQuestion();

        return q;
    }
    */

    public Iterador<QuestionGroup> questionGroups() {
        return this.groups.elements();
    }

    public void incNumAnswers() {
        this.numAnswers++;
    }

    public int numAnswers() {
        return this.numAnswers;
    }

    public void updateMostActiveUser(User user) {
        if (this.mostActiveUser == null) this.mostActiveUser = user;
        else if (this.mostActiveUser.numAnswers()<user.numAnswers()) this.mostActiveUser = user;
    }
}
