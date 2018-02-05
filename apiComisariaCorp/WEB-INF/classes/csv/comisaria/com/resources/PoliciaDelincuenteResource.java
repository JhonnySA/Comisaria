package csv.comisaria.com.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

import csv.comisaria.com.domain.Arresto;
import csv.comisaria.com.domain.ArrestoPK;
import csv.comisaria.com.services.dto.ArrestoDTO;
import csv.comisaria.com.services.ejb.ArrestoEJBInterface;
import csv.comisaria.com.services.ejb.DelincuenteEJBInterface;
import csv.comisaria.com.services.ejb.PoliciaEJBInterface;

//@Path("arrestos")
@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class PoliciaDelincuenteResource {

	@Inject
	private ArrestoEJBInterface policiaDelincuenteEJB;
	@Inject
	private PoliciaEJBInterface policiaEJB;
	@Inject
	private DelincuenteEJBInterface delincuenteEJB;

	private List<ArrestoDTO> listEntity2DTO(List<Arresto> entityList) {
		List<ArrestoDTO> list = new ArrayList<>();
		for (Arresto entity : entityList) {
			list.add(new ArrestoDTO(entity));
		}
		return list;
	}

	@GET
	@Path("/delincuentes")
	public List<ArrestoDTO> listDetalleCasDel(@PathParam("codPolicia") int codPolicia) {
		return listEntity2DTO(policiaEJB.getArresto(codPolicia));
	}

	@Path("/policias")
	@GET
	public List<ArrestoDTO> listDetalleDelCas(@PathParam("dni") String dni) {
		return listEntity2DTO(delincuenteEJB.getArresto(dni));
	}

	private ArrestoPK getPrimaryKeyDelincuente(int codPolicia, PathSegment pathSegment) {
		ArrestoPK key = new ArrestoPK();
		javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();

		key.setCodPolicia(codPolicia);

		List<String> dni = map.get("dni");
		if (dni != null && !dni.isEmpty()) {
			key.setDni(dni.get(0));
		}
		return key;
	}

	private ArrestoPK getPrimaryKeyPolicia(String dni, PathSegment pathSegment) {
		ArrestoPK key = new ArrestoPK();
		javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();

		key.setDni(dni);

		List<String> codPolicia = map.get("codPolicia");
		if (codPolicia != null && !codPolicia.isEmpty()) {
			key.setCodPolicia(new Integer(codPolicia.get(0)));
		}
		return key;
	}

	// url ejemplo:
	// http://localhost:8085/apiComisariaCorp/delincuentes/99999999/detalles/criminal/id;codCaso=4

	@GET
	@Path("delincuentes/criminal/{id}")
	public ArrestoDTO getDetallePoliciaDelincuente(@PathParam("codPolicia") int codPolicia,
			@PathParam("id") PathSegment id) {
		ArrestoPK key = getPrimaryKeyDelincuente(codPolicia, id);
		return new ArrestoDTO(policiaDelincuenteEJB.getArrestoById(key));
	}

	// url ejemplo:
	// http://localhost:8085/apiComisariaCorp/casos/1/detalles/unico/id;dni=99999999

	@GET
	@Path("policias/oficial/{id}")
	public ArrestoDTO getDetallePoliciaDelincuente(@PathParam("dni") String dni, @PathParam("id") PathSegment id) {
		ArrestoPK key = getPrimaryKeyPolicia(dni, id);
		return new ArrestoDTO(policiaDelincuenteEJB.getArrestoById(key));
	}

	// solo get(s) de ambas tablas provenientes
	// falta post, put, delete
}
