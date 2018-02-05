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
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import csv.comisaria.com.domain.Arsenal;
import csv.comisaria.com.domain.Clase;
import csv.comisaria.com.excepciones.DatosNoEncontradosExcepcion;
import csv.comisaria.com.services.dto.ClaseDTO;
import csv.comisaria.com.services.ejb.ClaseEJBInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Path("clases")
@io.swagger.annotations.Api(value = "/clases")
public class ClaseResource {

	@Inject
	ClaseEJBInterface claseEJB;

	@Context
	private HttpServletResponse response;

	private List<ClaseDTO> listEntity2DTO(List<Clase> entityList) {
		List<ClaseDTO> list = new ArrayList<>();
		for (Clase entity : entityList) {
			list.add(new ClaseDTO(entity));
		}
		return list;
	}

	@GET
	@Path("query")
	@ApiOperation(value = " Devuelve la lista de Clases ", notes = " Lista completa ", response = Arsenal.class, responseContainer = " List ")
	public List<ClaseDTO> getClasesPage(@QueryParam("page") Integer page, @QueryParam("limit") Integer limit,
			@Context UriInfo uriInfo) {

		if (page <= (claseEJB.countClases() / limit) + 1) {
			
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.queryParam("page", page); builder.queryParam("limit", limit);
			ArrayList<ClaseDTO> list = (ArrayList<ClaseDTO>)listEntity2DTO(claseEJB.getClases(page, limit));
			
			String selfLink = builder.toString();
			
			//UriBuilder builderNext = uriInfo.getAbsolutePathBuilder();
			//builder.queryParam("page", page+1); builder.queryParam("limit", limit);
			
			ClaseDTO claseDTO = list.get(list.size() - 1); 
			claseDTO.addLink(selfLink,"SelfPAGE");
			//claseDTO.addLink(builderNext.toString(),"PAGE");
			
			return list;
		}
		throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
	}

	/*
	 * UriBuilder builder = uriInfo.getAbsolutePathBuilder();
	 * builder.queryParam("page", page); builder.queryParam("limit", limit);
	 * 
	 * ArrayList<ClaseDTO> list = (ArrayList<ClaseDTO>)
	 * listEntity2DTO(claseEJB.getClases(page, limit));
	 * 
	 * String nextLink = builder.toString();
	 * 
	 * ClaseDTO claseDTO = list.get(list.size() - 1); claseDTO.addLink(nextLink,
	 * "NextPAGE");
	 * 
	 * return list;
	 */

	/*
	 * private boolean existeClase(int codClase) { boolean estado = false; Clase
	 * clase = claseEJB.getClaseById(codClase); if (clase != null) { estado = true;
	 * } return estado; }
	 */

	@GET
	@Path("cantidad")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCantidadClases() {
		return "" + claseEJB.countClases();
	}

	@GET
	@ApiOperation(value = " Devuelve la lista de Clases ", notes = " Lista completa ", response = Arsenal.class, responseContainer = " List ")
	public List<ClaseDTO> getClases() {
		return listEntity2DTO(claseEJB.getClases());
	}

	@GET
	@Path("/{cod: \\d+}")
	@ApiOperation(value = " Devuelve un registro de la lista de Clases ", notes = " Una Clase de Arsenal especifica ", response = Clase.class, responseContainer = "Clase.Class")
	public ClaseDTO getClase(@ApiParam(value = " Cod de la Clase ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true) @PathParam("cod") int cod, @Context UriInfo uriInfo) {

		ClaseDTO dto = new ClaseDTO(claseEJB.getClaseById(cod));
		ArrayList<ClaseDTO> list = (ArrayList<ClaseDTO>) listEntity2DTO(claseEJB.getClases());

		String linkNext = null;
		String linkFirt;
		String linkPrevious;
		String linkSelf;
		String linkLast;

		if (dto.getCodClase() == 0) {
			throw new DatosNoEncontradosExcepcion(
					"No se ha encontrado el clase con el cod: " + cod + " , probablemente fue removido del servidor");
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

	private String getLinkFirt(UriInfo uriInfo, ArrayList<ClaseDTO> list) {
		return uriInfo.getBaseUriBuilder().path(ClaseResource.class).path(Integer.toString(list.get(0).getCodClase()))
				.build().toString();
	}

	private String getLinkPrevious(UriInfo uriInfo, int cod) {
		ClaseDTO clase = new ClaseDTO(claseEJB.getPreviusClase(cod));
		String linkPrevious = null;
		if (clase != null) {
			linkPrevious = uriInfo.getBaseUriBuilder().path(ClaseResource.class)
					.path(Integer.toString(clase.getCodClase())).build().toString();
		}
		return linkPrevious;
	}

	private String getlinkSelf(UriInfo uriInfo) {
		return uriInfo.getAbsolutePath().toString();
	}

	private String getLinkNext(UriInfo uriInfo, int cod) {
		ClaseDTO clase = new ClaseDTO(claseEJB.getNexClase(cod));
		String linkNext = null;
		if (clase != null) {
			linkNext = uriInfo.getBaseUriBuilder().path(ClaseResource.class).path(Integer.toString(clase.getCodClase()))
					.build().toString();
		}
		return linkNext;
	}

	private String getLinkLast(UriInfo uriInfo, ArrayList<ClaseDTO> list) {
		return uriInfo.getBaseUriBuilder().path(ClaseResource.class)
				.path(Integer.toString(list.get(list.size() - 1).getCodClase())).build().toString();
	}

	@POST
	@ApiOperation(value = " Crea un nuevo registro de tipo Clase ", notes = " Crea un nuevo Clase.CLass ", response = Clase.class, responseContainer = "201")
	// @StatusCreated
	public Response createClase(ClaseDTO dto, @Context UriInfo uriInfo) {
		ClaseDTO claseDTO = new ClaseDTO(claseEJB.createClase(dto.toEntity()));
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(claseDTO.getCodClase())).build();
		return Response.created(uri).entity(claseDTO).build();
	}

	@PUT
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Actualiza un registro de la lista de Clases ", notes = " Actualiza un registro con un {codClase} ", response = Clase.class, responseContainer = "Clase.Class")
	public ClaseDTO updateClase(@ApiParam(value = " Cod de la Clase ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true) @PathParam("cod") int cod, ClaseDTO dto) {

		Clase entity = dto.toEntity();
		entity.setCodClase(cod);

		Clase oldEntity = claseEJB.getClaseById(cod);

		entity.setArsenals(oldEntity.getArsenals());

		return new ClaseDTO(claseEJB.updateClase(entity));
	}

	@DELETE
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Elimina un registro de la lista de Clases ", notes = " Elimina un registro con un {codClase} ", responseContainer = "200")
	public void deleteClase(@ApiParam(value = " Cod de la clase ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true) @PathParam("cod") int cod) {
		claseEJB.deleteClase(claseEJB.getClaseById(cod));
	}

	public void existsClase(int codClase) {

		Clase clase = claseEJB.getClaseById(codClase);
		if (clase == null) {
			throw new WebApplicationException(404);
		}
	}

	// creando el subservicio
	// http://localhost:8080/apiVentasCorp/v1/clases/2/arsenales

	@Path("{codClase: \\d+}/arsenals")
	public Class<ClaseArsenalResource> getClaseArsenalResource(@PathParam("codClase") int codClase) {
		existsClase(codClase);
		return ClaseArsenalResource.class;
	}

}
