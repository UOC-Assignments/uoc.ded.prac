package uoc.ded.practica.model;

import java.util.Comparator;
import java.util.Date;

import uoc.ded.practica.Trial4C19.Status;
import uoc.ded.practica.util.DateUtils;

public class Sample implements Comparable<Sample>{
	private static final Date now = DateUtils.createDate("01-01-2021 00:00:00"); //AIXÒ ESTA FET EN PLAN RÀPID, BUSCAR LA MANERA D'OBTENIR DATA REAL
    public static final Comparator<Sample> CMP = new Comparator<Sample>() {
		
    	public int compare(Sample s1, Sample s2) {
    		
		   /* Primer comparem per nivell de gravetat i desem el resultat a la var 
		    * "level_CMP" (utilitzem el mètode compareTo de la classe String)*/
    		
	       int level_CMP = s1.user.getLevel().compareTo(s2.user.getLevel());

	       /* Ara Comparem per edat i desem el resultat la comparació a "age_CMP" 
	        * (en aquest cas fem un cast dels int a string per a no haver 
	        * d'implementar un comparador de int amb condicionals, és a dir, podrem
	        * utilitzar el mètode "compareTo" de la classe String). Cal observar que 
	        * l'ordre ha d'estar de major edat a menor, així que intercanviem s1 i s2
	        * respecte de la comparació anterior */
    		
	       int age_CMP = String.valueOf(s2.user.years(now)).compareTo(String.valueOf(s1.user.years(now)));
	       
	       /* Finalment determinem si la mostra rebuda com a paràmetre del comparador 
	        * té major prioritat que la mostra amb la que està sent comparada, tot 
	        * tenint en compte els dos factors de prioritat (level i age): */

	       if (level_CMP > 0) { 
	    	   return 1;
	       } else if (level_CMP == 0) { 
	    	  if (age_CMP > 0) return 1;
	    	  if (age_CMP == 0) return 0;
	    	  if (age_CMP < 0) return -1;
	       } 
	       return -1;
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
        return this.user.getLevel().compareTo(s.user.getLevel());       
    }
    
    /* Retorna la data actual (implementació feta en plan ràpid, buscar manera de 
     * generar data real amb el format requerit */
    
    private Date currentDate() {
        return DateUtils.createDate("01-01-2021 00:00:00");
    }
}