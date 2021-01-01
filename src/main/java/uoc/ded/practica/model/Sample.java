package uoc.ded.practica.model;

import java.util.Comparator;
import java.util.Date;

import uoc.ded.practica.Trial4C19;
import uoc.ded.practica.Trial4C19.Status;
import uoc.ded.practica.util.DateUtils;

public class Sample implements Comparable<Sample>{
    public static final Comparator<Sample> CMP = new Comparator<Sample>() {
		
    	public int compare(Sample o1, Sample o2) {
	           int res = o1.user.getLevel().compareTo(o2.user.getLevel());
	           return res;
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

	public void setClinician(Clinician c) {
		this.clinician = c;
	}

	public void setStatus(Status s) {
		this.status = s;
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
	
	public void setReport(String r) {
		this.report = r;
	}

	public String getReport() {
		return this.report;
	}
	
	public void setDateCreation(Date d) {
		this.creationDate = d;
	}

	public Date getDateCreation() {
		return this.creationDate;
	}
	
	public void setDateSended(Date d) {
		this.shippingDate = d;
	}
	
	public Date getDateSended() {
		return this.shippingDate;
	}
	
	public void setDateCompleted(Date d) {
		this.completionDate = d;
	}

	public Date getDateCompleted() {
		return this.completionDate;
	}

    public int compareTo(Sample s) {
    	Date now = createDate("16-12-2020 11:30:00");
    	int age_CMP = 0;
    	
    	/* Primer comparem per nivell de gravetat i desem el resultat a la var 
    	 * "level_CMP" (utilitzem el mètode compareTo de la classe String)*/
    	
        return this.user.getLevel().compareTo(s.user.getLevel()); 
        
    }
    
    private Date createDate(String date) {
        return DateUtils.createDate(date);
    }
}