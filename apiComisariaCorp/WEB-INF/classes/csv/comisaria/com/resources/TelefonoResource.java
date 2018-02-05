package csv.comisaria.com.resources;

import java.net.URI;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import csv.comisaria.com.domain.Telefono;
import csv.comisaria.com.excepciones.DatosNoEncontradosExcepcion;
import csv.comisaria.com.services.dto.TelefonoDTO;
import csv.comisaria.com.services.ejb.TelefonoEJBInteface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Path("telefonos")
@io.swagger.annotations.Api(value = "/telefonos")
public class TelefonoResource {

	@Inject
	TelefonoEJBInteface telefonoEJB;

	@Context
	private HttpServletResponse response;
	@QueryParam("page")
	private Integer page;
	@QueryParam("limit")
	private Integer maxRecords;

	private List<TelefonoDTO> listEntity2DTO(List<Telefono> entityList) {
		List<TelefonoDTO> list = new ArrayList<>();
		for (Telefono entity : entityList) {
			list.add(new TelefonoDTO(entity));
		}
		return list;
	}

	@GET
	@ApiOperation(value = " Devuelve la lista de Telefonos ", notes = " Lista completa ", response = Telefono.class, responseContainer = " List ")
	public List<TelefonoDTO> getTelefonos() {

		if (page != null && maxRecords != null) {
			this.response.setIntHeader("X-Total-Count", telefonoEJB.countsTelefonos());
			return listEntity2DTO(telefonoEJB.getTelefonos(page, maxRecords));
		}
		return listEntity2DTO(telefonoEJB.getTelefonos());
	}

	@GET
	@Path("/{num: \\d+}")
	@ApiOperation(value = " Devuelve un registro de la lista de Telefonos ", notes = " Un Telefono especifico ", response = Telefono.class, responseContainer = "Telefono.Class")
	public TelefonoDTO getTelefono(
			@ApiParam(value = " Num del Telefono ", required = true) @PathParam("num") String num, @Context UriInfo uriInfo) {
		TelefonoDTO dto = new TelefonoDTO(telefonoEJB.getTelefonoById(num));
		ArrayList<TelefonoDTO> list = (ArrayList<TelefonoDTO>) listEntity2DTO(telefonoEJB.getTelefonos());
		
		String linkNext = null;
		String linkFirt;
		String linkPrevious;
		String linkSelf;
		String linkLast;

		if (dto.getNumero()== null) {
			throw new DatosNoEncontradosExcepcion(
					"No se ha encontrado el telefono con el Numero: " + num + " , probablemente fue removido del servidor");
		} else {

			linkFirt = getLinkFirt(uriInfo, list);
			linkPrevious = getLinkPrevious(uriInfo, num);
			linkSelf = getlinkSelf(uriInfo);
			linkNext = getLinkNext(uriInfo, num);
			linkLast = getLinkLast(uriInfo, list);

			if (linkSelf.equals(linkLast)) {
				linkNext = linkFirt;
			}
			if (linkSelf.equals(linkFirt)) {
				linkPrevious = linkLast;
			}

		}

		dto.addLink(linkFirt, "Firt");
		dto.addLink(linkPrevious, "Previous");
		dto.addLink(linkSelf, "Self");
		dto.addLink(linkNext, "Next");
		dto.addLink(linkLast, "Last");

		return dto;
		
	}

	private String getLinkFirt(UriInfo uriInfo, ArrayList<TelefonoDTO> list) {
		return uriInfo.getBaseUriBuilder().path(TelefonoResource.class)
				.path(list.get(0).getNumero()).build().toString();
	}

	private String getLinkPrevious(UriInfo uriInfo, String num) {
		TelefonoDTO telefono = new TelefonoDTO(telefonoEJB.getPreviusTelefono(num));
		String linkPrevious = null;
		if (telefono.getNumero() != null) {
			linkPrevious = uriInfo.getBaseUriBuilder().path(TelefonoResource.class)
					.path(telefono.getNumero()).build().toString();
		}
		return linkPrevious;
	}

	private String getlinkSelf(UriInfo uriInfo) {
		return uriInfo.getAbsolutePath().toString();
	}

	private String getLinkNext(UriInfo uriInfo, String num) {
		TelefonoDTO telefono = new TelefonoDTO(telefonoEJB.getNexTelefono(num));
		String linkNext = null;
		if (telefono.getNumero() != null) {
			linkNext = uriInfo.getBaseUriBuilder().path(TelefonoResource.class)
					.path(telefono.getNumero()).build().toString();
		}
		return linkNext;
	}

	private String getLinkLast(UriInfo uriInfo, ArrayList<TelefonoDTO> list) {
		return uriInfo.getBaseUriBuilder().path(TelefonoResource.class)
				.path(list.get(list.size() - 1).getNumero()).build().toString();
	}

	@POST
	@ApiOperation(value = " Crea un nuevo registro de tipo Telefono ", notes = " Crea un nuevo Telefono.CLass ", response = Telefono.class, responseContainer = "201")
	// @StatusCreated
	public Response createTelefono(TelefonoDTO dto, @Context UriInfo uriInfo) {
		TelefonoDTO telefonoDTO= new TelefonoDTO(telefonoEJB.createTelefono(dto.toEntity()));
		URI uri=uriInfo.getAbsolutePathBuilder().path(String.valueOf(telefonoDTO.getNumero())).build();
		return Response.created(uri).entity(telefonoDTO).build();
	}

	@PUT
	@Path("{num: \\d+}")
	@ApiOperation(value = " Actualiza un registro de la lista de Telefono ", notes = " Actualiza un registro con un {num} ", response = Telefono.class, responseContainer = "Telefono.Class")
	public TelefonoDTO updateTelefono(
			@ApiParam(value = " Num del Telefono ", required = true) @PathParam("num") String num, TelefonoDTO dto) {

		Telefono entity = dto.toEntity();
		entity.setNumero(num);
		
		return new TelefonoDTO(telefonoEJB.updateTelefono(entity));
	}

	@DELETE
	@Path("{num: \\d+}")
	@ApiOperation(value = " Elimina un registro de la lista de Telefonos ", notes = " Elimina un registro con un {num} ", responseContainer = "200")
	public void deletePolicia(@ApiParam(value = " Num del Telefono ", required = true) @PathParam("num") String num) {
		telefonoEJB.deleteTelefono(telefonoEJB.getTelefonoById(num));
	}
	
}