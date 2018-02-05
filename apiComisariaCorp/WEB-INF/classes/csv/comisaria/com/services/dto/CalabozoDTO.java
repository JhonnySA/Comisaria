package csv.comisaria.com.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import csv.comisaria.com.domain.Calabozo;
import csv.comisaria.com.domain.Delincuente;
import csv.comisaria.com.util.Link;
import lombok.Data;

@XmlRootElement
@Data
public class CalabozoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int codCalabozo;
	private String ubicacion;
	private List<Delincuente> delincuentes;
	private List<Link> links = new ArrayList<>();

	public CalabozoDTO() {

	}

	public CalabozoDTO(Calabozo entity) {
		if (entity != null) {
			this.codCalabozo = entity.getCodCalabozo();
			this.ubicacion = entity.getUbicacion();
			this.delincuentes = entity.getDelincuentes();
		}
	}

	public Calabozo toEntity() {
		Calabozo entity = new Calabozo();
		entity.setCodCalabozo(codCalabozo);
		entity.setUbicacion(ubicacion);
		entity.setDelincuentes(delincuentes);

		return entity;
	}

	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setUrl(url);
		link.setRel(rel);
		links.add(link);
	}

}