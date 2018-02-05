package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;


/**
 * The persistent class for the banda database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Banda.findAll", query="SELECT b FROM Banda b"),
	@NamedQuery(name = "Banda.nextId", query = "SELECT b FROM Banda b where b.codBanda >:cod"),
	@NamedQuery(name = "Banda.previousId", query = "SELECT b FROM Banda b where b.codBanda <:cod")
})
public class Banda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codBanda;

	private String descripcion;

	private String jefe;

	private String nombre;

	//bi-directional many-to-many association to Delincuente
	@ManyToMany
	@JoinTable(
		name="delincuentebanda"
		, joinColumns={
			@JoinColumn(name="CodBanda")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Dni")
			}
		)
	private List<Delincuente> delincuentes;

	public Banda() {
	}

	public int getCodBanda() {
		return this.codBanda;
	}

	public void setCodBanda(int codBanda) {
		this.codBanda = codBanda;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getJefe() {
		return this.jefe;
	}

	public void setJefe(String jefe) {
		this.jefe = jefe;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlTransient
	public List<Delincuente> getDelincuentes() {
		return this.delincuentes;
	}

	public void setDelincuentes(List<Delincuente> delincuentes) {
		this.delincuentes = delincuentes;
	}

}