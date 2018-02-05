package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;


/**
 * The persistent class for the clase database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Clase.findAll", query="SELECT c FROM Clase c"),
	@NamedQuery(name = "Clase.nextId", query = "SELECT c FROM Clase c where c.codClase >:cod"),
	@NamedQuery(name = "Clase.previousId", query = "SELECT c FROM Clase c where c.codClase <:cod"),
	@NamedQuery(name = "Clase.lastId", query="SELECT c FROM Clase c order by c.codClase desc"),

})
public class Clase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codClase;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to Arsenal
	@OneToMany(mappedBy="clase")
	private List<Arsenal> arsenals;

	public Clase() {
	}

	public int getCodClase() {
		return this.codClase;
	}

	public void setCodClase(int codClase) {
		this.codClase = codClase;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlTransient
	public List<Arsenal> getArsenals() {
		return this.arsenals;
	}

	public void setArsenals(List<Arsenal> arsenals) {
		this.arsenals = arsenals;
	}

	public Arsenal addArsenal(Arsenal arsenal) {
		getArsenals().add(arsenal);
		arsenal.setClase(this);

		return arsenal;
	}

	public Arsenal removeArsenal(Arsenal arsenal) {
		getArsenals().remove(arsenal);
		arsenal.setClase(null);

		return arsenal;
	}

}