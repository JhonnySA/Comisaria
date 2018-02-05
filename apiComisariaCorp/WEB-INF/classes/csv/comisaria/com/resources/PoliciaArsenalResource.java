package csv.comisaria.com.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

import csv.comisaria.com.domain.PoliciaArsenal;
import csv.comisaria.com.domain.PoliciaArsenalPK;
import csv.comisaria.com.services.dto.PoliciaArsenalDTO;
import csv.comisaria.com.services.ejb.ArsenalEJBInterface;
import csv.comisaria.com.services.ejb.PoliciaArsenalEJBInterface;
import csv.comisaria.com.services.ejb.PoliciaEJBInterface;

//@Path("prestamos")
@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class PoliciaArsenalResource {

	@Inject
	private PoliciaArsenalEJBInterface policiaArsenalEJB;
	@Inject
	private PoliciaEJBInterface policiaEJB;
	@Inject
	private ArsenalEJBInterface arsenalEJB;

	/*
	 * @Context private HttpServletResponse response;
	 * 
	 * @QueryParam("page") private Integer page;
	 * 
	 * @QueryParam("limit") private Integer maxRecords;
	 */

	private List<PoliciaArsenalDTO> listEntity2DTO(List<PoliciaArsenal> entityList) {
		List<PoliciaArsenalDTO> list = new ArrayList<>();
		for (PoliciaArsenal entity : entityList) {
			list.add(new PoliciaArsenalDTO(entity));
		}
		return list;
	}

	@GET
	@Path("/arsenals")
	public List<PoliciaArsenalDTO> listDetallePolArs(@PathParam("codPolicia") int codPolicia) {
		return listEntity2DTO(policiaEJB.getPoliciaArsenals(codPolicia));
	}

	@Path("/policias")
	@GET
	public List<PoliciaArsenalDTO> listDetalleArsPol(@PathParam("codArsenal") int codArsenal) {
		return listEntity2DTO(arsenalEJB.getPoliciaArsenals(codArsenal));
	}

	private PoliciaArsenalPK getPrimaryKeyArsenal(int codPolicia, PathSegment pathSegment) {
		PoliciaArsenalPK key = new PoliciaArsenalPK();
		javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();

		key.setCodPolicia(codPolicia);

		List<String> codArsenal = map.get("codArsenal");
		if (codArsenal != null && !codArsenal.isEmpty()) {
			key.setCodArsenal(new Integer(codArsenal.get(0)));
		}
		return key;
	}

	private PoliciaArsenalPK getPrimaryKeyPolicia(int codArsenal, PathSegment pathSegment) {
		PoliciaArsenalPK key = new PoliciaArsenalPK();
		javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();

		key.setCodArsenal(codArsenal);

		List<String> codPolicia = map.get("codPolicia");
		if (codPolicia != null && !codPolicia.isEmpty()) {
			key.setCodPolicia(new Integer(codPolicia.get(0)));
		}
		return key;
	}

	// url ejemplo:
	// http://localhost:8085/apiComisariaCorp/policias/1/prestamos/arsenals/id;codArsenal=4

	@GET
	@Path("arsenals/arma/{id}")
	public PoliciaArsenalDTO getDetallePrestamoArsenal(@PathParam("codPolicia") int codPolicia,
			@PathParam("id") PathSegment id) {
		PoliciaArsenalPK key = getPrimaryKeyArsenal(codPolicia, id);
		return new PoliciaArsenalDTO(policiaArsenalEJB.getDetallePolArs(key));
	}

	@GET
	@Path("policias/oficial/{id}")
	public PoliciaArsenalDTO getDetallePrestamoPolicia(@PathParam("codArsenal") int codArsenal,
			@PathParam("id") PathSegment id) {
		PoliciaArsenalPK key = getPrimaryKeyPolicia(codArsenal, id);
		return new PoliciaArsenalDTO(policiaArsenalEJB.getDetallePolArs(key));
	}

	@POST
	@Path("arsenals/arma/{codPolicia: \\d+}")
	public PoliciaArsenalDTO addDetalleArsPol(@PathParam("codArsenal") int codArsenal,
			@PathParam("codPolicia") int codPolicia) {
		return new PoliciaArsenalDTO(arsenalEJB.addPoliciaArsenal(codArsenal, codPolicia));
	}

	@POST
	@Path("policias/oficial/{codArsenal: \\d+}")
	public PoliciaArsenalDTO addDetallePolArs(@PathParam("codPolicia") int codPolicia,
			@PathParam("codArsenal") int codArsenal) {
		return new PoliciaArsenalDTO(policiaEJB.addPoliciaArsenal(codPolicia, codArsenal));
	}

	@PUT
	@Path("arsenals/arma/{id}")
	public PoliciaArsenalDTO updateDetallePrestamoArsenal(@PathParam("codPolicia") int codPolicia,
			@PathParam("id") PathSegment id, PoliciaArsenalDTO dto) {

		PoliciaArsenal entity = dto.toEntity();
		PoliciaArsenalPK key = getPrimaryKeyArsenal(codPolicia, id);
		entity.setId(key);
		PoliciaArsenal oldEntity = policiaArsenalEJB.getDetallePolArs(key);

		entity.setArsenal(oldEntity.getArsenal());
		return new PoliciaArsenalDTO(policiaArsenalEJB.updateDetallePolArs(entity));
	}

	// corregir quizar los post de oficial y arma
	// falta el put de policias/oficial/{id}
	// falta corregir el delete desde los policias y arsenal accediendo alm
	// subrecurso de prestamos
}