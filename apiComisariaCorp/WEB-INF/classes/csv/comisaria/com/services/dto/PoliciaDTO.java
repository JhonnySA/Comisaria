package csv.comisaria.com.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import csv.comisaria.com.domain.Arresto;
import csv.comisaria.com.domain.Caso;
import csv.comisaria.com.domain.Categoria;
import csv.comisaria.com.domain.Funcion;
import csv.comisaria.com.domain.Policia;
import csv.comisaria.com.domain.PoliciaArsenal;
import csv.comisaria.com.util.Link;
import lombok.Data;

@XmlRootElement
@Data
public class PoliciaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int codPolicia;
	private String apellidoMat;
	private String apellidoPat;
	private String nombre;
	private List<Arresto> arrestos;
	private Caso caso;
	private Categoria categoria;
	private Funcion funcion;
	private Policia policia;
	// private List<Policia> policias;
	private List<Link> links = new ArrayList<>();
	private List<PoliciaArsenal> policiaarsenals;

	public PoliciaDTO() {

	}

	public PoliciaDTO(Policia entity) {
		if (entity != null) {
			this.codPolicia = entity.getCodPolicia();
			this.apellidoMat = entity.getApellidoMat();
			this.apellidoPat = entity.getApellidoPat();
			this.nombre = entity.getNombre();
			this.arrestos = entity.getArrestos();
			this.caso = entity.getCaso();
			this.categoria = entity.getCategoria();
			this.funcion = entity.getFuncion();
			this.policia = entity.getPolicia();
			this.policiaarsenals = entity.getPoliciaarsenals();
		}
	}

	public Policia toEntity() {
		Policia entity = new Policia();
		entity.setCodPolicia(codPolicia);
		entity.setApellidoMat(apellidoMat);
		entity.setApellidoPat(apellidoPat);
		entity.setNombre(nombre);
		entity.setArrestos(arrestos);
		entity.setCaso(caso);
		entity.setCategoria(categoria);
		entity.setFuncion(funcion);
		entity.setPolicia(entity);
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