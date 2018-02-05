package csv.comisaria.com.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import csv.comisaria.com.domain.Arresto;
import csv.comisaria.com.domain.Banda;
import csv.comisaria.com.domain.Calabozo;
import csv.comisaria.com.domain.Delincuente;
import csv.comisaria.com.domain.DelincuenteCaso;
import csv.comisaria.com.domain.Telefono;
import csv.comisaria.com.util.Link;
import lombok.Data;

@XmlRootElement
@Data
public class DelincuenteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dni;
	private String apellidoMat;
	private String apellidoPat;
	private String nombre;
	private List<Arresto> arrestos;
	private List<Banda> bandas;
	private Calabozo calabozo;
	private List<DelincuenteCaso> delincuentecasos;
	private List<Telefono> telefonos;
	private List<Link> links = new ArrayList<>();

	public DelincuenteDTO() {

	}

	public DelincuenteDTO(Delincuente entity) {
		if (entity != null) {
			this.dni = entity.getDni();
			this.apellidoMat = entity.getApellidoMat();
			this.apellidoPat = entity.getApellidoPat();
			this.nombre = entity.getNombre();
			this.arrestos = entity.getArrestos();
			this.bandas = entity.getBandas();
			this.calabozo = entity.getCalabozo();
			this.delincuentecasos = entity.getDelincuentecasos();
			this.telefonos = entity.getTelefonos();
		}
	}

	public Delincuente toEntity() {
		Delincuente entity = new Delincuente();
		entity.setDni(dni);
		entity.setApellidoMat(apellidoMat);
		entity.setApellidoPat(apellidoPat);
		entity.setNombre(nombre);
		entity.setArrestos(arrestos);
		entity.setBandas(bandas);
		entity.setCalabozo(calabozo);
		entity.setDelincuentecasos(delincuentecasos);
		entity.setTelefonos(telefonos);

		return entity;
	}

	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setUrl(url);
		link.setRel(rel);
		links.add(link);
	}

}