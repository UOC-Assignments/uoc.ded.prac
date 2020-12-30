package uoc.ded.practica.model;

import java.util.Date;

import uoc.ded.practica.Trial4C19;
import uoc.ded.practica.Trial4C19.Status;

public class Sample {
	
	private String idSample;
	private Status status;
	private User user;
	private String report;
	private Date creationDate;
	private Date shippingDate;
	private Date completionDate;
	
	public Sample(String idSample, Status status, User user, String report) {
		this.setIdSample(idSample);
		this.setStatus(status);
		this.setUser(user);
		this.setReport(report);
		this.setDateCreation(now);
		this.setDateSended(null);
		this.setDateCompleted(null);
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
}