# DISSENY D'ESTRUCTURES DE DADES (DED - 05613)

**UOC - Universitat Oberta de Catalunya**

**ACTIVITAT: Pràctica Final** 

**ALUMNE: Bericat Ruz, Jordi** 

**SEMESTRE: Tardor-2020/21** 

**GITHUB REPO: UOC-Assignments/uoc.ded.prac** 


## DOCUMENTACIÓ DEL CODI:

Pel que fa als detalls d'implementació, s'ha cregut convenient afegir els comentaris que expliquen cada decisió d'implementació als fitxers font adjunts en la entrega, en comptes d'incloure-l's en aquest fitxer README.md. De la mateixa manera, el fitxer de test "Trial4C19PRACTest.java" esmentat seguidament també inclou un seguit de comentaris amb el màxim de detall possible. 
 
## JOCS DE PROVES:

Per a comprovar que les implementacions realitzades al projecte són fidels als requisits establerts a l'enunciat, s'ha ampliat el joc de proves JUNIT4 proporcionat:

 - Fitxer /src/test/java/FactoryTrial4C19Extended.java
 
Concretament, s'han afegit els tests proporcionats a la solució de la EP2 (tot modificant el test "addUser" per a testejar la implementació dels nous requeriments), així com els següents tests que proven les implementacions realitzades per la PRAC i que no estaven comtemplats en els 6 tests proporcionats per la PRAC:   

 - Extended test #1: Nous requeriments AddUser() --> Trial4C19PRATest.testAddUser()
 
 - Extended test #2: Tests sobre el AVL de mostres general després de realitzar canvis d'estat d'algunes mostres --> Trial4C19PRATest.testAddSamples()
 
 - Extended test #3: Comprovem que la implementació de la operació "samplesByUser()" demanada a l'enunciat és correcta --> Trial4C19PRATest.testAddSamples()
 
 - Extended test #4: Comprovem que la implementació de la operació "samplesByClinician()" demanada a l'enunciat és correcta --> Trial4C19PRATest.testAddSamples()
 
 - Extended test #5: Comprovem que la implementació de la operació "samplesByTrial()" demanada a l'enunciat és correcta --> Trial4C19PRATest.testAddSamples()

Cal destacar que la implementació del TAD "Trial4C19Impl.java" realitzada (així com de les estructures que aquest inclou) **supera totes les proves**.

## CONSIDERACIONS ADDICIONALS:

PENDENT Explicar:

- mostActiveClinician
- com s'índexen i afegeixen els laboratoris al vector de labs
- Implementació del comparador de mostres a Samples.java
- com es reparteixen les mostres entre els laboratoris (ús de Trial4C19.nextLaboratory i Trial4C19.numLaboratories)
