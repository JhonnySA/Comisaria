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

import csv.comisaria.com.domain.Delincuente;
import csv.comisaria.com.excepciones.DatosNoEncontradosExcepcion;
import csv.comisaria.com.services.dto.DelincuenteDTO;
import csv.comisaria.com.services.ejb.DelincuenteEJBInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Path("delincuentes")
@io.swagger.annotations.Api(value = "/delincuentes")
public class DelincuenteResource {

	@Inject
	DelincuenteEJBInterface delincuenteEJB;

	@Context
	private HttpServletResponse response;
	@QueryParam("page")
	private Integer page;
	@QueryParam("limit")
	private Integer maxRecords;

	private List<DelincuenteDTO> listEntity2DTO(List<Delincuente> entityList) {
		List<DelincuenteDTO> list = new ArrayList<>();
		for (Delincuente entity : entityList) {
			list.add(new DelincuenteDTO(entity));
		}
		return list;
	}

	@GET
	@ApiOperation(value = " Devuelve la lista de Delincuentes ", notes = " Lista completa ", response = Delincuente.class, responseContainer = " List ")
	public List<DelincuenteDTO> getDelincuentes() {

		if (page != null && maxRecords != null) {
			this.response.setIntHeader("X-Total-Count", delincuenteEJB.countDelincuentes());
			return listEntity2DTO(delincuenteEJB.getDelincuente(page, maxRecords));
		}
		return listEntity2DTO(delincuenteEJB.getDelincuentes());
	}

	@GET
	@Path("/{dni: \\d+}")
	@ApiOperation(value = " Devuelve un registro de la lista de Delincuentes ", notes = " Un Delincuente especifico ", response = Delincuente.class, responseContainer = "Delincuente.Class")
	public DelincuenteDTO getDelincuente(
			@ApiParam(value = " Dni del delincuente ", required = true) @PathParam("dni") String dni, @Context UriInfo uriInfo) {
		DelincuenteDTO dto = new DelincuenteDTO(delincuenteEJB.getDelincuenteById(dni));
		ArrayList<DelincuenteDTO> list = (ArrayList<DelincuenteDTO>) listEntity2DTO(delincuenteEJB.getDelincuentes());
		
		String linkNext = null;
		String linkFirt;
		String linkPrevious;
		String linkSelf;
		String linkLast;

		if (dto.getDni()==null) {
			throw new DatosNoEncontradosExcepcion(
					"No se ha encontrado el delincuente con el DNI: " + dni + " , probablemente fue removido del servidor");
		} else {

			linkFirt = getLinkFirt(uriInfo, list);
			linkPrevious = getLinkPrevious(uriInfo, dni);
			linkSelf = getlinkSelf(uriInfo);
			linkNext = getLinkNext(uriInfo, dni);
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

	private String getLinkFirt(UriInfo uriInfo, ArrayList<DelincuenteDTO> list) {
		return uriInfo.getBaseUriBuilder().path(DelincuenteResource.class)
				.path(list.get(0).getDni()).build().toString();
	}

	private String getLinkPrevious(UriInfo uriInfo, String dni) {
		DelincuenteDTO delincuente = new DelincuenteDTO(delincuenteEJB.getPreviusDelincuente(dni));
		String linkPrevious = null;
		if (delincuente.getDni() != null) {
			linkPrevious = uriInfo.getBaseUriBuilder().path(DelincuenteResource.class)
					.path(delincuente.getDni()).build().toString();
		}
		return linkPrevious;
	}

	private String getlinkSelf(UriInfo uriInfo) {
		return uriInfo.getAbsolutePath().toString();
	}

	private String getLinkNext(UriInfo uriInfo, String dni) {
		DelincuenteDTO delincuente = new DelincuenteDTO(delincuenteEJB.getNexDelincuente(dni));
		String linkNext = null;
		if (delincuente.getDni() != null) {
			linkNext = uriInfo.getBaseUriBuilder().path(DelincuenteResource.class)
					.path(delincuente.getDni()).build().toString();
		}
		return linkNext;
	}

	private String getLinkLast(UriInfo uriInfo, ArrayList<DelincuenteDTO> list) {
		return uriInfo.getBaseUriBuilder().path(DelincuenteResource.class)
				.path(list.get(list.size() - 1).getDni()).build().toString();
	}

	@POST
	@ApiOperation(value = " Crea un nuevo registro de tipo Delincuente ", notes = " Crea un nuevo Delincuente.CLass ", response = Delincuente.class, responseContainer = "201")
	// @StatusCreated
	public Response createDelincuente(DelincuenteDTO dto, @Context UriInfo uriInfo) {
		DelincuenteDTO delincuenteDTO= new DelincuenteDTO(delincuenteEJB.createDelincuente(dto.toEntity()));
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(delincuenteDTO.getDni())).build();
		return Response.created(uri).entity(delincuenteDTO).build();
	}

	@PUT
	@Path("{dni: \\d+}")
	@ApiOperation(value = " Actualiza un registro de la lista de Delincuentes ", notes = " Actualiza un registro con un {dni} ", response = Delincuente.class, responseContainer = "Delincuente.Class")
	public DelincuenteDTO updateDelincuente(
			@ApiParam(value = " Dni del delincuente ", required = true) @PathParam("dni") String dni,
			DelincuenteDTO dto) {

		Delincuente entity = dto.toEntity();
		entity.setDni(dni);

		Delincuente oldEntity = delincuenteEJB.getDelincuenteById(dni);
		
		entity.setArrestos(oldEntity.getArrestos());
		entity.setBandas(oldEntity.getBandas());
		entity.setDelincuentecasos(oldEntity.getDelincuentecasos());
		entity.setTelefonos(oldEntity.getTelefonos());

		return new DelincuenteDTO(delincuenteEJB.updateDelincuente(entity));
	}

	@DELETE
	@Path("{dni: \\d+}")
	@ApiOperation(value = " Elimina un registro de la lista de Delincuentes ", notes = " Elimina un registro con un {dni} ", responseContainer = "200")
	public void deleteClase(@ApiParam(value = " Dni del delincuente ", required = true) @PathParam("dni") String dni) {
		delincuenteEJB.deleteDelincuente(delincuenteEJB.getDelincuenteById(dni));
	}

	public void existsDelincuente(String dni) {

		Delincuente delincuente = delincuenteEJB.getDelincuenteById(dni);
		if (delincuente == null) {
			throw new WebApplicationException(404);
		}
	}

	// creando el subservicio
	// http://localhost:8080/apiVentasCorp/v1/delicuentes/99999999/detalles

	@Path("{dni: \\d+}/detalles")
	public Class<DelincuenteCasoResource> getDelincuenteCasoResource(@PathParam("dni") String dni) {
		existsDelincuente(dni);
		return DelincuenteCasoResource.class;
	}

	// creando el subservicio
	// http://localhost:8080/apiVentasCorp/v1/delicuentes/99999999/arrestos

	@Path("{dni: \\d+}/arrestos")
	public Class<PoliciaDelincuenteResource> getPoliciaDelincuenteResource(@PathParam("dni") String dni) {
		existsDelincuente(dni);
		return PoliciaDelincuenteResource.class;
	}

	// creando el subservicio
	// http://localhost:8080/apiVentasCorp/v1/delincuentes/99999999/telefonos

	@Path("{dni: \\d+}/telefonos")
	public Class<DelincuenteTelefonoResource> getDelincuenteTelefonoResource(@PathParam("dni") String dni) {
		existsDelincuente(dni);
		return DelincuenteTelefonoResource.class;
	}

}