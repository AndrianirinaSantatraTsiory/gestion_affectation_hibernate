package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import com.sun.istack.Pool;

@Entity
@Table(name="lieu")
public class Lieu {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="codelieu")
	private int codelieu;
	
	@Column(name="Designation")
	private String designation;
	
	@Column(name="Province")
	private String province;
	
	public Lieu() {
		
	}
	public Lieu(String designation, String province) {
		super();
		this.designation = designation;
		this.province = province;
	}

	public int getCodelieu() {
		return codelieu;
	}

	public void setCodelieu(int codelieu) {
		this.codelieu = codelieu;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Override
	public String toString() {
		return this.designation;
	}
}
