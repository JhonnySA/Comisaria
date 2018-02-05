package csv.comisaria.com.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;


/**
 * The persistent class for the caso database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Caso.findAll", query="SELECT c FROM Caso c"),
	@NamedQuery(name = "Caso.nextId", query = "SELECT c FROM Caso c where c.codCaso >:cod"),
	@NamedQuery(name = "Caso.previousId", query = "SELECT c FROM Caso c where c.codCaso <:cod")
})
public class Caso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codCaso;

	private String juzgado;

	//bi-directional many-to-one association to DelincuenteCaso
	@OneToMany(mappedBy="caso")
	private List<DelincuenteCaso> delincuentecasos;

	//bi-directional many-to-one association to Policia
	@OneToMany(mappedBy="caso")
	private List<Policia> policias;

	public Caso() {
	}

	public int getCodCaso() {
		return this.codCaso;
	}

	public void setCodCaso(int codCaso) {
		this.codCaso = codCaso;
	}

	public String getJuzgado() {
		return this.juzgado;
	}

	public void setJuzgado(String juzgado) {
		this.juzgado = juzgado;
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
		delincuentecaso.setCaso(this);

		return delincuentecaso;
	}

	public DelincuenteCaso removeDelincuentecaso(DelincuenteCaso delincuentecaso) {
		getDelincuentecasos().remove(delincuentecaso);
		delincuentecaso.setCaso(null);

		return delincuentecaso;
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
		policia.setCaso(this);

		return policia;
	}

	public Policia removePolicia(Policia policia) {
		getPolicias().remove(policia);
		policia.setCaso(null);

		return policia;
	}

}