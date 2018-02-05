package csv.comisaria.com.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import csv.comisaria.com.domain.Caso;
import csv.comisaria.com.domain.DelincuenteCaso;
import csv.comisaria.com.domain.Policia;
import csv.comisaria.com.util.Link;
import lombok.Data;

@XmlRootElement
@Data
public class CasoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int codCaso;
	private String juzgado;
	private List<DelincuenteCaso> delincuentecasos;
	private List<Policia> policias;
	private List<Link> links = new ArrayList<>();

	public CasoDTO() {

	}

	public CasoDTO(Caso entity) {
		if (entity != null) {
			this.codCaso = entity.getCodCaso();
			this.juzgado = entity.getJuzgado();
			this.delincuentecasos = entity.getDelincuentecasos();
			this.policias = entity.getPolicias();
		}
	}

	public Caso toEntity() {
		Caso entity = new Caso();
		entity.setCodCaso(codCaso);
		entity.setJuzgado(juzgado);
		entity.setDelincuentecasos(delincuentecasos);
		entity.setPolicias(policias);

		return entity;
	}

	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setUrl(url);
		link.setRel(rel);
		links.add(link);
	}

}