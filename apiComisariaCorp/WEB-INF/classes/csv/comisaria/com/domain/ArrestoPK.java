package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the arresto database table.
 * 
 */
@Embeddable
public class ArrestoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int codPolicia;

	@Column(insertable=false, updatable=false)
	private String dni;

	public ArrestoPK() {
	}
	public int getCodPolicia() {
		return this.codPolicia;
	}
	public void setCodPolicia(int codPolicia) {
		this.codPolicia = codPolicia;
	}
	public String getDni() {
		return this.dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ArrestoPK)) {
			return false;
		}
		ArrestoPK castOther = (ArrestoPK)other;
		return 
			(this.codPolicia == castOther.codPolicia)
			&& this.dni.equals(castOther.dni);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codPolicia;
		hash = hash * prime + this.dni.hashCode();
		
		return hash;
	}
}