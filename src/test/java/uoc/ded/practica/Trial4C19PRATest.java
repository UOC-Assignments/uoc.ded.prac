package uoc.ded.practica;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uoc.ded.practica.exceptions.*;
import uoc.ded.practica.model.*;
import uoc.ded.practica.util.DateUtils;
import uoc.ei.tads.Iterador;

import java.util.Date;

public class Trial4C19PRATest {

    private Trial4C19 trial4C19;

    @Before
    public void setUp() throws Exception {
        this.trial4C19 = FactoryTrial4C19.getTrial4C19();
    }

    @After
    public void tearDown() {
        this.trial4C19 = null;
    }

    /**********************************************************************
     * 
     *                      		TESTS EP2 
     *                      
     **********************************************************************/
    
    /**
     * *feature*: (sobre la que fem @test): addUser del TAD TrialC19
     * *given*: Hi ha 10 usuaris en el sistema
     * *scenario*:
     * - S'afegeixen 10 usuaris més de manera desordenada
     * - Es modifiquen les dades del CINQUÉ usuari inserit (idUser9999)
     * 
     * Donat que s'ha ampliat el nombre d'atributs que contindrà cada usuari (level 
     * i birthdate) 
     */
    
    @Test
    public void testAddUser() {

        // GIVEN:
        Assert.assertEquals(10, this.trial4C19.numUsers());
        //

        this.trial4C19.addUser("idUser1000", "Robert", "Lopez", createDate("02-01-1942 00:00:00"), Trial4C19.Level.C);
        Assert.assertEquals("Robert", this.trial4C19.getUser("idUser1000").getName());
        Assert.assertEquals(11, this.trial4C19.numUsers());

        this.trial4C19.addUser("idUser9999", "XXXXX", "YYYYY", createDate("12-11-1962 00:00:00"), Trial4C19.Level.D);
        Assert.assertEquals("XXXXX", this.trial4C19.getUser("idUser9999").getName());
        Assert.assertEquals(12, this.trial4C19.numUsers());

        this.trial4C19.addUser("idUser9999", "Lluis", "Casals", createDate("22-07-1938 00:00:00"), Trial4C19.Level.C);
        Assert.assertEquals("Lluis", this.trial4C19.getUser("idUser9999").getName());
        Assert.assertEquals("Casals", this.trial4C19.getUser("idUser9999").getSurname());
        Assert.assertEquals(12, this.trial4C19.numUsers());
    }


    /**
     * *feature*: (sobre la que fem @test): addTrial del TAD Trial4C19
     * *given*: Hi ha 6 Assajos en el sistema
     * *scenario*:
     * - S'afegeix un nou assaig en el sistema
     * - S'afegeix un segon assaig en el sistema
     */
    @Test
    public void testAddTrial()
            throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        //

