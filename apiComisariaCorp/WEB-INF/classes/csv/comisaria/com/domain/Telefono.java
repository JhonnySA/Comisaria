package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the telefono database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Telefono.findAll", query="SELECT t FROM Telefono t"),
	@NamedQuery(name = "Telefono.nextId", query = "SELECT t FROM Telefono t where t.numero >:num"),
	@NamedQuery(name = "Telefono.previousId", query = "SELECT t FROM Telefono t where t.numero <:num")
})
public class Telefono implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String numero;

	private String operador;

	//bi-directional many-to-one association to Delincuente
	@ManyToOne
	@JoinColumn(name="Dni")
	private Delincuente delincuente;

	public Telefono() {
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getOperador() {
		return this.operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public Delincuente getDelincuente() {
		return this.delincuente;
	}

	public void setDelincuente(Delincuente delincuente) {
		this.delincuente = delincuente;
	}

}