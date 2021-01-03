# DISSENY D'ESTRUCTURES DE DADES (DED - 05613)

**UOC - Universitat Oberta de Catalunya**

**ACTIVITAT: Pràctica Final** 

**ALUMNE: Bericat Ruz, Jordi** 

**SEMESTRE: Tardor-2020/21** 

**GITHUB REPO: UOC-Assignments/uoc.ded.prac** 

## 1. ASPECTES DESTACADES D'IMPLEMENTACIÓ DE LES ESTRUCTURES I LES OPERACIONS DEMANADES:

- **Actualitzar l'especialista més actiu *(Trial4C19.mostActiveClinician)*:** Per tal 
  d'actualitzar l'especialista (clinician) més actiu, s'ha creat el mètode privat auxiliar 
  *"Trial4C19Impl.updateMostActiveClinician()"* el qual invoquem cada cop que s'extrau una mostra 
  amb *"Trial4C19Impl.NewSample()"*, ja que és el moment en el que s'afegeixen elements a la 
  llista encadenada de mostres de l'especialista que supervisa la mostra.

- **Com s'indexen i afegeixen els laboratoris al vector de labs:** Afegirem el laboratoris a la 
  darrera posició lliure del vector de laboratoris, de manera que aquests quedaran "compactats" 
  (no hi hauarà posicions lliures intermedies en el vector). L'ordre d'inserció estableix l'ordre 
  en el que s'anirà rotant l'assignació del laboratori al que s'envien les mostres. Com es comenta 
  al codi, les operacions de cerca sobre el vector (implementada a la operació 
  Trial4C19.getLaboratory()), tot i que "sobre el paper" tindran una eficiència temporal linial 
  O(n), a la pràctica la poca quantitat de laboratoris "n" tal que "n<100" farà que com a màxim
  la eficiència temporal de les cerques passi a ser O(100), equivalent a un temps constant.

- **Implementació del comparador de mostres *(Samples.CMP)*:**

- **Com es reparteixen les mostres entre els laboratoris *(Trial4C19.nextLaboratory i 
  Trial4C19.numLaboratories)*:**

## 2. JOCS DE PROVES:

Per a comprovar que les implementacions realitzades al projecte són fidels als requisits 
establerts a l'enunciat, s'ha ampliat el joc de proves JUNIT4 proporcionat al fitxer 
*"/src/test/java/Trial4C19PRACTest.java"*
 
Concretament, s'han afegit els tests proporcionats a la solució de la EP2 (tot modificant el test 
*"testAddUser()"* per tal de testejar la implementació dels nous requeriments), així com els 
següents tests que proven les implementacions realitzades per la PRAC i que no estaven comtemplats 
en els 6 tests proporcionats per la PRAC:   

- **Extended test #1:** Nous requeriments AddUser() --> *Trial4C19PRATest.testAddUser()*
 
- **Extended test #2:** Tests sobre el AVL de mostres general després de realitzar canvis d'estat 
  d'algunes mostres --> *Trial4C19PRATest.testAddSamples()*
 
- **Extended test #3:** Comprovem que la implementació de la operació "Trial4C19.samplesByUser()" 
  demanada a l'enunciat és correcta --> *Trial4C19PRATest.testAddSamples()*
 
- **Extended test #4:** Comprovem que la implementació de la operació 
  "Trial4C19.samplesByClinician()" demanada a l'enunciat és correcta --> 
  *Trial4C19PRATest.testAddSamples()*
 
- **Extended test #5:** Comprovem que la implementació de la operació "Trial4C19.samplesByTrial()" 
  demanada a l'enunciat és correcta --> *Trial4C19PRATest.testAddSamples()*

Cal destacar que la implementació del TAD *"Trial4C19Impl.java"* realitzada (així com de les 
estructures que aquest inclou) **supera totes les proves**.

## 3. ALTRES CONSIDERACIONS:

Pel que fa la resta de detalls d'implementació, s'ha cregut convenient afegir els comentaris que 
expliquen cada decisió presa als fitxers font adjunts en la entrega, en comptes d'incloure-l's en 
aquest fitxer README.md. De la mateixa manera, el fitxer de test *"Trial4C19PRACTest.java"* 
esmentat al punt anterior també inclou un seguit de comentaris amb el màxim de detall possible. 
