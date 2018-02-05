package csv.comisaria.com.services.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import csv.comisaria.com.domain.Banda;
import csv.comisaria.com.services.dao.BandasDAOLocal;

/**
 * Session Bean implementation class BandaEJB
 */
@Stateless
public class BandaEJB implements BandaEJBInterface {

	@Inject
	BandasDAOLocal bandaDAO;

	/**
	 * Default constructor.
	 */
	public BandaEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int countBandas() {
		// TODO Auto-generated method stub
		return bandaDAO.count();
	}

	@Override
	public List<Banda> getBandas() {
		// TODO Auto-generated method stub
		return bandaDAO.findAll();
	}

	@Override
	public List<Banda> getBandas(Integer page, Integer maxRecords) {
		// TODO Auto-generated method stub
		return bandaDAO.findAll(page, maxRecords);
	}

	@Override
	public Banda getBandaById(Object id) {
		// TODO Auto-generated method stub
		return bandaDAO.findByID(id);
	}

	@Override
	public Banda createBanda(Banda entity) {
		// TODO Auto-generated method stub
		bandaDAO.create(entity);
		return entity;
	}

	@Override
	public Banda updateBanda(Banda entity) {
		// TODO Auto-generated method stub
		return bandaDAO.update(entity);
	}

	@Override
	public void deleteBanda(Banda entity) {
		// TODO Auto-generated method stub
		bandaDAO.delete(entity);
	}

	// Relacion con Link
	@Override
	public Banda getNexBanda(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Banda> list = bandaDAO.executeListNamedQuery("Banda.nextId", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Banda getPreviusBanda(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Banda> list = bandaDAO.executeListNamedQuery("Banda.previousId", map);
		if (list != null && list.size() > 0) {
			return list.get(list.size() - 1);
		}
		return null;
	}

}