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

import csv.comisaria.com.domain.Caso;
import csv.comisaria.com.excepciones.DatosNoEncontradosExcepcion;
import csv.comisaria.com.services.dto.CasoDTO;
import csv.comisaria.com.services.ejb.CasoEJBInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Path("casos")
@io.swagger.annotations.Api(value = "/casos")
public class CasoResource {

	@Inject
	CasoEJBInterface casoEJB;

	@Context
	private HttpServletResponse response;
	@QueryParam("page")
	private Integer page;
	@QueryParam("limit")
	private Integer maxRecords;

	private List<CasoDTO> listEntity2DTO(List<Caso> entityList) {
		List<CasoDTO> list = new ArrayList<>();
		for (Caso entity : entityList) {
			list.add(new CasoDTO(entity));
		}
		return list;
	}

	@GET
	@ApiOperation(value = " Devuelve la lista de Casos ", notes = " Lista completa ", response = Caso.class, responseContainer = " List ")
	public List<CasoDTO> getCasos() {

		if (page != null && maxRecords != null) {
			this.response.setIntHeader("X-Total-Count", casoEJB.countCasos());
			return listEntity2DTO(casoEJB.getCasos(page, maxRecords));
		}
		return listEntity2DTO(casoEJB.getCasos());
	}

	@GET
	@Path("/{cod: \\d+}")
	@ApiOperation(value = " Devuelve un registro de la lista de Casos ", notes = " Un Caso especifico ", response = Caso.class, responseContainer = "Caso.Class")
	public CasoDTO getCaso(@ApiParam(value = " Cod del Caso ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true) @PathParam("cod") int cod, @Context UriInfo uriInfo) {
		CasoDTO dto = new CasoDTO(casoEJB.getCasoById(cod));
		ArrayList<CasoDTO> list = (ArrayList<CasoDTO>) listEntity2DTO(casoEJB.getCasos());

		String linkNext = null;
		String linkFirt;
		String linkPrevious;
		String linkSelf;
		String linkLast;

		if (dto.getCodCaso() == 0) {
			throw new DatosNoEncontradosExcepcion(
					"No se ha encontrado el caso con el cod: " + cod + " , probablemente fue removido del servidor");
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

	private String getLinkFirt(UriInfo uriInfo, ArrayList<CasoDTO> list) {
		return uriInfo.getBaseUriBuilder().path(CasoResource.class).path(Integer.toString(list.get(0).getCodCaso()))
				.build().toString();
	}

	private String getLinkPrevious(UriInfo uriInfo, int cod) {
		CasoDTO caso = new CasoDTO(casoEJB.getPreviusCaso(cod));
		String linkPrevious = null;
		if (caso != null) {
			linkPrevious = uriInfo.getBaseUriBuilder().path(CasoResource.class)
					.path(Integer.toString(caso.getCodCaso())).build().toString();
		}
		return linkPrevious;
	}

	private String getlinkSelf(UriInfo uriInfo) {
		return uriInfo.getAbsolutePath().toString();
	}

	private String getLinkNext(UriInfo uriInfo, int cod) {
		CasoDTO caso = new CasoDTO(casoEJB.getNexCaso(cod));
		String linkNext = null;
		if (caso != null) {
			linkNext = uriInfo.getBaseUriBuilder().path(CasoResource.class).path(Integer.toString(caso.getCodCaso()))
					.build().toString();
		}
		return linkNext;
	}

	private String getLinkLast(UriInfo uriInfo, ArrayList<CasoDTO> list) {
		return uriInfo.getBaseUriBuilder().path(CasoResource.class)
				.path(Integer.toString(list.get(list.size() - 1).getCodCaso())).build().toString();
	}

	@POST
	@ApiOperation(value = " Crea un nuevo registro de tipo Caso ", notes = " Crea un nuevo Caso.CLass ", response = Caso.class, responseContainer = "201")
	// @StatusCreated
	public Response createCaso(CasoDTO dto, @Context UriInfo uriInfo) {
		CasoDTO casoDTO = new CasoDTO(casoEJB.createCaso(dto.toEntity()));
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(casoDTO.getCodCaso())).build();
		return Response.created(uri).entity(casoDTO).build();

	}

	@PUT
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Actualiza un registro de la lista de Casos ", notes = " Actualiza un registro con un {codCaso} ", response = Caso.class, responseContainer = "Caso.Class")
	public CasoDTO updateCaso(@ApiParam(value = " Cod del Caso ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true) @PathParam("cod") int cod, CasoDTO dto) {

		Caso entity = dto.toEntity();
		entity.setCodCaso(cod);
		Caso oldEntity = casoEJB.getCasoById(cod);

		entity.setDelincuentecasos(oldEntity.getDelincuentecasos());
		entity.setPolicias(oldEntity.getPolicias());

		return new CasoDTO(casoEJB.updateCaso(entity));
	}

	@DELETE
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Elimina un registro de la lista de Casos ", notes = " Elimina un registro con un {codCaso} ", responseContainer = "200")
	public void deleteCaso(@ApiParam(value = " Cod del Caso ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true) @PathParam("cod") int cod) {
		casoEJB.deleteCaso(casoEJB.getCasoById(cod));
	}

	public void existsCaso(int codCaso) {

		Caso caso = casoEJB.getCasoById(codCaso);
		if (caso == null) {
			throw new WebApplicationException(404);
		}
	}

	// creando el subservicio
	// http://localhost:8080/apiVentasCorp/v1/casos/2/policias

	@Path("{codCaso: \\d+}/policias")
	public Class<CasoPoliciaResource> getCasoPoliciaResource(@PathParam("codCaso") int codCaso) {
		existsCaso(codCaso);
		return CasoPoliciaResource.class;
	}

	// creando el subservicio
	// http://localhost:8080/apiVentasCorp/v1/casos/2/detalles

	@Path("{codCaso: \\d+}/detalles")
	public Class<DelincuenteCasoResource> getDelincuenteCasoResource(@PathParam("codCaso") int codCaso) {
		existsCaso(codCaso);
		return DelincuenteCasoResource.class;
	}

}
