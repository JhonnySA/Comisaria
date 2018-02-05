package csv.comisaria.com.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import csv.comisaria.com.domain.Delincuente;
import csv.comisaria.com.domain.Telefono;
import csv.comisaria.com.util.Link;
import lombok.Data;

@XmlRootElement
@Data
public class TelefonoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String numero;
	private String operador;
	private Delincuente delincuente;
	private List<Link> links = new ArrayList<>();

	public TelefonoDTO() {

	}

	public TelefonoDTO(Telefono entity) {
		if (entity != null) {
			this.numero = entity.getNumero();
			this.operador = entity.getOperador();
			this.delincuente = entity.getDelincuente();
		}
	}

	public Telefono toEntity() {
		Telefono entity = new Telefono();
		entity.setNumero(numero);
		entity.setOperador(operador);
		entity.setDelincuente(delincuente);

		return entity;
	}

	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setUrl(url);
		link.setRel(rel);
		links.add(link);
	}

}