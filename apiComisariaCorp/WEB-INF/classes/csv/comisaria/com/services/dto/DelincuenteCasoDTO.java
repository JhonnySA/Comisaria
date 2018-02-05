package csv.comisaria.com.services.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import csv.comisaria.com.domain.Caso;
import csv.comisaria.com.domain.Delincuente;
import csv.comisaria.com.domain.DelincuenteCaso;
import csv.comisaria.com.domain.DelincuenteCasoPK;
import lombok.Data;

@XmlRootElement
@Data
public class DelincuenteCasoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DelincuenteCasoPK id;
	private String cargo;
	private Caso caso;
	private Delincuente delincuente;

	public DelincuenteCasoDTO() {

	}

	public DelincuenteCasoDTO(DelincuenteCaso entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.cargo = entity.getCargo();
			this.caso = entity.getCaso();
			this.delincuente = entity.getDelincuente();
		}
	}

	public DelincuenteCaso toEntity() {
		DelincuenteCaso entity = new DelincuenteCaso();
		entity.setId(id);
		entity.setCargo(cargo);
		entity.setCaso(caso);
		entity.setDelincuente(delincuente);

		return entity;
	}

}