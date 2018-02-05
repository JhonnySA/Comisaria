package csv.comisaria.com.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import csv.comisaria.com.domain.Arsenal;
import csv.comisaria.com.domain.Clase;
import csv.comisaria.com.util.Link;
import lombok.Data;

@XmlRootElement
@Data
public class ClaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int codClase;
	private String descripcion;
	private String nombre;
	private List<Arsenal> arsenals;
	private List<Link> links = new ArrayList<>();

	public ClaseDTO() {

	}

	public ClaseDTO(Clase entity) {
		if (entity != null) {
			this.codClase = entity.getCodClase();
			this.descripcion = entity.getDescripcion();
			this.nombre = entity.getNombre();
			this.arsenals = entity.getArsenals();
		}
	}

	public Clase toEntity() {
		Clase entity = new Clase();
		entity.setCodClase(codClase);
		entity.setDescripcion(descripcion);
		entity.setNombre(nombre);
		entity.setArsenals(arsenals);
		return entity;
	}

	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setUrl(url);
		link.setRel(rel);
		links.add(link);
	}

}