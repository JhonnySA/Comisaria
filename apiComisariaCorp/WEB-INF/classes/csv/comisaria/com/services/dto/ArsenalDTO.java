package csv.comisaria.com.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import csv.comisaria.com.domain.Arsenal;
import csv.comisaria.com.domain.Clase;
import csv.comisaria.com.domain.PoliciaArsenal;
import csv.comisaria.com.util.Link;
import lombok.Data;

@XmlRootElement
@Data
public class ArsenalDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int codArsenal;
	private String nombre;
	private Clase clase;
	private List<PoliciaArsenal> policiaarsenals;
	private List<Link> links = new ArrayList<>();

	public ArsenalDTO() {

	}

	public ArsenalDTO(Arsenal entity) {
		if (entity != null) {
			this.codArsenal = entity.getCodArsenal();
			this.nombre = entity.getNombre();
			this.clase = entity.getClase();
			this.policiaarsenals = entity.getPoliciaarsenals();
		}
	}

	public Arsenal toEntity() {
		Arsenal entity = new Arsenal();
		entity.setCodArsenal(codArsenal);
		entity.setNombre(nombre);
		entity.setClase(clase);
		entity.setPoliciaarsenals(policiaarsenals);

		return entity;
	}

	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setUrl(url);
		link.setRel(rel);
		links.add(link);
	}
	
}