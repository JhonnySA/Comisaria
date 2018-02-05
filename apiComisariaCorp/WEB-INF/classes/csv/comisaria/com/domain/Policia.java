package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;

/**
 * The persistent class for the policia database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Policia.findAll", query = "SELECT p FROM Policia p"),
	@NamedQuery(name = "Policia.nextId", query = "SELECT p FROM Policia p where p.codPolicia >:cod"),
	@NamedQuery(name = "Policia.previousId", query = "SELECT p FROM Policia p where p.codPolicia <:cod")
})
public class Policia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codPolicia;

	private String apellidoMat;

	private String apellidoPat;

	private String nombre;

	// bi-directional many-to-one association to Arresto
	@OneToMany(mappedBy = "policia")
	private List<Arresto> arrestos;

	// bi-directional many-to-one association to Caso
	@ManyToOne
	@JoinColumn(name = "CodCaso")
	private Caso caso;

	// bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name = "CodCategoria")
	private Categoria categoria;

	// bi-directional many-to-one association to Funcion
	@ManyToOne
	@JoinColumn(name = "CodFuncion")
	private Funcion funcion;

	// bi-directional many-to-one association to Policia
	@ManyToOne
	@JoinColumn(name = "CodJefe")
	private Policia policia;

	// bi-directional many-to-one association to Policia
	@OneToMany(mappedBy = "policia")
	private List<Policia> policias;

	// bi-directional many-to-one association to PoliciaArsenal
	@OneToMany(mappedBy = "policia")
	private List<PoliciaArsenal> policiaarsenals;

	public Policia() {
	}

	public int getCodPolicia() {
		return this.codPolicia;
	}

	public void setCodPolicia(int codPolicia) {
		this.codPolicia = codPolicia;
	}

	public String getApellidoMat() {
		return this.apellidoMat;
	}

	public void setApellidoMat(String apellidoMat) {
		this.apellidoMat = apellidoMat;
	}

	public String getApellidoPat() {
		return this.apellidoPat;
	}

	public void setApellidoPat(String apellidoPat) {
		this.apellidoPat = apellidoPat;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlTransient
	public List<Arresto> getArrestos() {
		return this.arrestos;
	}

	public void setArrestos(List<Arresto> arrestos) {
		this.arrestos = arrestos;
	}

	public Arresto addArresto(Arresto arresto) {
		getArrestos().add(arresto);
		arresto.setPolicia(this);

		return arresto;
	}

	public Arresto removeArresto(Arresto arresto) {
		getArrestos().remove(arresto);
		arresto.setPolicia(null);

		return arresto;
	}

	public Caso getCaso() {
		return this.caso;
	}

	public void setCaso(Caso caso) {
		this.caso = caso;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Funcion getFuncion() {
		return this.funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	public Policia getPolicia() {
		return this.policia;
	}

	public void setPolicia(Policia policia) {
		this.policia = policia;
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
		policia.setPolicia(this);

		return policia;
	}

	public Policia removePolicia(Policia policia) {
		getPolicias().remove(policia);
		policia.setPolicia(null);

		return policia;
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
		policiaarsenal.setPolicia(this);

		return policiaarsenal;
	}

	public PoliciaArsenal removePoliciaarsenal(PoliciaArsenal policiaarsenal) {
		getPoliciaarsenals().remove(policiaarsenal);
		policiaarsenal.setPolicia(null);

		return policiaarsenal;
	}

}