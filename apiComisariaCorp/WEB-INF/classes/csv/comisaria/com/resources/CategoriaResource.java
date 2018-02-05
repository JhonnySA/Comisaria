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
import csv.comisaria.com.domain.Categoria;
import csv.comisaria.com.excepciones.DatosNoEncontradosExcepcion;
import csv.comisaria.com.services.dto.CategoriaDTO;
import csv.comisaria.com.services.ejb.CategoriaEJBInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Path("categorias")
@io.swagger.annotations.Api(value = "/categorias")
public class CategoriaResource {

	@Inject
	CategoriaEJBInterface categoriaEJB;

	@Context
	private HttpServletResponse response;
	@QueryParam("page")
	private Integer page;
	@QueryParam("limit")
	private Integer maxRecords;

	private List<CategoriaDTO> listEntity2DTO(List<Categoria> entityList) {
		List<CategoriaDTO> list = new ArrayList<>();
		for (Categoria entity : entityList) {
			list.add(new CategoriaDTO(entity));
		}
		return list;
	}

	@GET
	@ApiOperation(value = " Devuelve la lista de Categorias ", notes = " Lista completa ", response = Categoria.class, responseContainer = " List ")
	public List<CategoriaDTO> getCategorias() {

		if (page != null && maxRecords != null) {
			this.response.setIntHeader("X-Total-Count", categoriaEJB.countCategorias());
			return listEntity2DTO(categoriaEJB.getCategorias(page, maxRecords));
		}
		return listEntity2DTO(categoriaEJB.getCategorias());
	}

	@GET
	@Path("/{cod: \\d+}")
	@ApiOperation(value = " Devuelve un registro de la lista de Categorias ", notes = " Una Categoria especifico ", response = Categoria.class, responseContainer = "Categoria.Class")
	public CategoriaDTO getCategoria(@ApiParam(value = " Cod de la Categoria ", allowableValues = "range[1,"
			+ Integer.MAX_VALUE + "]", required = true) @PathParam("cod") int cod, @Context UriInfo uriInfo) {
		CategoriaDTO dto = new CategoriaDTO(categoriaEJB.getCategoriaById(cod));
		ArrayList<CategoriaDTO> list = (ArrayList<CategoriaDTO>) listEntity2DTO(categoriaEJB.getCategorias());
		
		String linkNext = null;
		String linkFirt;
		String linkPrevious;
		String linkSelf;
		String linkLast;

		if (dto.getCodCategoria() == 0) {
			throw new DatosNoEncontradosExcepcion(
					"No se ha encontrado la categoria con el cod: " + cod + " , probablemente fue removido del servidor");
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

	private String getLinkFirt(UriInfo uriInfo, ArrayList<CategoriaDTO> list) {
		return uriInfo.getBaseUriBuilder().path(CategoriaResource.class).path(Integer.toString(list.get(0).getCodCategoria()))
				.build().toString();
	}

	private String getLinkPrevious(UriInfo uriInfo, int cod) {
		CategoriaDTO caso = new CategoriaDTO(categoriaEJB.getPreviusCategoria(cod));
		String linkPrevious = null;
		if (caso != null) {
			linkPrevious = uriInfo.getBaseUriBuilder().path(CategoriaResource.class)
					.path(Integer.toString(caso.getCodCategoria())).build().toString();
		}
		return linkPrevious;
	}

	private String getlinkSelf(UriInfo uriInfo) {
		return uriInfo.getAbsolutePath().toString();
	}

	private String getLinkNext(UriInfo uriInfo, int cod) {
		CategoriaDTO categoria = new CategoriaDTO(categoriaEJB.getNexCategoria(cod));
		String linkNext = null;
		if (categoria != null) {
			linkNext = uriInfo.getBaseUriBuilder().path(CategoriaResource.class).path(Integer.toString(categoria.getCodCategoria()))
					.build().toString();
		}
		return linkNext;
	}

	private String getLinkLast(UriInfo uriInfo, ArrayList<CategoriaDTO> list) {
		return uriInfo.getBaseUriBuilder().path(CategoriaResource.class)
				.path(Integer.toString(list.get(list.size() - 1).getCodCategoria())).build().toString();
	}

	@POST
	@ApiOperation(value = " Crea un nuevo registro de tipo Categoria ", notes = " Crea un nuevo Categori.CLass ", response = Categoria.class, responseContainer = "201")
	// @StatusCreated
	public Response createCategoria(CategoriaDTO dto, @Context UriInfo uriInfo) {
		CategoriaDTO categoriaDTO = new CategoriaDTO(categoriaEJB.createCategoria(dto.toEntity()));
		;
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(categoriaDTO.getCodCategoria())).build();
		return Response.created(uri).entity(categoriaDTO).build();
	}

	@PUT
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Actualiza un registro de la lista de Categorias ", notes = " Actualiza un registro con un {codCategoria} ", response = Calabozo.class, responseContainer = "Calabozo.Class")
	public CategoriaDTO updateCategoria(@ApiParam(value = " Cod de la Categoria ", allowableValues = "range[1,"
			+ Integer.MAX_VALUE + "]", required = true) @PathParam("cod") int cod, CategoriaDTO dto) {

		Categoria entity = dto.toEntity();
		entity.setCodCategoria(cod);

		Categoria oldEntity = categoriaEJB.getCategoriaById(cod);

		entity.setNombre(oldEntity.getNombre());
		entity.setPolicias(oldEntity.getPolicias());

		return new CategoriaDTO(categoriaEJB.updateCategoria(entity));
	}

	@DELETE
	@Path("{cod: \\d+}")
	@ApiOperation(value = " Elimina un registro de la lista de Categorias ", notes = " Elimina un registro con un {codCategoria} ", responseContainer = "200")
	public void deleteCategoria(@ApiParam(value = " Cod de la Categoria ", allowableValues = "range[1,"
			+ Integer.MAX_VALUE + "]", required = true) @PathParam("cod") int cod) {
		categoriaEJB.deleteCategoria(categoriaEJB.getCategoriaById(cod));
	}

	public void existsCategoria(int codCategoria) {

		Categoria categoria = categoriaEJB.getCategoriaById(codCategoria);
		if (categoria == null) {
			throw new WebApplicationException(404);
		}
	}

	// creando el subservicio
	// http://localhost:8080/apiVentasCorp/v1/categorias/2/policias

	@Path("{codCategoria: \\d+}/policias")
	public Class<CategoriaPoliciaResource> getCategoriaPoliciaResource(@PathParam("codCategoria") int codCategoria) {
		existsCategoria(codCategoria);
		return CategoriaPoliciaResource.class;
	}

}
