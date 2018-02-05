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

import csv.comisaria.com.domain.DelincuenteCaso;
import csv.comisaria.com.domain.DelincuenteCasoPK;
import csv.comisaria.com.services.dto.DelincuenteCasoDTO;
import csv.comisaria.com.services.ejb.CasoEJBInterface;
import csv.comisaria.com.services.ejb.DelincuenteCasoEJBInterface;
import csv.comisaria.com.services.ejb.DelincuenteEJBInterface;

//@Path("detalleCaso")
@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class DelincuenteCasoResource {

	@Inject
	private DelincuenteCasoEJBInterface delincuenteCasoEJB;
	@Inject
	private DelincuenteEJBInterface delincuenteEJB;
	@Inject
	private CasoEJBInterface casoEJB;

	private List<DelincuenteCasoDTO> listEntity2DTO(List<DelincuenteCaso> entityList) {
		List<DelincuenteCasoDTO> list = new ArrayList<>();
		for (DelincuenteCaso entity : entityList) {
			list.add(new DelincuenteCasoDTO(entity));
		}
		return list;
	}

	@GET
	@Path("/delincuentes")
	public List<DelincuenteCasoDTO> listDetalleCasDel(@PathParam("codCaso") int codCaso) {
		return listEntity2DTO(casoEJB.getDelincuenteCasos(codCaso));
	}

	@Path("/casos")
	@GET
	public List<DelincuenteCasoDTO> listDetalleDelCas(@PathParam("dni") String dni) {
		return listEntity2DTO(delincuenteEJB.getDelincuenteCasos(dni));
	}

	private DelincuenteCasoPK getPrimaryKeyDelincuente(int codCaso, PathSegment pathSegment) {
		DelincuenteCasoPK key = new DelincuenteCasoPK();
		javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();

		key.setCodCaso(codCaso);

		List<String> dni = map.get("dni");
		if (dni != null && !dni.isEmpty()) {
			key.setDni(dni.get(0));
		}
		return key;
	}

	private DelincuenteCasoPK getPrimaryKeyCaso(String dni, PathSegment pathSegment) {
		DelincuenteCasoPK key = new DelincuenteCasoPK();
		javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();

		key.setDni(dni);

		List<String> codCaso = map.get("codCaso");
		if (codCaso != null && !codCaso.isEmpty()) {
			key.setCodCaso(new Integer(codCaso.get(0)));
		}
		return key;
	}

	// url ejemplo:
	// http://localhost:8085/apiComisariaCorp/delincuentes/99999999/detalles/criminal/id;codCaso=4

	@GET
	@Path("delincuentes/criminal/{id}")
	public DelincuenteCasoDTO getDetalleDelincuenteCaso(@PathParam("codCaso") int codCaso,
			@PathParam("id") PathSegment id) {
		DelincuenteCasoPK key = getPrimaryKeyDelincuente(codCaso, id);
		return new DelincuenteCasoDTO(delincuenteCasoEJB.getDelincuenteCasoById(key));
	}

	// url ejemplo:
	// http://localhost:8085/apiComisariaCorp/casos/1/detalles/unico/id;dni=99999999

	@GET
	@Path("casos/unico/{id}")
	public DelincuenteCasoDTO getDetalleDelincuenteCaso(@PathParam("dni") String dni, @PathParam("id") PathSegment id) {
		DelincuenteCasoPK key = getPrimaryKeyCaso(dni, id);
		return new DelincuenteCasoDTO(delincuenteCasoEJB.getDelincuenteCasoById(key));
	}

	// solo get(s) de ambas tablas provenientes
	// falta post, put, delete

}
