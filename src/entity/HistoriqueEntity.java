package entity;

import java.util.Date;

public class HistoriqueEntity {
	private Date dateAffectation;
	private String lieuAvant;
	private String lieuApres;
	public HistoriqueEntity() {
		
	}
	public Date getDateAffectation() {
		return dateAffectation;
	}
	public void setDateAffectation(Date dateAffectation) {
		this.dateAffectation = dateAffectation;
	}
	public String getLieuAvant() {
		return lieuAvant;
	}
	public void setLieuAvant(String lieuAvant) {
		this.lieuAvant = lieuAvant;
	}
	public String getLieuApres() {
		return lieuApres;
	}
	public void setLieuApres(String lieuApres) {
		this.lieuApres = lieuApres;
	}
	
	@Override
	public String toString() {
		return "Historique [avant=" + lieuAvant + ", apres=" + lieuApres + ", Date=" + dateAffectation + "]";
	}
}
