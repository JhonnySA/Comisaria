package csv.comisaria.com.services.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import csv.comisaria.com.domain.Arsenal;
import csv.comisaria.com.domain.Policia;
import csv.comisaria.com.domain.PoliciaArsenal;
import csv.comisaria.com.domain.PoliciaArsenalPK;
import lombok.Data;

@XmlRootElement
@Data
public class PoliciaArsenalDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private PoliciaArsenalPK id;
	private Date fechaAdquisicion;
	private Date fechaRetiro;
	private int grado;
	private Arsenal arsenal;
	private Policia policia;

	public PoliciaArsenalDTO() {

	}

	public PoliciaArsenalDTO(PoliciaArsenal entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.fechaAdquisicion = entity.getFechaAdquisicion();
			this.fechaRetiro = entity.getFechaRetiro();
			this.grado = entity.getGrado();
			this.arsenal = entity.getArsenal();
			this.policia = entity.getPolicia();
		}
	}

	public PoliciaArsenal toEntity() {
		PoliciaArsenal entity = new PoliciaArsenal();
		entity.setId(id);
		entity.setFechaAdquisicion(fechaAdquisicion);
		entity.setFechaRetiro(fechaRetiro);
		entity.setGrado(grado);
		entity.setArsenal(arsenal);
		entity.setPolicia(policia);

		return entity;
	}

}