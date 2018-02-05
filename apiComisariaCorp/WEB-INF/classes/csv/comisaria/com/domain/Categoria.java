package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;

/**
 * The persistent class for the categoria database table.
 * 
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c"),
	@NamedQuery(name = "Categoria.nextId", query = "SELECT c FROM Categoria c where c.codCategoria >:cod"),
	@NamedQuery(name = "Categoria.previousId", query = "SELECT c FROM Categoria c where c.codCategoria <:cod") 
})
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codCategoria;

	private String nombre;

	// bi-directional many-to-one association to Policia
	@OneToMany(mappedBy = "categoria")
	private List<Policia> policias;

	public Categoria() {
	}

	public int getCodCategoria() {
		return this.codCategoria;
	}

	public void setCodCategoria(int codCategoria) {
		this.codCategoria = codCategoria;
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
		policia.setCategoria(this);

		return policia;
	}

	public Policia removePolicia(Policia policia) {
		getPolicias().remove(policia);
		policia.setCategoria(null);

		return policia;
	}

}