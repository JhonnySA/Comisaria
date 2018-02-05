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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import csv.comisaria.com.domain.Policia;
import csv.comisaria.com.services.dto.PoliciaDTO;
import csv.comisaria.com.services.ejb.CasoEJBInterface;

//@Path("policias")
@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class CasoPoliciaResource {

	@Inject
	private CasoEJBInterface casoEJB;
	@Context
	private HttpServletResponse response;

	private List<PoliciaDTO> PoliciasListEntity2DTO(List<Policia> entityList) {
		List<PoliciaDTO> list = new ArrayList<>();
		for (Policia entity : entityList) {
			list.add(new PoliciaDTO(entity));
		}
		return list;
	}

	private List<Policia> PoliciasListDTO2Entity(List<PoliciaDTO> dtos) {
		List<Policia> list = new ArrayList<>();
		for (PoliciaDTO dto : dtos) {
			list.add(dto.toEntity());
		}
		return list;
	}

	@GET
	public List<PoliciaDTO> listPolicias(@PathParam("codCaso") int codCaso) {
		return PoliciasListEntity2DTO(casoEJB.getPolicias(codCaso));
	}

	@GET
	@Path("{codPolicia: \\d+}")
	public PoliciaDTO getPolicia(@PathParam("codCaso") int codCaso, @PathParam("codPolicia") int codPolicia) {
		return new PoliciaDTO(casoEJB.getPolicia(codCaso, codPolicia));
	}

	@POST
	@Path("{codPolicia: \\d+}")
	public PoliciaDTO addPolicia(@PathParam("codCaso") int codCaso, @PathParam("codPolicia") int codPolicia) {
		return new PoliciaDTO(casoEJB.addPolicia(codCaso, codPolicia));
	}

	@PUT
	public List<PoliciaDTO> replacePolicias(@PathParam("codCaso") int codCaso, List<PoliciaDTO> policias) {
		return PoliciasListEntity2DTO(casoEJB.replacePolicia(codCaso, PoliciasListDTO2Entity(policias)));
	}

	@DELETE
	@Path("{codPolicia: \\d+}")
	public void removeVenta(@PathParam("codCaso") int codCaso, @PathParam("codPolicia") int codPolicia) {
		casoEJB.removePolicia(codCaso, codPolicia);
	}
}
