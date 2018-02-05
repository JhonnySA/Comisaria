package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;


/**
 * The persistent class for the calabozo database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Calabozo.findAll", query="SELECT c FROM Calabozo c"),
	@NamedQuery(name = "Calabozo.nextId", query = "SELECT c FROM Calabozo c where c.codCalabozo >:cod"),
	@NamedQuery(name = "Calabozo.previousId", query = "SELECT c FROM Calabozo c where c.codCalabozo <:cod")
})

public class Calabozo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codCalabozo;

	private String ubicacion;

	//bi-directional many-to-one association to Delincuente
	@OneToMany(mappedBy="calabozo")
	private List<Delincuente> delincuentes;

	public Calabozo() {
	}

	public int getCodCalabozo() {
		return this.codCalabozo;
	}

	public void setCodCalabozo(int codCalabozo) {
		this.codCalabozo = codCalabozo;
	}

	public String getUbicacion() {
		return this.ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	@XmlTransient
	public List<Delincuente> getDelincuentes() {
		return this.delincuentes;
	}

	public void setDelincuentes(List<Delincuente> delincuentes) {
		this.delincuentes = delincuentes;
	}

	public Delincuente addDelincuente(Delincuente delincuente) {
		getDelincuentes().add(delincuente);
		delincuente.setCalabozo(this);

		return delincuente;
	}

	public Delincuente removeDelincuente(Delincuente delincuente) {
		getDelincuentes().remove(delincuente);
		delincuente.setCalabozo(null);

		return delincuente;
	}

}