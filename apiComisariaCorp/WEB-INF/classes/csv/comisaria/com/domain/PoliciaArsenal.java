package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the policiaarsenal database table.
 * 
 */
@Entity
@NamedQuery(name="PoliciaArsenal.findAll", query="SELECT p FROM PoliciaArsenal p")
public class PoliciaArsenal implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PoliciaArsenalPK id;

	@Temporal(TemporalType.DATE)
	private Date fechaAdquisicion;

	@Temporal(TemporalType.DATE)
	private Date fechaRetiro;

	private int grado;

	//bi-directional many-to-one association to Arsenal
	@ManyToOne
	@JoinColumn(name="CodArsenal")
	private Arsenal arsenal;

	//bi-directional many-to-one association to Policia
	@ManyToOne
	@JoinColumn(name="CodPolicia")
	private Policia policia;

	public PoliciaArsenal() {
	}

	public PoliciaArsenalPK getId() {
		return this.id;
	}

	public void setId(PoliciaArsenalPK id) {
		this.id = id;
	}

	public Date getFechaAdquisicion() {
		return this.fechaAdquisicion;
	}

	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public Date getFechaRetiro() {
		return this.fechaRetiro;
	}

	public void setFechaRetiro(Date fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}

	public int getGrado() {
		return this.grado;
	}

	public void setGrado(int grado) {
		this.grado = grado;
	}

	public Arsenal getArsenal() {
		return this.arsenal;
	}

	public void setArsenal(Arsenal arsenal) {
		this.arsenal = arsenal;
	}

	public Policia getPolicia() {
		return this.policia;
	}

	public void setPolicia(Policia policia) {
		this.policia = policia;
	}

}