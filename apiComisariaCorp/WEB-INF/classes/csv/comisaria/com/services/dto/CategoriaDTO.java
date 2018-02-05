package csv.comisaria.com.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import csv.comisaria.com.domain.Categoria;
import csv.comisaria.com.domain.Policia;
import csv.comisaria.com.util.Link;
import lombok.Data;

@XmlRootElement
@Data
public class CategoriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int codCategoria;
	private String nombre;
	private List<Policia> policias;
	private List<Link> links= new ArrayList<>();
	
	public CategoriaDTO() {

	}

	public CategoriaDTO(Categoria entity) {
		if (entity != null) {
			this.codCategoria = entity.getCodCategoria();
			this.nombre = entity.getNombre();
			this.policias = entity.getPolicias();
		}
	}

	public Categoria toEntity() {
		Categoria entity = new Categoria();
		entity.setCodCategoria(codCategoria);
		entity.setNombre(nombre);
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