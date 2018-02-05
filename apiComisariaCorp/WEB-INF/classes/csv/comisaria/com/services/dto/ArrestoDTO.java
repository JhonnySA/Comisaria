package csv.comisaria.com.services.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import csv.comisaria.com.domain.Arresto;
import csv.comisaria.com.domain.ArrestoPK;
import csv.comisaria.com.domain.Delincuente;
import csv.comisaria.com.domain.Policia;
import lombok.Data;

@XmlRootElement
@Data
public class ArrestoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrestoPK id;
	private Date fecha;
	private Delincuente delincuente;
	private Policia policia;

	public ArrestoDTO() {

	}

	public ArrestoDTO(Arresto entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.fecha = entity.getFecha();
			this.delincuente = getDelincuente();
			this.policia = entity.getPolicia();
		}
	}

	public Arresto toEntity() {
		Arresto entity = new Arresto();
		entity.setId(id);
		entity.setFecha(fecha);
		entity.setDelincuente(delincuente);
		entity.setPolicia(policia);

		return entity;
	}
}
