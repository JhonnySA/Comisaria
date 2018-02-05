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
import csv.comisaria.com.services.ejb.CategoriaEJBInterface;

//@Path("policias")
@Stateless
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class CategoriaPoliciaResource {
	@Inject
	private CategoriaEJBInterface categoriaEJB;
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
	public List<PoliciaDTO> listPolicias(@PathParam("codCategoria") int codCategoria) {
		return PoliciasListEntity2DTO(categoriaEJB.gePolicias(codCategoria));
	}

	@GET
	@Path("{codPolicia: \\d+}")
	public PoliciaDTO getPolicia(@PathParam("codCategoria") int codCategoria, @PathParam("codPolicia") int codPolicia) {
		return new PoliciaDTO(categoriaEJB.getPolicia(codCategoria, codPolicia));
	}

	/*@GET
    @Path("{ventaId: \\d+}")
    public VentaDTO getVenta(@PathParam("clienteId") int clienteId, @PathParam("ventaId") int ventaId) {
        return new VentaDTO(sCliente.getVenta(clienteId, ventaId));       
    }
	 * */
	
	@POST
	@Path("{codPolicia: \\d+}")
	public PoliciaDTO addPolicia(@PathParam("codCategoria") int codCategoria, @PathParam("codPolicia") int codPolicia) {
		return new PoliciaDTO(categoriaEJB.addPolicia(codCategoria, codPolicia));
	}

	@PUT
	public List<PoliciaDTO> replacePolicias(@PathParam("codCategoria") int codCategoria, List<PoliciaDTO> policias) {
		return PoliciasListEntity2DTO(categoriaEJB.replacePolicias(codCategoria, PoliciasListDTO2Entity(policias)));
	}

	@DELETE
	@Path("{codPolicia: \\d+}")
	public void removeVenta(@PathParam("codCategoria") int codCategoria, @PathParam("codPolicia") int codPolicia) {
		categoriaEJB.removePolicia(codCategoria, codPolicia);
	}

}