package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the policiaarsenal database table.
 * 
 */
@Embeddable
public class PoliciaArsenalPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int codPolicia;

	@Column(insertable=false, updatable=false)
	private int codArsenal;

	public PoliciaArsenalPK() {
	}
	public int getCodPolicia() {
		return this.codPolicia;
	}
	public void setCodPolicia(int codPolicia) {
		this.codPolicia = codPolicia;
	}
	public int getCodArsenal() {
		return this.codArsenal;
	}
	public void setCodArsenal(int codArsenal) {
		this.codArsenal = codArsenal;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PoliciaArsenalPK)) {
			return false;
		}
		PoliciaArsenalPK castOther = (PoliciaArsenalPK)other;
		return 
			(this.codPolicia == castOther.codPolicia)
			&& (this.codArsenal == castOther.codArsenal);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codPolicia;
		hash = hash * prime + this.codArsenal;
		
		return hash;
	}
}