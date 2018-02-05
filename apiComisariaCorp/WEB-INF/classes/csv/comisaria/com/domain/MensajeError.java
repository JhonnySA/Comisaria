package csv.comisaria.com.domain;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement
@Data
public class MensajeError {

	String mensaje;
	int codigo;
	String descripcion;

}
