package uoc.ded.practica.model;

import uoc.ei.tads.LlistaEncadenada;

public class Clinician {
	private String idClinician;
	private String name;
	private String surname;
	private String knowledgeArea;	
	private LlistaEncadenada<Sample> samples;

	public Clinician(String idClinician, String name, String surname, String knowledgeArea) {
		this.setIdClinician(idClinician);
		this.setName(name);
		this.setSurname(surname);
		this.setKnowledgeArea(knowledgeArea);
		this.samples = new LlistaEncadenada<Sample>();
	}

	public String getIdClinician() {
		return idClinician;
	}

	public void setIdClinician(String idClinician) {
		this.idClinician = idClinician;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getKnowledgeArea() {
		return knowledgeArea;
	}

	public void setKnowledgeArea(String knowledgeArea) {
		this.knowledgeArea = knowledgeArea;
	}
	
	public int getNumSamples() {
		return this.samples.nombreElems();
	}
	
	public void addSample(Sample s) {
		this.samples.afegirAlFinal(s);
	}
}