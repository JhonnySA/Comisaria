package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the arresto database table.
 * 
 */
@Entity
@NamedQuery(name="Arresto.findAll", query="SELECT a FROM Arresto a")
public class Arresto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ArrestoPK id;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	//bi-directional many-to-one association to Delincuente
	@ManyToOne
	@JoinColumn(name="Dni")
	private Delincuente delincuente;

	//bi-directional many-to-one association to Policia
	@ManyToOne
	@JoinColumn(name="CodPolicia")
	private Policia policia;

	public Arresto() {
	}

	public ArrestoPK getId() {
		return this.id;
	}

	public void setId(ArrestoPK id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Delincuente getDelincuente() {
		return this.delincuente;
	}

	public void setDelincuente(Delincuente delincuente) {
		this.delincuente = delincuente;
	}

	public Policia getPolicia() {
		return this.policia;
	}

	public void setPolicia(Policia policia) {
		this.policia = policia;
	}

}