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

import csv.comisaria.com.domain.Policia;
import csv.comisaria.com.excepciones.DatosNoEncontradosExcepcion;
import csv.comisaria.com.services.dto.PoliciaDTO;
import csv.comisaria.com.services.ejb.PoliciaEJBInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Path("policias")
@io.swagger.annotations.Api(value = "/policias")
public class PoliciaResource {

	@Inject
	PoliciaEJBInterface policiaEJB;

	@Context
	private HttpServletResponse response;
	@QueryParam("page")
	private Integer page;
	@QueryParam("limit")
	private Integer maxRecords;

	private List<PoliciaDTO> listEntity2DTO(List<Policia> entityList) {
		List<PoliciaDTO> list = new ArrayList<>();
		for (Policia entity : entityList) {
			list.add(new PoliciaDTO(entity));
		}
		return list;
	}

	@GET
	@ApiOperation(value = " Devuelve la lista de Policias ", notes = " Lista completa ", response = Policia.class, responseContainer = " List ")
	public List<PoliciaDTO> getPolicias() {

		if (page != null && maxRecords != null) {
			this.response.setIntHeader("X-Total-Count", policiaEJB.countPolicias());
			return listEntity2DTO(policiaEJB.getPolicias(page, maxRecords));
		}
		return listEntity2DTO(policiaEJB.getPolicias());
	}

	@GET
	@Path("/{cod: \\d+}")
	@ApiOperation(value = " Devuelve un registro de la lista de Policias ", notes = " Un Policia especifico ", response = Policia.class, responseContainer = "Policia.Class")
	public PoliciaDTO getPolicia(@ApiParam(value = " Cod del Arsenal ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true) @PathParam("cod") int cod, @Context UriInfo uriInfo) {
		PoliciaDTO dto = new PoliciaDTO(policiaEJB.getPoliciaById(cod));
		ArrayList<PoliciaDTO> list = (ArrayList<PoliciaDTO>) listEntity2DTO(policiaEJB.getPolicias());

		String linkNext = null;
		String linkFirt;
		String linkPrevious;
		String linkSelf;
		String linkLast;

		if (dto.getCodPolicia() == 0) {
			throw new DatosNoEncontradosExcepcion(
					"No se ha encontrado el policia con el cod: " + cod + " , probablemente fue removido del servidor");
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

	private String getLinkFirt(UriInfo uriInfo, ArrayList<PoliciaDTO> list) {
		return uriInfo.getBaseUriBuilder().path(PoliciaResource.class)
				.path(Integer.toString(list.get(0).getCodPolicia())).build().toString();
	}

	private String getLinkPrevious(UriInfo uriInfo, int cod) {
		PoliciaDTO policia = new PoliciaDTO(policiaEJB.getPreviusPolicia(cod));
		String linkPrevious = null;
		if (policia != null) {
			linkPrevious = uriInfo.getBaseUriBuilder().path(PoliciaResource.class)
					.path(Integer.toString(policia.getCodPolicia())).build().toString();
		}
		return linkPrevious;
	}

	private String getlinkSelf(UriInfo uriInfo) {
		return uriInfo.getAbsolutePath().toString();
	}

	private String getLinkNext(UriInfo uriInfo, int cod) {
		PoliciaDTO policia = new PoliciaDTO(policiaEJB.getNexPolicia(cod));
		String linkNext = null;
		if (policia != null) {
			linkNext = uriInfo.getBaseUriBuilder().path(PoliciaResource.class)
					.path(Integer.toString(policia.getCodPolicia())).build().toString();
		}
		return linkNext;
	}

	private String getLinkLast(UriInfo uriInfo, ArrayList<PoliciaDTO> list) {
		return uriInfo.getBaseUriBuilder().path(PoliciaResource.class)
				.path(Integer.toString(list.get(list.size() - 1).getCodPolicia())).build().toString();
	}

	@POST
	@ApiOperation(value = " Crea un nuevo registro de tipo Policia ", notes = " Crea un nuevo Policia.CLass ", response = Policia.class, responseContainer = "201")
	// @StatusCreated
	public Response createPolicia(PoliciaDTO dto, @Context UriInfo uriInfo) {
		PoliciaDTO policiaDTO=new PoliciaDTO(policiaEJB.createPolicia(dto.toEntity()));
		URI uri= uriInfo.getAbsolutePathBuilder().path(String.valueOf(policiaDTO.getCodPolicia())).build();
		return Response.created(uri).entity(policiaDTO).build();
	}

	@PUT
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Actualiza un registro de la lista de Policias ", notes = " Actualiza un registro con un {codPolicia} ", response = Policia.class, responseContainer = "Policia.Class")
	public PoliciaDTO updatePolicia(@ApiParam(value = " Cod del Policia ", allowableValues = "range[1,"
			+ Integer.MAX_VALUE + "]", required = true) @PathParam("cod") int cod, PoliciaDTO dto) {

		Policia entity = dto.toEntity();
		entity.setCodPolicia(cod);

		Policia oldEntity = policiaEJB.getPoliciaById(cod);

		entity.setArrestos(oldEntity.getArrestos());
		entity.setPoliciaarsenals(oldEntity.getPoliciaarsenals());

		return new PoliciaDTO(policiaEJB.updatePolicia(entity));
	}

	@DELETE
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Elimina un registro de la lista de Policias ", notes = " Elimina un registro con un {codPolicia} ", responseContainer = "200")
	public void deletePolicia(@ApiParam(value = " Cod del Policia ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true) @PathParam("cod") int cod) {
		policiaEJB.deletePolicia(policiaEJB.getPoliciaById(cod));
	}

	public void existsPolicia(int codPolicia) {

		Policia policia = policiaEJB.getPoliciaById(codPolicia);
		if (policia == null) {
			throw new WebApplicationException(404);
		}
	}

	// creando el subservicio
	// http://localhost:8080/apiVentasCorp/v1/policias/2/prestamoArsenals

	@Path("{codPolicia: \\d+}/prestamos")
	public Class<PoliciaArsenalResource> getPoliciaArsenalResource(@PathParam("codPolicia") int codPolicia) {
		existsPolicia(codPolicia);
		return PoliciaArsenalResource.class;
	}

	// creando el subservicio
	// http://localhost:8080/apiVentasCorp/v1/policias/2/arrestos

	@Path("{codPolicia: \\d+}/arrestos")
	public Class<PoliciaDelincuenteResource> getPoliciaDelincuenteResource(@PathParam("codPolicia") int codPolicia) {
		existsPolicia(codPolicia);
		return PoliciaDelincuenteResource.class;
	}
	
}