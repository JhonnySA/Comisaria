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

import csv.comisaria.com.domain.Arsenal;
import csv.comisaria.com.excepciones.DatosNoEncontradosExcepcion;
import csv.comisaria.com.services.dto.ArsenalDTO;
import csv.comisaria.com.services.ejb.ArsenalEJBInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Path("arsenals")
@io.swagger.annotations.Api(value = "/arsenals")
public class ArsenalResource {

	@Inject
	ArsenalEJBInterface arsenalEJB;

	@Context
	private HttpServletResponse response;
	@QueryParam("page")
	private Integer page;
	@QueryParam("limit")
	private Integer maxRecords;

	private List<ArsenalDTO> listEntity2DTO(List<Arsenal> entityList) {
		List<ArsenalDTO> list = new ArrayList<>();
		for (Arsenal entity : entityList) {
			list.add(new ArsenalDTO(entity));
		}
		return list;
	}

	@GET
	@ApiOperation(value = " Devuelve la lista de Arsenals ", notes = " Lista completa ", response = Arsenal.class, responseContainer = " List ")
	public List<ArsenalDTO> getArsenales() {

		if (page != null && maxRecords != null) {
			this.response.setIntHeader("X-Total-Count", arsenalEJB.countArsenales());
			return listEntity2DTO(arsenalEJB.getArsenales(page, maxRecords));
		}
		return listEntity2DTO(arsenalEJB.getArsenales());
	}

	@GET
	@Path("/{cod: \\d+}")
	@ApiOperation(value = " Devuelve un registro de la lista de Arsenals ", notes = " Un Arsenals especifico ", response = Arsenal.class, responseContainer = "Arsenal.Class")
	public ArsenalDTO getArsenal(@ApiParam(value = " Cod del Arsenal ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true) @PathParam("cod") int cod, @Context UriInfo uriInfo) {

		ArsenalDTO dto = new ArsenalDTO(arsenalEJB.getArsenalById(cod));
		ArrayList<ArsenalDTO> list = (ArrayList<ArsenalDTO>) listEntity2DTO(arsenalEJB.getArsenales());

		String linkNext = null;
		String linkFirt;
		String linkPrevious;
		String linkSelf;
		String linkLast;

		if (dto.getCodArsenal() == 0) {
			throw new DatosNoEncontradosExcepcion(
					"No se ha encontrado el arsenal con el cod: " + cod + " , probablemente fue removido del servidor");
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

	private String getLinkFirt(UriInfo uriInfo, ArrayList<ArsenalDTO> list) {
		return uriInfo.getBaseUriBuilder().path(ArsenalResource.class)
				.path(Integer.toString(list.get(0).getCodArsenal())).build().toString();
	}

	private String getLinkPrevious(UriInfo uriInfo, int cod) {
		ArsenalDTO arsenal = new ArsenalDTO(arsenalEJB.getPreviusArsenal(cod));
		String linkPrevious = null;
		if (arsenal != null) {
			linkPrevious = uriInfo.getBaseUriBuilder().path(ArsenalResource.class)
					.path(Integer.toString(arsenal.getCodArsenal())).build().toString();
		}
		return linkPrevious;
	}

	private String getlinkSelf(UriInfo uriInfo) {
		return uriInfo.getAbsolutePath().toString();
	}

	private String getLinkNext(UriInfo uriInfo, int cod) {
		ArsenalDTO arsenal = new ArsenalDTO(arsenalEJB.getNexArsenal(cod));
		String linkNext = null;
		if (arsenal != null) {
			linkNext = uriInfo.getBaseUriBuilder().path(ArsenalResource.class)
					.path(Integer.toString(arsenal.getCodArsenal())).build().toString();
		}
		return linkNext;
	}

	private String getLinkLast(UriInfo uriInfo, ArrayList<ArsenalDTO> list) {
		return uriInfo.getBaseUriBuilder().path(ArsenalResource.class)
				.path(Integer.toString(list.get(list.size() - 1).getCodArsenal())).build().toString();
	}

	@POST
	@ApiOperation(value = " Crea un nuevo registro de tipo Arsenal ", notes = " Crea un nuevo Arsenal.CLass ", response = Arsenal.class, responseContainer = "201")
	// @StatusCreated
	public Response createArsenal(ArsenalDTO dto, @Context UriInfo uriInfo) {
		ArsenalDTO arsenalDTO = new ArsenalDTO(arsenalEJB.createArsenal(dto.toEntity()));
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(arsenalDTO.getCodArsenal())).build();
		return Response.created(uri).entity(arsenalDTO).build();
	}

	@PUT
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Actualiza un registro de la lista de Arsenals ", notes = " Actualiza un registro con un {codArsenal} ", response = Arsenal.class, responseContainer = "Arsenal.Class")
	public ArsenalDTO updateArsenal(@ApiParam(value = " Cod del Arsenal ", allowableValues = "range[1,"
			+ Integer.MAX_VALUE + "]", required = true) @PathParam("cod") int cod, ArsenalDTO dto) {

		Arsenal entity = dto.toEntity();
		entity.setCodArsenal(cod);

		Arsenal oldEntity = arsenalEJB.getArsenalById(cod);
		entity.setPoliciaarsenals(oldEntity.getPoliciaarsenals());

		return new ArsenalDTO(arsenalEJB.updateArsenal(entity));
	}

	public void existsArsenal(int codArsenal) {

		Arsenal arsenal = arsenalEJB.getArsenalById(codArsenal);
		if (arsenal == null) {
			throw new WebApplicationException(404);
		}
	}

	@DELETE
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Elimina un registro de la lista de Arsenals ", notes = " Elimina un registro con un {codArsenal} ", responseContainer = "200")
	public void deleteArsenal(@ApiParam(value = " Cod del Arsenal ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true) @PathParam("cod") int cod) {
		arsenalEJB.deleteArsenal(arsenalEJB.getArsenalById(cod));
	}

	// creando el subservicio
	// http://localhost:8080/apiVentasCorp/v1/arsenal/2/prestamoArsenals

	@Path("{codArsenal: \\d+}/prestamos")
	@ApiOperation(value = " Sub-Servicio ", notes = " Muestra los detalles de un prestamo ", response = PoliciaArsenalResource.class, responseContainer = "List")
	public Class<PoliciaArsenalResource> getPoliciaArsenalResource(@PathParam("codArsenal") int codArsenal) {
		existsArsenal(codArsenal);
		return PoliciaArsenalResource.class;
	}

}
