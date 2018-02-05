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

import csv.comisaria.com.domain.Arsenal;
import csv.comisaria.com.services.dto.ArsenalDTO;
import csv.comisaria.com.services.ejb.ClaseEJBInterface;

//@Path("arsenals")
@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class ClaseArsenalResource {

	@Inject
	private ClaseEJBInterface claseEJB;
	@Context
	private HttpServletResponse response;

	private List<ArsenalDTO> ArsenalListEntity2DTO(List<Arsenal> entityList) {
		List<ArsenalDTO> list = new ArrayList<>();
		for (Arsenal entity : entityList) {
			list.add(new ArsenalDTO(entity));
		}
		return list;
	}

	private List<Arsenal> ArsenalListDTO2Entity(List<ArsenalDTO> dtos) {
		List<Arsenal> list = new ArrayList<>();
		for (ArsenalDTO dto : dtos) {
			list.add(dto.toEntity());
		}
		return list;
	}

	@GET
	public List<ArsenalDTO> listArsenal(@PathParam("codClase") int codClase) {
		return ArsenalListEntity2DTO(claseEJB.getArsenales(codClase));
	}

	@GET
	@Path("{codArsenal: \\d+}")
	public ArsenalDTO getArsenal(@PathParam("codClase") int codClase, @PathParam("codArsenal") int codArsenal) {
		return new ArsenalDTO(claseEJB.getArsenal(codClase, codArsenal));
	}

	@POST
	@Path("{codArsenal: \\d+}")
	public ArsenalDTO addArsenal(@PathParam("codClase") int codClase, @PathParam("codArsenal") int codArsenal) {
		return new ArsenalDTO(claseEJB.addArsenal(codClase, codArsenal));
	}

	@PUT
	public List<ArsenalDTO> replaceArsenals(@PathParam("codClase") int codClase, List<ArsenalDTO> arsenals) {
		return ArsenalListEntity2DTO(claseEJB.replaceArsenal(codClase, ArsenalListDTO2Entity(arsenals)));
	}

	@DELETE
	@Path("{codArsenal: \\d+}")
	public void removeVenta(@PathParam("codClase") int codClase, @PathParam("codArsenal") int codArsenal) {
		claseEJB.removeArsenal(codClase, codArsenal);
	}

}