package uoc.ded.practica.model;

import java.util.Comparator;
import java.util.Date;

import uoc.ded.practica.Trial4C19;
import uoc.ded.practica.Trial4C19.Status;

public class Sample implements Comparable<User>{
    public static final Comparator<String> CMP = new Comparator<String>() {
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    } ;
	
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

	private void setClinician(Clinician clinician) {
		this.clinician = clinician;
	}

	private void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return this.status;
	}

	private void setIdSample(String idSample) {
		this.idSample = idSample;
	}

	public String getIdSample() {
		return this.idSample;
	}
	
	private void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}
	
	private void setReport(String report) {
		this.report = report;
	}

	public String getReport() {
		return this.report;
	}
	
	private void setDateCreation(Date date) {
		this.creationDate = date;
	}

	public Object getDateCreation() {
		return this.creationDate;
	}
	
	private void setDateSended(Date date) {
		this.shippingDate = date;
	}
	
	public Date getDateSended() {
		return this.shippingDate;
	}
	
	private void setDateCompleted(Date date) {
		this.completionDate = date;
	}

	public Object getDateCompleted() {
		return this.completionDate;
	}

	@Override
    public int compareTo(User o) {
        return this.getIdSample().compareTo(o.getId());
    }
}