        this.trial4C19.addTrial(22, "Description 22");
        this.trial4C19.addTrial(6, "Description 6");
        Assert.assertEquals(8, this.trial4C19.numTrials());
    }

    /**
     * *feature*: (sobre la que fem @test): addTrial del TAD Trial4C19
     * *given*: Hi ha 6 assajos en el sistema
     * *scenario*:
     * - S'afegeix un nou assaig en el sistema
     * - S'afegeix un segon assaig en el sistema que ja existeix
     */
    @Test(expected = TrialAlreadyExistsException.class)
    public void testAddTrialAlreadyExists()
            throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        //

        this.trial4C19.addTrial(22, "Description 22222");
        Assert.assertEquals(7, this.trial4C19.numTrials());
        this.trial4C19.addTrial(22, "Description 22222");

    }


    /**
     * *feature*: (sobre la que fem @test): AddQuestionGroup del TAD Trial4C19
     * *given*: Hi ha 6 assajos en el sistema i tres grups de preguntes
     * <p>
     * *scenario*:
     * - S'afegeix un nou grup de preguntes
     */
    @Test
    public void testAddQuestionGroup() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        //
        trial4C19.addQuestionGroup("hygiene", Trial4C19.Priority.LOWER);


        Assert.assertEquals(4, this.trial4C19.numQuestionGroups());

        Iterador<QuestionGroup> it = this.trial4C19.getQuestionGroups();
        QuestionGroup qg1 = it.seguent();
        QuestionGroup qg2 = it.seguent();
        QuestionGroup qg3 = it.seguent();
        QuestionGroup qg4 = it.seguent();

        Assert.assertEquals("symptoms", qg1.getIdGroup());
        Assert.assertEquals(Trial4C19.Priority.HIGH, qg1.getPriority());

        Assert.assertEquals("habits", qg2.getIdGroup());
        Assert.assertEquals(Trial4C19.Priority.MEDIUM, qg2.getPriority());

        Assert.assertEquals("wellness", qg3.getIdGroup());
        Assert.assertEquals(Trial4C19.Priority.LOWER, qg3.getPriority());

        Assert.assertEquals("hygiene", qg4.getIdGroup());
        Assert.assertEquals(Trial4C19.Priority.LOWER, qg4.getPriority());
    }

    /**
     * *feature*: (sobre la que fem @test): AddQuestion del TAD Trial4C19
     * *given*: Hi ha 6 assajos en el sistema,  tres grups de preguntes i tres preguntes per grup
     * <p>
     * *scenario*:
     * - S'afegeix una nova pregunta
     */
    @Test
    public void testAddQuestion() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        //
        trial4C19.addQuestionGroup("hygiene", Trial4C19.Priority.LOWER);
        Assert.assertEquals(0, this.trial4C19.numQuestion4Group("hygiene"));

        Assert.assertEquals(4, this.trial4C19.numQuestionGroups());


        trial4C19.addQuestion("idQuestion100", "theWording", Trial4C19.Type.TEXT_PLAIN, null, "hygiene");
        Assert.assertEquals(1, this.trial4C19.numQuestion4Group("hygiene"));

    }

    /**
     * *feature*: (sobre la que fem @test): AddQuestion del TAD Trial4C19
     * *given*: Hi ha 6 assajos en el sistema,  tres grups de preguntes i tres preguntes per grup
     * <p>
     * *scenario*:
     * - S'afegeix un nou grup de preguntes sobre un grup de preguntes inexistent
     */
    @Test(expected = QuestionGroupNotFoundException.class)
    public void testAddQuestionQuestionGroupNotFound() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        //
        trial4C19.addQuestion("idQuestion100", "theWording", Trial4C19.Type.TEXT_PLAIN, null, "XXXXXXXXXX");
    }

    /**
     * *feature*: (sobre la que fem @test): AddQuestion del TAD Trial4C19
     * *given*: Hi ha 6 assajos en el sistema,  tres grups de preguntes i tres preguntes per grup
     * <p>
     * *scenario*:
     * - es consulten les preguntes d'un grup de preguntes
     */
    @Test
    public void testGetQuestions() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        //
        Iterador<Question> it = trial4C19.getQuestions("symptoms");

        Question q1 = it.seguent();
        Assert.assertEquals("idQuestion1a", q1.getIdQuestion());

        Question q2 = it.seguent();
        Assert.assertEquals("idQuestion1b", q2.getIdQuestion());

        Question q3 = it.seguent();
        Assert.assertEquals("idQuestion1c", q3.getIdQuestion());

    }

    /**
     * *feature*: (sobre la que fem @test): AddQuestion del TAD Trial4C19
     * *given*: Hi ha 6 assajos en el sistema,  tres grups de preguntes i tres preguntes per grup
     * <p>
     * *scenario*:
     * - es consulten les preguntes d'un grup de preguntes INEXISTENT
     */
    @Test(expected = QuestionGroupNotFoundException.class)
    public void testGetQuestionsAndQuestionGroupNotFound() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        //
        Iterador<Question> it = trial4C19.getQuestions("XXXXXX");
    }

    /**
     * *feature*: (sobre la que fem @test): assignQuestionGroup2Trial del TAD Trial4C19
     * *given*: Hi ha:
     *  - 6 assajos en el sistema
     *  - tres grups de preguntes
     *  - tres preguntes per grup
     *  - 3 grups de preguntes a l'assaig 1
     *  - 1 grups de preguntes a l'assaig 2
     * <p>
     * *scenario*:
     * - S'assigna un segon grup de preguntes a un assaig clínic (2)
     */
    @Test
    public void testAssignQuestionGroup2Trial() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        Assert.assertEquals(3, trial4C19.numQuestionGroups4Trial(1));
        Assert.assertEquals(1, trial4C19.numQuestionGroups4Trial(2));
        //

        trial4C19.assignQuestionGroup2Trial("symptoms", 2);

        Assert.assertEquals(3, trial4C19.numQuestionGroups4Trial(1));
        Assert.assertEquals(2, trial4C19.numQuestionGroups4Trial(2));
    }

    /**
     * *feature*: (sobre la que fem @test): assignQuestionGroup2Trial del TAD Trial4C19
     * given*: Hi ha:
     *  - 6 assajos en el sistema
     *  - tres grups de preguntes
     *  - tres preguntes per grup
     *  - 3 grups de preguntes a l'assaig 1
     *  - 1 grups de preguntes a l'assaig 2
     * <p>
     * *scenario*:
     * - S'assigna un  grup de preguntes INEXISTENT a un assaig clínic
     */
    @Test(expected = QuestionGroupNotFoundException.class)
    public void testAssignQuestionGroup2TriaAndQuestionGroupNotFoundl() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        Assert.assertEquals(3, trial4C19.numQuestionGroups4Trial(1));
        Assert.assertEquals(1, trial4C19.numQuestionGroups4Trial(2));

        //
        trial4C19.assignQuestionGroup2Trial("XXXXX", 1);
    }

    /**
     * *feature*: (sobre la que fem @test): assignQuestionGroup2Trial del TAD Trial4C19
     * *given*: Hi ha 6 assajos en el sistema,  tres grups de preguntes i tres preguntes per grup
     * <p>
     * *scenario*:
     * - S'assigna un  grup de preguntes a un assaig clínic INEXISTENT
     */
    @Test(expected = TrialNotFoundException.class)
    public void testAssignQuestionGroup2TriaAndTrialNotFoundl() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        Assert.assertEquals(3, trial4C19.numQuestionGroups4Trial(1));
        Assert.assertEquals(1, trial4C19.numQuestionGroups4Trial(2));

        //
        trial4C19.assignQuestionGroup2Trial("habits", 50);
    }

    /**
     * *feature*: (sobre la que fem @test): assignUser2Trial del TAD Trial4C19
     * given Hi ha:
     *  - 6 assajos en el sistema
     *  - tres grups de preguntes
     *  - tres preguntes per grup
     *  - 3 grups de preguntes a l'assaig 1
     *  - 1 grups de preguntes a l'assaig 2
     *  - 3 usuaris assignats a l'assaig 1
     *  - 1 usuari assignat a l'assaig 2
     *  <p>
     * *scenario*:
     * - S'assigna un usuari a un assaig clínic
     * - S'assigna un segon usuari a un assaig clínic
     * - S'assigna un tercer usuari a un assaig clínic
     * - S'assigna un primer usuari a un nou assaig clínic
     */
    @Test
    public void testAssignUser2Trial() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        Assert.assertEquals(6, trial4C19.numUsers4Trial(1));
        Assert.assertEquals(3, trial4C19.numUsers4Trial(2));
        //
        trial4C19.assignUser2Trial(1, "idUser10");


        Assert.assertEquals(7, trial4C19.numUsers4Trial(1));
        Assert.assertEquals(3, trial4C19.numUsers4Trial(2));
    }

    /**
     * *feature*: (sobre la que fem @test): assignUser2Trial del TAD Trial4C19
     * given Hi ha:
     *  - 6 assajos en el sistema
     *  - tres grups de preguntes
     *  - tres preguntes per grup
     *  - 3 grups de preguntes a l'assaig 1
     *  - 1 grups de preguntes a l'assaig 2
     *  - 3 usuaris assignats a l'assaig 1
     *  - 1 usuari assignat a l'assaig 2
     *  <p>
     * *scenario*:
     * - S'assigna un usuari a un assaig clínic
     */
    @Test(expected = UserIsAlreadyInTrialException.class)
    public void testAssignUser2TrialAndUserAlreadyInTrial() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        Assert.assertEquals(6, trial4C19.numUsers4Trial(1));
        Assert.assertEquals(3, trial4C19.numUsers4Trial(2));
        //
        trial4C19.assignUser2Trial(2, "idUser1");
    }

    /**
     * *feature*: (sobre la que fem @test): getCurrentQuestion del TAD Trial4C19
     * given
     *  Hi ha:
     *  - 6 assajos en el sistema
     *  - tres grups de preguntes
     *  - tres preguntes per grup
     *  - 3 grups de preguntes a l'assaig 1
     *  - 1 grups de preguntes a l'assaig 2
     *  - 3 usuaris assignats a l'assaig 1
     *  - 1 usuari assignat a l'assaig 2
     * <p>
     * *scenario*:
     *  - Es consulta la primera pregunta
     *  - Es consulta la següent pregunta
     *  - Es consulta la tercera pregunta

     */
    @Test
    public void testAnswerQuestions() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        Assert.assertEquals(6, trial4C19.numUsers4Trial(1));
        Assert.assertEquals(3, trial4C19.numUsers4Trial(2));
        //

        Question q1 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion1a", q1.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 17:00:00"), "NO");

        Question q2 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion1b", q2.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 17:15:00"), "YES");

        Question q3 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion1c", q3.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 17:20:00"), "YES");

        Question q4 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion2a", q4.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 17:250:00"), "5 times a day");

        Question q5 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion2b", q5.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 18:00:00"), "N95 masks");

        Question q6 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion2c", q6.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 19:00:00"), "3 times a day");

        Question q7 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion3a", q7.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 20:00:00"), "Yes");

        Question q8 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion3b", q8.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 21:00:00"), "Yes");

        Question q9 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion3c", q9.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 22:00:00"), "Yes");

        Iterador<Answer> it = trial4C19.getAnswers("idUser1");

        Answer a1 = it.seguent();
        Assert.assertEquals("NO",a1.getAnswer() );

        Answer a2 = it.seguent();
        Assert.assertEquals("YES",a2.getAnswer() );

        Answer a3 = it.seguent();
        Assert.assertEquals("YES",a3.getAnswer() );
    }
    
    /**********************************************************************
     * 
     *                     TESTS PROPORCIONATS A LA PRAC
     *                      
     **********************************************************************/

    /**
     * *feature*: (sobre la que fem @test): addClinician del TAD TrialC19
     * *given*: Hi ha 10 especialistes en el sistema
     * *scenario*:
     * - S'afegeix un nou especialista en el sistema
     * - S'afegeix un segon especialista en el sistema
     * - Es modifiquen les dades del segon especialista inserit
     */
    @Test
    public void testAddClinician() {

        // GIVEN:
        Assert.assertEquals(10, this.trial4C19.numClinician());
        //

        this.trial4C19.addClinician("idClinician100", "Maria", "Garcia", "biology, cells");
        Assert.assertEquals("Maria", this.trial4C19.getClinician("idClinician100").getName());
        Assert.assertEquals(11, this.trial4C19.numClinician());

        this.trial4C19.addClinician("idClinician9999", "XXXXX", "YYYYY", "KK,KK");
        Assert.assertEquals("XXXXX", this.trial4C19.getClinician("idClinician9999").getName());
        Assert.assertEquals(12, this.trial4C19.numClinician());

        this.trial4C19.addClinician("idClinician9999", "David", "Roncon", "K1,K2");
        Assert.assertEquals("David", this.trial4C19.getClinician("idClinician9999").getName());
        Assert.assertEquals("Roncon", this.trial4C19.getClinician("idClinician9999").getSurname());
        Assert.assertEquals("K1,K2", this.trial4C19.getClinician("idClinician9999").getKnowledgeArea());
        Assert.assertEquals(12, this.trial4C19.numClinician());
    }


    /**
     * *feature*: (sobre la que fem @test): addLaboratory del TAD TrialC19
     * *given*: Hi ha 5 Laboratoris en el sistema
     * *scenario*:
     * - S'afegeix un nou laboratori en el sistema
     * - S'afegeix un segon laboratori en el sistema
     * - Es modifiquen les dades del segon laboratori inserit
     */
    @Test
    public void testAddLaboratory() {

        // GIVEN:
        Assert.assertEquals(5, this.trial4C19.numLaboratories());
        //

        this.trial4C19.addLaboratory("LAB6", "LAB CLINIC 6");
        Assert.assertEquals("LAB CLINIC 6", this.trial4C19.getLaboratory("LAB6").getName());
        Assert.assertEquals(6, this.trial4C19.numLaboratories());

        this.trial4C19.addLaboratory("LAB7", "LAB XXXXXX");
        Assert.assertEquals("LAB XXXXXX", this.trial4C19.getLaboratory("LAB7").getName());
        Assert.assertEquals(7, this.trial4C19.numLaboratories());

        this.trial4C19.addLaboratory("LAB7", "LAB CLINIC 7");
        Assert.assertEquals("LAB CLINIC 7", this.trial4C19.getLaboratory("LAB7").getName());
        Assert.assertEquals(7, this.trial4C19.numLaboratories());
    }


    /**
     *

     */
    @Test
    public void testAddSamples() throws DEDException {
        Assert.assertEquals(10, this.trial4C19.numUsers());
        Assert.assertEquals(5, this.trial4C19.numLaboratories());

        this.trial4C19.newSample("sample1", "idUser7", "idClinician1", createDate("15-12-2020 15:00:00"));
        this.trial4C19.newSample("sample2", "idUser8", "idClinician1", createDate("15-12-2020 15:10:00"));
        this.trial4C19.newSample("sample3", "idUser9", "idClinician2", createDate("15-12-2020 15:20:00"));
        this.trial4C19.newSample("sample4", "idUser1", "idClinician3", createDate("15-12-2020 15:50:00"));
        this.trial4C19.newSample("sample5", "idUser2", "idClinician3", createDate("15-12-2020 16:00:00"));
        this.trial4C19.newSample("sample6", "idUser3", "idClinician3", createDate("15-12-2020 16:10:00"));
        this.trial4C19.newSample("sample7", "idUser4", "idClinician3", createDate("15-12-2020 16:20:00"));
        this.trial4C19.newSample("sample8", "idUser1", "idClinician1", createDate("16-12-2020 10:00:00"));

        Clinician clinician = this.trial4C19.mostActiveClinician();
        Assert.assertEquals("idClinician3", clinician.getIdClinician());

        Assert.assertEquals(8, this.trial4C19.numSamples());
        Assert.assertEquals(3, this.trial4C19.numSamplesByClinician("idClinician1"));
        Assert.assertEquals(1, this.trial4C19.numSamplesByClinician("idClinician2"));
        Assert.assertEquals(4, this.trial4C19.numSamplesByClinician("idClinician3"));
        Assert.assertEquals(2, this.trial4C19.numSamplesByUser("idUser1"));

        Assert.assertEquals(5, this.trial4C19.numSamplesByTrial(1));
        Assert.assertEquals(3, this.trial4C19.numSamplesByTrial(2));


        Sample sample1 = this.trial4C19.getSample("sample1");
        Assert.assertEquals(Trial4C19.Status.PENDING, sample1.getStatus());
        Sample sample2 = this.trial4C19.getSample("sample2");
        Assert.assertEquals(Trial4C19.Status.PENDING, sample2.getStatus());

        // sample 7
        Sample s1st = this.trial4C19.sendSample(createDate("15-12-2020 20:00:00"));

        // sample 6
        Sample s2nd = this.trial4C19.sendSample(createDate("15-12-2020 20:01:00"));

        // sample 5
        Sample s3rd = this.trial4C19.sendSample(createDate("15-12-2020 20:02:00"));

        // sample 4
        Sample s4th = this.trial4C19.sendSample(createDate("15-12-2020 20:03:00"));

        // sample 8
        Sample s5th = this.trial4C19.sendSample(createDate("15-12-2020 20:04:00"));

        // sample 2
        Sample s6th = this.trial4C19.sendSample(createDate("15-12-2020 20:05:00"));

        // sample 3
        Sample s7th = this.trial4C19.sendSample(createDate("15-12-2020 20:06:00"));

        // sample 1
        Sample s8th = this.trial4C19.sendSample(createDate("15-12-2020 20:07:00"));

        Date now = createDate("16-12-2020 11:30:00");

        Assert.assertEquals("sample7", s1st.getIdSample());
        Assert.assertEquals(Trial4C19.Level.D,  s1st.getUser().getLevel());
        Assert.assertEquals(96 , s1st.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s1st.getStatus());

        Assert.assertEquals("sample6", s2nd.getIdSample());
        Assert.assertEquals(Trial4C19.Level.D,  s2nd.getUser().getLevel());
        Assert.assertEquals(89 , s2nd.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s2nd.getStatus());

        Assert.assertEquals("sample5", s3rd.getIdSample());
        Assert.assertEquals(Trial4C19.Level.D,  s3rd.getUser().getLevel());
        Assert.assertEquals(35 , s3rd.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s3rd.getStatus());

        Assert.assertEquals("sample4", s4th.getIdSample());
        Assert.assertEquals(Trial4C19.Level.C,  s4th.getUser().getLevel());
        Assert.assertEquals(86 , s4th.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s4th.getStatus());

        Assert.assertEquals("sample8", s5th.getIdSample());
        Assert.assertEquals(Trial4C19.Level.C,  s5th.getUser().getLevel());
        Assert.assertEquals(86 , s5th.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s5th.getStatus());

        Assert.assertEquals("sample2", s6th.getIdSample());
        Assert.assertEquals(Trial4C19.Level.B,  s6th.getUser().getLevel());
        Assert.assertEquals(28 , s6th.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s6th.getStatus());

        Assert.assertEquals("sample3", s7th.getIdSample());
        Assert.assertEquals(Trial4C19.Level.B,  s7th.getUser().getLevel());
        Assert.assertEquals(15 , s7th.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s7th.getStatus());

        Assert.assertEquals("sample1", s8th.getIdSample());
        Assert.assertEquals(Trial4C19.Level.A,  s8th.getUser().getLevel());
        Assert.assertEquals(32 , s8th.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s8th.getStatus());

        Assert.assertEquals(2, this.trial4C19.numPendingSamplesByLaboratory("LAB1"));
        Assert.assertEquals(2, this.trial4C19.numPendingSamplesByLaboratory("LAB2"));
        Assert.assertEquals(2, this.trial4C19.numPendingSamplesByLaboratory("LAB3"));
        Assert.assertEquals(1, this.trial4C19.numPendingSamplesByLaboratory("LAB4"));
        Assert.assertEquals(1, this.trial4C19.numPendingSamplesByLaboratory("LAB5"));

        Sample sample7Lab1 = this.trial4C19.processSample("LAB1", now, "Report: Sample7 (LAB1)");
        Sample sample2Lab1 = this.trial4C19.processSample("LAB1", now, "Report: Sample2 (LAB1)");
        Assert.assertEquals("sample7", sample7Lab1.getIdSample());
        Assert.assertEquals("sample2", sample2Lab1.getIdSample());


        Sample sample6Lab2 = this.trial4C19.processSample("LAB2", now, "Report: Sample6 (LAB2)");
        Sample sample3Lab2 = this.trial4C19.processSample("LAB2", now, "Report: Sample3 (LAB2)");
        Assert.assertEquals("sample6", sample6Lab2.getIdSample());
        Assert.assertEquals("sample3", sample3Lab2.getIdSample());

        Sample sample5Lab3 = this.trial4C19.processSample("LAB3", now, "Report: Sample5 (LAB3)");
        Sample sample1Lab3 = this.trial4C19.processSample("LAB3", now, "Report: Sample1 (LAB3)");
        Assert.assertEquals("sample5", sample5Lab3.getIdSample());
        Assert.assertEquals("sample1", sample1Lab3.getIdSample());

        Sample sample4Lab4 = this.trial4C19.processSample("LAB4", now, "Report: Sample4 (LAB4)");
        Assert.assertEquals("sample4", sample4Lab4.getIdSample());

        Sample sample7Lab5 = this.trial4C19.processSample("LAB5", now, "Report: Sample7 (LAB5)");
        Assert.assertEquals("sample8", sample7Lab5.getIdSample());


        Assert.assertEquals(Trial4C19.Status.COMPLETED, s1st.getStatus());
        Assert.assertEquals("Report: Sample7 (LAB1)", s1st.getReport());

        Assert.assertEquals(Trial4C19.Status.COMPLETED, s2nd.getStatus());
        Assert.assertEquals("Report: Sample6 (LAB2)", s2nd.getReport());

        Assert.assertEquals(Trial4C19.Status.COMPLETED, s3rd.getStatus());
        Assert.assertEquals("Report: Sample5 (LAB3)", s3rd.getReport());

        Assert.assertEquals(Trial4C19.Status.COMPLETED, s4th.getStatus());
        Assert.assertEquals("Report: Sample4 (LAB4)", s4th.getReport());

        Assert.assertEquals(Trial4C19.Status.COMPLETED, s5th.getStatus());
        Assert.assertEquals("Report: Sample7 (LAB5)", s5th.getReport());

        Assert.assertEquals(Trial4C19.Status.COMPLETED, s6th.getStatus());
        Assert.assertEquals("Report: Sample2 (LAB1)", s6th.getReport());

        Assert.assertEquals(Trial4C19.Status.COMPLETED, s7th.getStatus());
        Assert.assertEquals("Report: Sample3 (LAB2)", s7th.getReport());

        Assert.assertEquals(Trial4C19.Status.COMPLETED, s8th.getStatus());
        Assert.assertEquals("Report: Sample1 (LAB3)", s8th.getReport());

        Assert.assertEquals(createDate("15-12-2020 15:00:00"), sample1.getDateCreation() );
        Assert.assertEquals(createDate("15-12-2020 20:07:00"), sample1.getDateSended() );
        Assert.assertEquals(createDate("16-12-2020 11:30:00"), sample1.getDateCompleted() );


    }

    @Test(expected = UserNotFoundException.class)
    public void testAddSamplesAndUserNotFound() throws DEDException {
        this.trial4C19.newSample("sample7", "idUserXXX", "idClinician3", createDate("15-12-2020 16:20:00"));
    }

    @Test(expected = TrialNotFoundException.class)
    public void testAddSamplesAndTrialNotFound() throws DEDException {
        this.trial4C19.newSample("sample7", "idUser10", "idClinician3", createDate("15-12-2020 16:20:00"));
    }


    @Test(expected = ClinicianNotFoundException.class)
    public void testAddSamplesAndClinicianNotFound() throws DEDException {
        this.trial4C19.newSample("sample1", "idUser1", "idClinicianXXX", createDate("15-12-2020 16:20:00"));
    }
    
    /**********************************************************************
     * 
     *                    TESTS ADDICIONALS DESENVOLUPATS
     *                      
     **********************************************************************/

    
    /**********************************************************************
     * 
     *                           MÈTODES AUXILIARS
     *                      
     **********************************************************************/
    
    private Date createDate(String date) {
        return DateUtils.createDate(date);
    }

}