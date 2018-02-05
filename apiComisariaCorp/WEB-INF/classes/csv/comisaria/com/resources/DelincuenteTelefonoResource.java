package csv.comisaria.com.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import csv.comisaria.com.domain.Telefono;
import csv.comisaria.com.services.dto.TelefonoDTO;
import csv.comisaria.com.services.ejb.DelincuenteEJBInterface;

//@Path("telefonos")
@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class DelincuenteTelefonoResource {

	@Inject
	private DelincuenteEJBInterface delincuenteEJB;
	@Context
	private HttpServletResponse response;

	private List<TelefonoDTO> TelefonoListEntity2DTO(List<Telefono> entityList) {
		List<TelefonoDTO> list = new ArrayList<>();
		for (Telefono entity : entityList) {
			list.add(new TelefonoDTO(entity));
		}
		return list;
	}

	private List<Telefono> TelefonoListDTO2Entity(List<TelefonoDTO> dtos) {
		List<Telefono> list = new ArrayList<>();
		for (TelefonoDTO dto : dtos) {
			list.add(dto.toEntity());
		}
		return list;
	}

	@GET
	public List<TelefonoDTO> listTelefonos(@PathParam("dni") String dni) {
		return TelefonoListEntity2DTO(delincuenteEJB.getTelefonos(dni));
	}

	@GET
	@Path("{numero: \\d+}")
	public TelefonoDTO getTelefono(@PathParam("dni") String dni, @PathParam("numero") String numero) {
		return new TelefonoDTO(delincuenteEJB.getTelefono(dni, numero));
	}

	@POST
	@Path("{numero: \\d+}")
	public TelefonoDTO addTelefono(@PathParam("dni") String dni, @PathParam("numero") String numero) {
		return new TelefonoDTO(delincuenteEJB.addTelefono(dni, numero));
	}

	@PUT
	public List<TelefonoDTO> replaceTelefonos(@PathParam("dni") String dni, List<TelefonoDTO> telefonos) {
		return TelefonoListEntity2DTO(delincuenteEJB.replaceTelefono(dni, TelefonoListDTO2Entity(telefonos)));
	}

	@DELETE
	@Path("{numero: \\d+}")
	public void removeVenta(@PathParam("dni") String dni, @PathParam("numero") String numero) {
		delincuenteEJB.removeTelefono(dni, numero);
	}

}