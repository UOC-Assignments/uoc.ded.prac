package uoc.ded.practica.model;

import uoc.ei.tads.CuaVectorImpl;
import uoc.ded.practica.Trial4C19;

public class Laboratory {
	private String idLaboratory;
	private String name;
	private CuaVectorImpl<Sample> samples;
	
	public Laboratory(String idLaboratory, String name) {
		this.setIdLaboratory(idLaboratory);
		this.setName(name);
		this.samples = new CuaVectorImpl<Sample>(Trial4C19.MAX_SAMPLES_LAB);
	}

	public String getIdLaboratory() {
		return idLaboratory;
	}

	public void setIdLaboratory(String idLaboratory) {
		this.idLaboratory = idLaboratory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addSample(Sample s) {
		this.samples.encuar(s);
	}
	
	public Sample getNextSample() {
		return this.samples.desencuar();
	}

	public int getNumSamples() {
		return this.samples.nombreElems();
	}
}