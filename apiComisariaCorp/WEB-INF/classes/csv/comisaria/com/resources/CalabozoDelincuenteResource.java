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

import csv.comisaria.com.domain.Delincuente;
import csv.comisaria.com.services.dto.DelincuenteDTO;
import csv.comisaria.com.services.ejb.CalabozoEJBInterface;

//@Path("delincuentes")
@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class CalabozoDelincuenteResource {

	@Inject
	private CalabozoEJBInterface calabozoEJB;
	@Context
	private HttpServletResponse response;

	private List<DelincuenteDTO> DelincuenteListEntity2DTO(List<Delincuente> entityList) {
		List<DelincuenteDTO> list = new ArrayList<>();
		for (Delincuente entity : entityList) {
			list.add(new DelincuenteDTO(entity));
		}
		return list;
	}

	private List<Delincuente> DelincuenteListDTO2Entity(List<DelincuenteDTO> dtos) {
		List<Delincuente> list = new ArrayList<>();
		for (DelincuenteDTO dto : dtos) {
			list.add(dto.toEntity());
		}
		return list;
	}

	@GET
	public List<DelincuenteDTO> listDelincuentes(@PathParam("codCalabozo") int codCalabozo) {
		return DelincuenteListEntity2DTO(calabozoEJB.getDelincuentes(codCalabozo));
	}

	@GET
	@Path("{dni: \\d+}")
	public DelincuenteDTO getDelincuente(@PathParam("codCalabozo") int codCalabozo, @PathParam("dni") String dni) {
		return new DelincuenteDTO(calabozoEJB.getDelincuente(codCalabozo, dni));
	}

	@POST
	@Path("{dni: \\d+}")
	public DelincuenteDTO addDelincuente(@PathParam("codCalabozo") int codCalabozo, @PathParam("dni") String dni) {
		return new DelincuenteDTO(calabozoEJB.addDelincuente(codCalabozo, dni));
	}

	@PUT
	public List<DelincuenteDTO> replaceDelincuentes(@PathParam("codCalabozo") int codCalabozo,
			List<DelincuenteDTO> delincuentes) {
		return DelincuenteListEntity2DTO(
				calabozoEJB.replaceDelincuentes(codCalabozo, DelincuenteListDTO2Entity(delincuentes)));
	}

	@DELETE
	@Path("{dni: \\d+}")
	public void removeDelincuente(@PathParam("codCalabozo") int codCalabozo, @PathParam("dni") String dni) {
		calabozoEJB.removeDelincuente(codCalabozo, dni);
	}
	
}