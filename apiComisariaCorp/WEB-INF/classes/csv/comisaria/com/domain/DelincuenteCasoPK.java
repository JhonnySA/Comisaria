package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the delincuentecaso database table.
 * 
 */
@Embeddable
public class DelincuenteCasoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String dni;

	@Column(insertable=false, updatable=false)
	private int codCaso;

	public DelincuenteCasoPK() {
	}
	public String getDni() {
		return this.dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public int getCodCaso() {
		return this.codCaso;
	}
	public void setCodCaso(int codCaso) {
		this.codCaso = codCaso;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DelincuenteCasoPK)) {
			return false;
		}
		DelincuenteCasoPK castOther = (DelincuenteCasoPK)other;
		return 
			this.dni.equals(castOther.dni)
			&& (this.codCaso == castOther.codCaso);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.dni.hashCode();
		hash = hash * prime + this.codCaso;
		
		return hash;
	}
}