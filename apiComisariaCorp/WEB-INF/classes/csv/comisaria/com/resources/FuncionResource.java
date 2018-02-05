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

import csv.comisaria.com.domain.Funcion;
import csv.comisaria.com.excepciones.DatosNoEncontradosExcepcion;
import csv.comisaria.com.services.dto.FuncionDTO;
import csv.comisaria.com.services.ejb.FuncionEJBInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Path("funciones")
@io.swagger.annotations.Api(value = "/funciones")
public class FuncionResource {

	@Inject
	FuncionEJBInterface funcionEJB;

	@Context
	private HttpServletResponse response;
	@QueryParam("page")
	private Integer page;
	@QueryParam("limit")
	private Integer maxRecords;

	private List<FuncionDTO> listEntity2DTO(List<Funcion> entityList) {
		List<FuncionDTO> list = new ArrayList<>();
		for (Funcion entity : entityList) {
			list.add(new FuncionDTO(entity));
		}
		return list;
	}

	@GET
	@ApiOperation(value = " Devuelve la lista de Funciones ", notes = " Lista completa ", response = Funcion.class, responseContainer = " List ")
	public List<FuncionDTO> getFunciones() {

		if (page != null && maxRecords != null) {
			this.response.setIntHeader("X-Total-Count", funcionEJB.countFunciones());
			return listEntity2DTO(funcionEJB.getFunciones(page, maxRecords));
		}
		return listEntity2DTO(funcionEJB.getFunciones());
	}

	@GET
	@Path("/{cod: \\d+}")
	@ApiOperation(value = " Devuelve un registro de la lista de Funciones ", notes = " Una Funcion especifica ", response = Funcion.class, responseContainer = "Funcion.Class")
	public FuncionDTO getFuncion(@ApiParam(value = " Cod de la Funcion ", allowableValues = "range[1,"
			+ Integer.MAX_VALUE + "]", required = true) @PathParam("cod") int cod, @Context UriInfo uriInfo) {		
		FuncionDTO dto = new FuncionDTO(funcionEJB.getFuncionById(cod));
		ArrayList<FuncionDTO> list = (ArrayList<FuncionDTO>) listEntity2DTO(funcionEJB.getFunciones());

		String linkNext = null;
		String linkFirt;
		String linkPrevious;
		String linkSelf;
		String linkLast;

		if (dto.getCodFuncion() == 0) {
			throw new DatosNoEncontradosExcepcion(
					"No se ha encontrado la funcion con el cod: " + cod + " , probablemente fue removido del servidor");
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

	private String getLinkFirt(UriInfo uriInfo, ArrayList<FuncionDTO> list) {
		return uriInfo.getBaseUriBuilder().path(FuncionResource.class)
				.path(Integer.toString(list.get(0).getCodFuncion())).build().toString();
	}

	private String getLinkPrevious(UriInfo uriInfo, int cod) {
		FuncionDTO clase = new FuncionDTO(funcionEJB.getPreviusFuncion(cod));
		String linkPrevious = null;
		if (clase != null) {
			linkPrevious = uriInfo.getBaseUriBuilder().path(FuncionResource.class)
					.path(Integer.toString(clase.getCodFuncion())).build().toString();
		}
		return linkPrevious;
	}

	private String getlinkSelf(UriInfo uriInfo) {
		return uriInfo.getAbsolutePath().toString();
	}

	private String getLinkNext(UriInfo uriInfo, int cod) {
		FuncionDTO funcion = new FuncionDTO(funcionEJB.getNexFuncion(cod));
		String linkNext = null;
		if (funcion != null) {
			linkNext = uriInfo.getBaseUriBuilder().path(FuncionResource.class)
					.path(Integer.toString(funcion.getCodFuncion())).build().toString();
		}
		return linkNext;
	}

	private String getLinkLast(UriInfo uriInfo, ArrayList<FuncionDTO> list) {
		return uriInfo.getBaseUriBuilder().path(FuncionResource.class)
				.path(Integer.toString(list.get(list.size() - 1).getCodFuncion())).build().toString();
	}

	@POST
	@ApiOperation(value = " Crea un nuevo registro de tipo Funcion ", notes = " Crea un nuevo Funcion.CLass ", response = Funcion.class, responseContainer = "201")
	// @StatusCreated
	public Response createFuncion(FuncionDTO dto, @Context UriInfo uriInfo) {
		FuncionDTO funcionDTO=new FuncionDTO(funcionEJB.createFuncion(dto.toEntity()));
		URI uri=uriInfo.getAbsolutePathBuilder().path(String.valueOf(funcionDTO.getCodFuncion())).build();
		return Response.created(uri).entity(funcionDTO).build();
	}

	@PUT
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Actualiza un registro de la lista de Funciones ", notes = " Actualiza un registro con un {codFuncion} ", response = Funcion.class, responseContainer = "Funcion.Class")
	public FuncionDTO updateFuncion(@ApiParam(value = " Cod de la Funcion ", allowableValues = "range[1,"
			+ Integer.MAX_VALUE + "]", required = true) @PathParam("cod") int cod, FuncionDTO dto) {

		Funcion entity = dto.toEntity();
		entity.setCodFuncion(cod);

		Funcion oldEntity = funcionEJB.getFuncionById(cod);

		entity.setNombre(oldEntity.getNombre());
		entity.setPolicias(oldEntity.getPolicias());

		return new FuncionDTO(funcionEJB.updateFuncion(entity));
	}

	@DELETE
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Elimina un registro de la lista de Funciones ", notes = " Elimina un registro con un {codFuncion} ", responseContainer = "200")
	public void deleteFuncion(@ApiParam(value = " Cod de la Funcion ", allowableValues = "range[1," + Integer.MAX_VALUE
			+ "]", required = true) @PathParam("cod") int cod) {
		funcionEJB.deleteFuncion(funcionEJB.getFuncionById(cod));
	}

	public void existsFuncion(int codFuncion) {

		Funcion funcion = funcionEJB.getFuncionById(codFuncion);
		if (funcion == null) {
			throw new WebApplicationException(404);
		}
	}

	// creando el subservicio
	// http://localhost:8080/apiVentasCorp/v1/funciones/2/policias

	@Path("{codFuncion: \\d+}/policias")
	public Class<FuncionPoliciaResource> getFuncionPoliciaResource(@PathParam("codFuncion") int codFuncion) {
		existsFuncion(codFuncion);
		return FuncionPoliciaResource.class;
	}
	
}