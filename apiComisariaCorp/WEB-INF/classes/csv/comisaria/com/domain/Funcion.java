package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;


/**
 * The persistent class for the funcion database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Funcion.findAll", query="SELECT f FROM Funcion f"),
	@NamedQuery(name = "Funcion.nextId", query = "SELECT f FROM Funcion f where f.codFuncion >:cod"),
	@NamedQuery(name = "Funcion.previousId", query = "SELECT f FROM Funcion f where f.codFuncion <:cod")
})
public class Funcion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codFuncion;

	private String nombre;

	//bi-directional many-to-one association to Policia
	@OneToMany(mappedBy="funcion")
	private List<Policia> policias;

	public Funcion() {
	}

	public int getCodFuncion() {
		return this.codFuncion;
	}

	public void setCodFuncion(int codFuncion) {
		this.codFuncion = codFuncion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlTransient
	public List<Policia> getPolicias() {
		return this.policias;
	}

	public void setPolicias(List<Policia> policias) {
		this.policias = policias;
	}

	public Policia addPolicia(Policia policia) {
		getPolicias().add(policia);
		policia.setFuncion(this);

		return policia;
	}

	public Policia removePolicia(Policia policia) {
		getPolicias().remove(policia);
		policia.setFuncion(null);

		return policia;
	}

}