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

	private String getIdLaboratory() {
		return idLaboratory;
	}

	private void setIdLaboratory(String idLaboratory) {
		this.idLaboratory = idLaboratory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}