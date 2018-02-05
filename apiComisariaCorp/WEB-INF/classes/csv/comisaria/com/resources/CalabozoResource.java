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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import csv.comisaria.com.domain.Calabozo;
import csv.comisaria.com.excepciones.DatosNoEncontradosExcepcion;
import csv.comisaria.com.services.dto.CalabozoDTO;
import csv.comisaria.com.services.ejb.CalabozoEJBInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Path("calabozos")
@io.swagger.annotations.Api(value = "/calabozos")
public class CalabozoResource {

	@Inject
	CalabozoEJBInterface calabozoEJB;

	@Context
	private HttpServletResponse response;
	@QueryParam("page")
	private Integer page;
	@QueryParam("limit")
	private Integer maxRecords;

	private List<CalabozoDTO> listEntity2DTO(List<Calabozo> entityList) {
		List<CalabozoDTO> list = new ArrayList<>();
		for (Calabozo entity : entityList) {
			list.add(new CalabozoDTO(entity));
		}
		return list;
	}

	@GET
	@ApiOperation(value = " Devuelve la lista de Calabozos ", notes = " Lista completa ", response = Calabozo.class, responseContainer = " List ")
	public List<CalabozoDTO> getCalabozos() {

		if (page != null && maxRecords != null) {
			this.response.setIntHeader("X-Total-Count", calabozoEJB.countCalabozos());
			return listEntity2DTO(calabozoEJB.getCalabozos(page, maxRecords));
		}
		return listEntity2DTO(calabozoEJB.getCalabozos());
	}

	@GET
	@Path("/{cod: \\d+}")
	@ApiOperation(value = " Devuelve un registro de la lista de Calabozos ", notes = " Un Calabozo especifico ", response = Calabozo.class, responseContainer = "Calabozo.Class")
	public CalabozoDTO getCalabozo(@ApiParam(value = " Cod del Calabozo ", allowableValues = "range[1,"
			+ Integer.MAX_VALUE + "]", required = true) @PathParam("cod") int cod, @Context UriInfo uriInfo) {
		
		CalabozoDTO dto = new CalabozoDTO(calabozoEJB.getCalabozoById(cod));
		ArrayList<CalabozoDTO> list = (ArrayList<CalabozoDTO>) listEntity2DTO(calabozoEJB.getCalabozos());
		
		String linkNext = null;
		String linkFirt;
		String linkPrevious;
		String linkSelf;
		String linkLast;

		if (dto.getCodCalabozo() == 0) {
			throw new DatosNoEncontradosExcepcion(
					"No se ha encontrado el calabozo con el cod: " + cod + " , probablemente fue removido del servidor");
		} else {

			linkFirt = getLinkFirt(uriInfo, list);
			linkPrevious = getLinkPrevious(uriInfo, cod);
			linkSelf = getlinkSelf(uriInfo);
			linkNext = getLinkNext(uriInfo, cod);
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

	private String getLinkFirt(UriInfo uriInfo, ArrayList<CalabozoDTO> list) {
		return uriInfo.getBaseUriBuilder().path(CalabozoResource.class)
				.path(Integer.toString(list.get(0).getCodCalabozo())).build().toString();
	}

	private String getLinkPrevious(UriInfo uriInfo, int cod) {
		CalabozoDTO calabozo = new CalabozoDTO(calabozoEJB.getPreviusCalabozo(cod));
		String linkPrevious = null;
		if (calabozo != null) {
			linkPrevious = uriInfo.getBaseUriBuilder().path(CalabozoResource.class)
					.path(Integer.toString(calabozo.getCodCalabozo())).build().toString();
		}
		return linkPrevious;
	}

	private String getlinkSelf(UriInfo uriInfo) {
		return uriInfo.getAbsolutePath().toString();
	}

	private String getLinkNext(UriInfo uriInfo, int cod) {
		CalabozoDTO calabozo = new CalabozoDTO(calabozoEJB.getNexCalabozo(cod));
		String linkNext = null;
		if (calabozo != null) {
			linkNext = uriInfo.getBaseUriBuilder().path(CalabozoResource.class)
					.path(Integer.toString(calabozo.getCodCalabozo())).build().toString();
		}
		return linkNext;
	}

	private String getLinkLast(UriInfo uriInfo, ArrayList<CalabozoDTO> list) {
		return uriInfo.getBaseUriBuilder().path(CalabozoResource.class)
				.path(Integer.toString(list.get(list.size() - 1).getCodCalabozo())).build().toString();
	}

	@POST
	@ApiOperation(value = " Crea un nuevo registro de tipo Calabozo ", notes = " Crea un nuevo Calabozo.CLass ", response = Calabozo.class, responseContainer = "201")
	// @StatusCreated
	public Response createCalabozo(CalabozoDTO dto, @Context UriInfo uriInfo) {
		CalabozoDTO calabozoDTO= new CalabozoDTO(calabozoEJB.createCalabozo(dto.toEntity()));;
		URI uri=uriInfo.getAbsolutePathBuilder().path(String.valueOf(calabozoDTO.getCodCalabozo())).build();
		return Response.created(uri).entity(calabozoDTO).build();
	}

	@PUT
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Actualiza un registro de la lista de Calabozos ", notes = " Actualiza un registro con un {codCalabozo} ", response = Calabozo.class, responseContainer = "Calabozo.Class")
	public CalabozoDTO updateCalabozo(@ApiParam(value = " Cod del Calabozo ", allowableValues = "range[1,"
			+ Integer.MAX_VALUE + "]", required = true)
			@PathParam("cod") int cod, CalabozoDTO dto) {

		Calabozo entity = dto.toEntity();
		entity.setCodCalabozo(cod);
		Calabozo oldEntity = calabozoEJB.getCalabozoById(cod);
		entity.setUbicacion(oldEntity.getUbicacion());
		entity.setDelincuentes(oldEntity.getDelincuentes());

		return new CalabozoDTO(calabozoEJB.updateCalabozo(entity));

	}

	@DELETE
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Elimina un registro de la lista de Calabozos ", notes = " Elimina un registro con un {codCalabozo} ", responseContainer = "200")
	public void deleteCliente(@ApiParam(value = " Cod del Calabozo ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true)
			@PathParam("cod") int cod) {
		calabozoEJB.deleteCalabozo(calabozoEJB.getCalabozoById(cod));
	}

	public void existsCliente(int codCalabozo) {

		Calabozo calabozo = calabozoEJB.getCalabozoById(codCalabozo);
		if (calabozo == null) {
			throw new WebApplicationException(404);
		}
	}

	// creando el subservicio
	// http://localhost:8080/apiVentasCorp/v1/calabozos/2/delincuentes

	@Path("{codCalabozo: \\d+}/delincuentes")
	public Class<CalabozoDelincuenteResource> getClienteVentasResource(@PathParam("codCalabozo") int codCalabozo) {
		existsCliente(codCalabozo);
		return CalabozoDelincuenteResource.class;
	}

}