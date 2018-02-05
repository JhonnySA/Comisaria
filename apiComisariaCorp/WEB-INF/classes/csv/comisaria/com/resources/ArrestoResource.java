package csv.comisaria.com.resources;

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
import javax.ws.rs.core.PathSegment;

import csv.comisaria.com.domain.Arresto;
import csv.comisaria.com.domain.ArrestoPK;
import csv.comisaria.com.services.dto.ArrestoDTO;
import csv.comisaria.com.services.ejb.ArrestoEJBInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Path("arrestos")
@io.swagger.annotations.Api(value = "/arrestos")
public class ArrestoResource {

	@Inject
	ArrestoEJBInterface arrestoEJB;

	@Context
	private HttpServletResponse response;
	@QueryParam("page")
	private Integer page;
	@QueryParam("limit")
	private Integer maxRecords;

	private List<ArrestoDTO> listEntity2DTO(List<Arresto> entityList) {
		List<ArrestoDTO> list = new ArrayList<>();
		for (Arresto entity : entityList) {
			list.add(new ArrestoDTO(entity));
		}
		return list;
	}

	@GET
	@ApiOperation(value = " Devuelve la lista de Arrestos ", notes = " Lista completa ", response = Arresto.class, responseContainer = " List ")
	public List<ArrestoDTO> getArrestos() {
		if (page != null && maxRecords != null) {
			this.response.setIntHeader("X-Total-Count", arrestoEJB.countArrestos());
			return listEntity2DTO(arrestoEJB.getArrestos(page, maxRecords));
		}
		return listEntity2DTO(arrestoEJB.getArrestos());
	}

	private ArrestoPK getPrimaryKey(PathSegment pathSegment) {
		ArrestoPK key = new ArrestoPK();
		javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
		List<String> idPolicia = map.get("codPolicia");
		if (idPolicia != null && !idPolicia.isEmpty()) {
			key.setCodPolicia(new Integer(idPolicia.get(0)));
		}
		List<String> dni = map.get("dni");
		if (dni != null && !dni.isEmpty()) {
			key.setDni(new String(dni.get(0)));
		}
		return key;
	}

	@GET
	@Path("/{cod}")
	@ApiOperation(value = " Devuelve un registro de la lista de Arrestos ", notes = " Un Arresto especifico dado: /id;codPolicia=1;dni=99999999", response = Arresto.class, responseContainer = "Arresto.Class")
	public ArrestoDTO getArresto(
			@ApiParam(value = " MatrixQuery, almacena codPolicia y dni ", required = true) @PathParam("cod") PathSegment cod) {
		ArrestoPK key = getPrimaryKey(cod);
		return new ArrestoDTO(arrestoEJB.getArrestoById(key));
	}

	@POST
	@ApiOperation(value = " Crea un nuevo registro de tipo Arresto ", notes = " Crea un nuevo Arresto.CLass ", response = Arresto.class, responseContainer = "201")
	// @StatusCreated
	public ArrestoDTO createArresto(ArrestoDTO dto) {
		return new ArrestoDTO(arrestoEJB.createArresto(dto.toEntity()));
	}

	@PUT
	@Path("/{cod}")
	@ApiOperation(value = " Actualiza un registro de la lista de Arrestos ", notes = " Actualiza un Arresto especifico dado: /id;codPolicia=1;dni=99999999 con un {cod} ", response = Arresto.class, responseContainer = "Arresto.Class")
	public ArrestoDTO updateArresto(
			@ApiParam(value = " MatrixQuery, almacena codPolicia y dni ", required = true) @PathParam("cod") PathSegment cod,
			ArrestoDTO dto) {

		Arresto entity = dto.toEntity();
		ArrestoPK key = getPrimaryKey(cod);
		entity.setId(key);

		Arresto oldEntity = arrestoEJB.getArrestoById(key);
		entity.setDelincuente(oldEntity.getDelincuente());
		entity.setFecha(oldEntity.getFecha());
		entity.setPolicia(oldEntity.getPolicia());

		return new ArrestoDTO(arrestoEJB.updateArresto(oldEntity));
	}

	@DELETE
	@Path("/{cod}")
	@ApiOperation(value = " Elimina un registro de la lista de Arrestos ", notes = " Elimina un Arresto especifico dado: /id;codPolicia=1;dni=99999999 con un {cod} ", responseContainer = "200")
	public void deleteArresto(
			@ApiParam(value = " MatrixQuery, almacena codPolicia y dni ", required = true) @PathParam("cod") PathSegment cod) {
		ArrestoPK key = getPrimaryKey(cod);
		arrestoEJB.deleteArresto(arrestoEJB.getArrestoById(key));
	}

}