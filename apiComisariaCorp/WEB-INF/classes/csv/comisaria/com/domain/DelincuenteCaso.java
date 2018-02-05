package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the delincuentecaso database table.
 * 
 */
@Entity
@NamedQuery(name="DelincuenteCaso.findAll", query="SELECT d FROM DelincuenteCaso d")
public class DelincuenteCaso implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DelincuenteCasoPK id;

	private String cargo;

	//bi-directional many-to-one association to Caso
	@ManyToOne
	@JoinColumn(name="CodCaso")
	private Caso caso;

	//bi-directional many-to-one association to Delincuente
	@ManyToOne
	@JoinColumn(name="Dni")
	private Delincuente delincuente;

	public DelincuenteCaso() {
	}

	public DelincuenteCasoPK getId() {
		return this.id;
	}

	public void setId(DelincuenteCasoPK id) {
		this.id = id;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Caso getCaso() {
		return this.caso;
	}

	public void setCaso(Caso caso) {
		this.caso = caso;
	}

	public Delincuente getDelincuente() {
		return this.delincuente;
	}

	public void setDelincuente(Delincuente delincuente) {
		this.delincuente = delincuente;
	}

}