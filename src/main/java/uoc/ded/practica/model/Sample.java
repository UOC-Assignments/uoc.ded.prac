package uoc.ded.practica.model;

import java.util.Comparator;
import java.util.Date;

import uoc.ded.practica.Trial4C19;
import uoc.ded.practica.Trial4C19.Status;

public class Sample implements Comparable<Sample>{
    public static final Comparator<Sample> CMP = new Comparator<Sample>() {
		public int compare(Sample o1, Sample o2) {
	           return o1.compareTo(o2);
		}
    };
	
	private String idSample;
	private Status status;
	private User user;
	private Clinician clinician;
	private Date creationDate;
	private Date shippingDate;
	private Date completionDate;
	private String report;
	
	public Sample(String idSample, User user, Clinician clinician, Date creationDate) {
		this.setIdSample(idSample);
		this.setStatus(Status.PENDING);
		this.setUser(user);
		this.setClinician(clinician);
		this.setReport(report);
		this.setDateCreation(creationDate);
		this.setDateSended(null);
		this.setDateCompleted(null);
	}

	public void setClinician(Clinician clinician) {
		this.clinician = clinician;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setIdSample(String idSample) {
		this.idSample = idSample;
	}

	public String getIdSample() {
		return this.idSample;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}
	
	public void setReport(String report) {
		this.report = report;
	}

	public String getReport() {
		return this.report;
	}
	
	public void setDateCreation(Date date) {
		this.creationDate = date;
	}

	public Date getDateCreation() {
		return this.creationDate;
	}
	
	private void setDateSended(Date date) {
		this.shippingDate = date;
	}
	
	public Date getDateSended() {
		return this.shippingDate;
	}
	
	public void setDateCompleted(Date date) {
		this.completionDate = date;
	}

	public Date getDateCompleted() {
		return this.completionDate;
	}

    public int compareTo(Sample s) {
    	//AQUI ESTEM COMPARANT NOMÉS PER LEVEL I TAMBÉ S'HA DE TENIR EN COMPTE LA EDAT (REVISAR!!)
        return this.user.getLevel().compareTo(s.user.getLevel());         
    }
}