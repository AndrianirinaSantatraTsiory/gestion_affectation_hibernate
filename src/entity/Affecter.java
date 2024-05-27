package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="affecter")
public class Affecter {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idAffect")
	private int idAffect;
	
	@Column(name="codeemp")
	private String codeemp;
	
	@Column(name="codelieu")
	private int codelieu;
	
	@Temporal(TemporalType.DATE)
    @Column(name="dateAffectation")
    private Date dateAffectation;
	
	public Affecter() {
		
	}
	public Affecter(String codeemp, int codelieu, Date dateAffectation) {
		super();
		this.codeemp = codeemp;
		this.codelieu = codelieu;
		this.dateAffectation = dateAffectation;
	}

	public int getIdAffect() {
		return idAffect;
	}

	public void setIdAffect(int idAffect) {
		this.idAffect = idAffect;
	}

	public String getCodememp() {
		return codeemp;
	}

	public void setCodememp(String codememp) {
		this.codeemp = codememp;
	}

	public int getCodelieu() {
		return codelieu;
	}

	public void setCodelieu(int codelieu) {
		this.codelieu = codelieu;
	}

	public Date getDateAffectation() {
		return dateAffectation;
	}

	public void setDateAffectation(Date dateAffectation) {
		this.dateAffectation = dateAffectation;
	}

	@Override
	public String toString() {
		return "Affecter [idAffect=" + idAffect + ", codememp=" + codeemp + ", codelieu=" + codelieu
				+ ", dateAffectation=" + dateAffectation + "]";
	}
	
	
}
