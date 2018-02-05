package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;

/**
 * The persistent class for the arsenal database table.
 * 
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name = "Arsenal.findAll", query = "SELECT a FROM Arsenal a"),
	@NamedQuery(name = "Arsenal.nextId", query = "SELECT a FROM Arsenal a where a.codArsenal >:cod"),
	@NamedQuery(name = "Arsenal.previousId", query = "SELECT a FROM Arsenal a where a.codArsenal <:cod")
	
})

public class Arsenal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codArsenal;

	private String nombre;

	// bi-directional many-to-one association to Clase
	@ManyToOne
	@JoinColumn(name = "CodClase")
	private Clase clase;

	// bi-directional many-to-one association to PoliciaArsenal
	@OneToMany(mappedBy = "arsenal")
	private List<PoliciaArsenal> policiaarsenals;

	public Arsenal() {
	}

	public int getCodArsenal() {
		return this.codArsenal;
	}

	public void setCodArsenal(int codArsenal) {
		this.codArsenal = codArsenal;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Clase getClase() {
		return this.clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
	}

	@XmlTransient
	public List<PoliciaArsenal> getPoliciaarsenals() {
		return this.policiaarsenals;
	}

	public void setPoliciaarsenals(List<PoliciaArsenal> policiaarsenals) {
		this.policiaarsenals = policiaarsenals;
	}

	public PoliciaArsenal addPoliciaarsenal(PoliciaArsenal policiaarsenal) {
		getPoliciaarsenals().add(policiaarsenal);
		policiaarsenal.setArsenal(this);

		return policiaarsenal;
	}

	public PoliciaArsenal removePoliciaarsenal(PoliciaArsenal policiaarsenal) {
		getPoliciaarsenals().remove(policiaarsenal);
		policiaarsenal.setArsenal(null);

		return policiaarsenal;
	}

}