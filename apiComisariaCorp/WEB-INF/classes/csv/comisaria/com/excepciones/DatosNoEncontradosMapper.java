package csv.comisaria.com.excepciones;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import csv.comisaria.com.domain.MensajeError;

@Provider
public class DatosNoEncontradosMapper implements ExceptionMapper<DatosNoEncontradosExcepcion> {

	@Override
	public Response toResponse(DatosNoEncontradosExcepcion exception) {
		MensajeError error = new MensajeError();
		error.setCodigo(404);
		error.setMensaje("No se ha encontrado los datos solicitados");
		error.setDescripcion(exception.getMessage());
		return Response.status(Response.Status.NOT_FOUND).entity(error).build();
	}

}
