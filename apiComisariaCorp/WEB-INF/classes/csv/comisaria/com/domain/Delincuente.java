package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;


/**
 * The persistent class for the delincuente database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Delincuente.findAll", query="SELECT d FROM Delincuente d"),
	@NamedQuery(name = "Delincuente.nextId", query = "SELECT d FROM Delincuente d where d.dni >:dni"),
	@NamedQuery(name = "Delincuente.previousId", query = "SELECT d FROM Delincuente d where d.dni <:dni")
})
public class Delincuente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String dni;

	private String apellidoMat;

	private String apellidoPat;

	private String nombre;

	//bi-directional many-to-one association to Arresto
	@OneToMany(mappedBy="delincuente")
	private List<Arresto> arrestos;

	//bi-directional many-to-many association to Banda
	@ManyToMany(mappedBy="delincuentes")
	private List<Banda> bandas;

	//bi-directional many-to-one association to Calabozo
	@ManyToOne
	@JoinColumn(name="CodCalabozo")
	private Calabozo calabozo;

	//bi-directional many-to-one association to DelincuenteCaso
	@OneToMany(mappedBy="delincuente")
	private List<DelincuenteCaso> delincuentecasos;

	//bi-directional many-to-one association to Telefono
	@OneToMany(mappedBy="delincuente")
	private List<Telefono> telefonos;

	public Delincuente() {
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
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
		arresto.setDelincuente(this);

		return arresto;
	}

	public Arresto removeArresto(Arresto arresto) {
		getArrestos().remove(arresto);
		arresto.setDelincuente(null);

		return arresto;
	}

	@XmlTransient
	public List<Banda> getBandas() {
		return this.bandas;
	}

	public void setBandas(List<Banda> bandas) {
		this.bandas = bandas;
	}

	public Calabozo getCalabozo() {
		return this.calabozo;
	}

	public void setCalabozo(Calabozo calabozo) {
		this.calabozo = calabozo;
	}

	@XmlTransient
	public List<DelincuenteCaso> getDelincuentecasos() {
		return this.delincuentecasos;
	}

	public void setDelincuentecasos(List<DelincuenteCaso> delincuentecasos) {
		this.delincuentecasos = delincuentecasos;
	}

	public DelincuenteCaso addDelincuentecaso(DelincuenteCaso delincuentecaso) {
		getDelincuentecasos().add(delincuentecaso);
		delincuentecaso.setDelincuente(this);

		return delincuentecaso;
	}

	public DelincuenteCaso removeDelincuentecaso(DelincuenteCaso delincuentecaso) {
		getDelincuentecasos().remove(delincuentecaso);
		delincuentecaso.setDelincuente(null);

		return delincuentecaso;
	}

	@XmlTransient
	public List<Telefono> getTelefonos() {
		return this.telefonos;
	}

	public void setTelefonos(List<Telefono> telefonos) {
		this.telefonos = telefonos;
	}

	public Telefono addTelefono(Telefono telefono) {
		getTelefonos().add(telefono);
		telefono.setDelincuente(this);

		return telefono;
	}

	public Telefono removeTelefono(Telefono telefono) {
		getTelefonos().remove(telefono);
		telefono.setDelincuente(null);

		return telefono;
	}

}