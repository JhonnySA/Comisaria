package csv.comisaria.com.excepciones;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import csv.comisaria.com.domain.MensajeError;

public class ExcepcionGeneral implements ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable throwable) {
		MensajeError error = new MensajeError();
		error.setCodigo(404);
		error.setMensaje("Error de sintaxis");
		error.setDescripcion(throwable.getCause().toString());
		return Response.status(Response.Status.NOT_FOUND).entity(error).build();
	}
}
