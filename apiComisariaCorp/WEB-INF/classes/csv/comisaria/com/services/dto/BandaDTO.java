package csv.comisaria.com.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import csv.comisaria.com.domain.Banda;
import csv.comisaria.com.domain.Delincuente;
import csv.comisaria.com.util.Link;
import lombok.Data;

@XmlRootElement
@Data
public class BandaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int codBanda;
	private String descripcion;
	private String jefe;
	private String nombre;
	private List<Delincuente> delincuentes;
	private List<Link> links=new ArrayList<>();

	public BandaDTO() {

	}

	public BandaDTO(Banda entity) {
		if (entity != null) {
			this.codBanda = entity.getCodBanda();
			this.descripcion = entity.getDescripcion();
			this.jefe = entity.getJefe();
			this.nombre = entity.getNombre();
			this.delincuentes = entity.getDelincuentes();
		}
	}

	public Banda toEntity() {
		Banda entity = new Banda();
		entity.setCodBanda(codBanda);
		entity.setDescripcion(descripcion);
		entity.setJefe(jefe);
		entity.setNombre(nombre);
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