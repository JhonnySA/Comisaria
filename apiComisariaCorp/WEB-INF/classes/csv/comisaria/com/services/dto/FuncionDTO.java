package csv.comisaria.com.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import csv.comisaria.com.domain.Funcion;
import csv.comisaria.com.domain.Policia;
import csv.comisaria.com.util.Link;
import lombok.Data;

@XmlRootElement
@Data
public class FuncionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int codFuncion;
	private String nombre;
	private List<Policia> policias;
	private List<Link> links= new ArrayList<>();

	public FuncionDTO() {

	}

	public FuncionDTO(Funcion entity) {
		if (entity != null) {
			this.codFuncion = entity.getCodFuncion();
			this.nombre = entity.getNombre();
			this.policias = entity.getPolicias();
		}
	}

	public Funcion toEntity() {
		Funcion entity = new Funcion();
		entity.setCodFuncion(codFuncion);
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