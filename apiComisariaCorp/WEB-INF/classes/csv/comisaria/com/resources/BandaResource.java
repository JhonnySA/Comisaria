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

import csv.comisaria.com.domain.Arsenal;
import csv.comisaria.com.domain.Banda;
import csv.comisaria.com.excepciones.DatosNoEncontradosExcepcion;
import csv.comisaria.com.services.dto.BandaDTO;
import csv.comisaria.com.services.ejb.BandaEJBInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Path("bandas")
@io.swagger.annotations.Api(value = "/bandas")
public class BandaResource {

	@Inject
	BandaEJBInterface bandaEJB;

	@Context
	private HttpServletResponse response;
	@QueryParam("page")
	private Integer page;
	@QueryParam("limit")
	private Integer maxRecords;

	private List<BandaDTO> listEntity2DTO(List<Banda> entityList) {
		List<BandaDTO> list = new ArrayList<>();
		for (Banda entity : entityList) {
			list.add(new BandaDTO(entity));
		}
		return list;
	}

	@GET
	@ApiOperation(value = " Devuelve la lista de Bandas ", notes = " Lista completa ", response = Arsenal.class, responseContainer = " List ")
	public List<BandaDTO> getBandas() {

		if (page != null && maxRecords != null) {
			this.response.setIntHeader("X-Total-Count", bandaEJB.countBandas());
			return listEntity2DTO(bandaEJB.getBandas(page, maxRecords));
		}
		return listEntity2DTO(bandaEJB.getBandas());
	}

	@GET
	@Path("/{cod: \\d+}")
	@ApiOperation(value = " Devuelve un registro de la lista de Bandas ", notes = " Una Banda especifica ", response = Banda.class, responseContainer = "Banda.Class")
	public BandaDTO getBanda(@ApiParam(value = " Cod de Banda ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true) @PathParam("cod") int cod, @Context UriInfo uriInfo) {
		
		BandaDTO dto = new BandaDTO(bandaEJB.getBandaById(cod));
		ArrayList<BandaDTO> list = (ArrayList<BandaDTO>) listEntity2DTO(bandaEJB.getBandas());
		
		String linkNext = null;
		String linkFirt;
		String linkPrevious;
		String linkSelf;
		String linkLast;

		if (dto.getCodBanda() == 0) {
			throw new DatosNoEncontradosExcepcion(
					"No se ha encontrado la banda con el cod: " + cod + " , probablemente fue removido del servidor");
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

	private String getLinkFirt(UriInfo uriInfo, ArrayList<BandaDTO> list) {
		return uriInfo.getBaseUriBuilder().path(BandaResource.class)
				.path(Integer.toString(list.get(0).getCodBanda())).build().toString();
	}

	private String getLinkPrevious(UriInfo uriInfo, int cod) {
		BandaDTO banda = new BandaDTO(bandaEJB.getPreviusBanda(cod));
		String linkPrevious = null;
		if (banda != null) {
			linkPrevious = uriInfo.getBaseUriBuilder().path(BandaResource.class)
					.path(Integer.toString(banda.getCodBanda())).build().toString();
		}
		return linkPrevious;
	}

	private String getlinkSelf(UriInfo uriInfo) {
		return uriInfo.getAbsolutePath().toString();
	}

	private String getLinkNext(UriInfo uriInfo, int cod) {
		BandaDTO banda = new BandaDTO(bandaEJB.getNexBanda(cod));
		String linkNext = null;
		if (banda != null) {
			linkNext = uriInfo.getBaseUriBuilder().path(BandaResource.class)
					.path(Integer.toString(banda.getCodBanda())).build().toString();
		}
		return linkNext;
	}

	private String getLinkLast(UriInfo uriInfo, ArrayList<BandaDTO> list) {
		return uriInfo.getBaseUriBuilder().path(BandaResource.class)
				.path(Integer.toString(list.get(list.size() - 1).getCodBanda())).build().toString();
	}

	@POST
	@ApiOperation(value = " Crea un nuevo registro de tipo Banda ", notes = " Crea un nuevo Banda.CLass ", response = Banda.class, responseContainer = "201")
	// @StatusCreated
	public Response createBanda(BandaDTO dto, @Context UriInfo uriInfo) {
		BandaDTO bandaDTO= new BandaDTO((bandaEJB.createBanda(dto.toEntity())));
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(bandaDTO.getCodBanda())).build();
		return Response.created(uri).entity(bandaDTO).build();
	}

	@PUT
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Actualiza un registro de la lista de Bandas ", notes = " Actualiza un registro con un {codBanda} ", response = Banda.class, responseContainer = "Banda.Class")
	public BandaDTO updateBanda(@ApiParam(value = " Cod de la Banda ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true) @PathParam("cod") int cod, BandaDTO dto) {

		Banda entity = dto.toEntity();
		entity.setCodBanda(cod);

		Banda oldEntity = bandaEJB.getBandaById(cod);
		
		entity.setDelincuentes(oldEntity.getDelincuentes());
		
		return new BandaDTO(bandaEJB.updateBanda(entity));

	}

	@DELETE
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Elimina un registro de la lista de Bandas ", notes = " Elimina un registro con un {codBanda} ", responseContainer = "200")
	public void deleteArsenal(@ApiParam(value = " Cod de la Banda ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true) @PathParam("cod") int cod) {
		bandaEJB.deleteBanda(bandaEJB.getBandaById(cod));
	}
	
}
