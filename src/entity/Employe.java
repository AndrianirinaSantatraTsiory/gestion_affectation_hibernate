package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employe")
public class Employe {
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="codeemp")
	String codeemp;
	
	@Column(name="Nom")
	String nom;
	
	@Column(name="Prenom")
	String prenom;
	
	@Column(name="Poste")
	String poste;
	
	public Employe() {
		
	}
	
	public Employe(String codeemp,String nom, String prenom, String poste) {
		super();
		this.codeemp = codeemp;
		this.nom = nom;
		this.prenom = prenom;
		this.poste = poste;
	}


	public String getCodeemp() {
		return codeemp;
	}


	public void setCodeemp(String codeemp) {
		this.codeemp = codeemp;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getPoste() {
		return poste;
	}


	public void setPoste(String poste) {
		this.poste = poste;
	}


	@Override
	public String toString() {
		return "Employe [codeemp=" + codeemp + ", nom=" + nom + ", prenom=" + prenom + ", poste=" + poste + "]";
	}
	
	
}
