package csv.comisaria.com.services.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import csv.comisaria.com.domain.Arsenal;
import csv.comisaria.com.domain.Clase;
import csv.comisaria.com.services.dao.ArsenalDAOLocal;
import csv.comisaria.com.services.dao.ClaseDAOLocal;

/**
 * Session Bean implementation class ClaseEJB
 */
@Stateless
public class ClaseEJB implements ClaseEJBInterface {

	@Inject
	ClaseDAOLocal claseDAO;
	@Inject
	ArsenalDAOLocal arsenalDAO;

	/**
	 * Default constructor.
	 */

	public ClaseEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int countClases() {
		// TODO Auto-generated method stub
		return claseDAO.count();
	}

	@Override
	public List<Clase> getClases() {
		// TODO Auto-generated method stub
		return claseDAO.findAll();
	}

	@Override
	public List<Clase> getClases(Integer page, Integer limit) {
		// TODO Auto-generated method stub
		return claseDAO.findAll(page, limit);	}

	// Paginacion especializada (Mi propio metodo)
	@Override
	public List<Clase> getPageClases(Integer page, Integer maxRecords, List<Clase> list) {
		if (page != null && maxRecords != null) {
			List<Clase> clases = new ArrayList<>();
			for (int i = maxRecords * (page - 1); i <= (maxRecords * page) - 1; i++) {
				clases.add(list.get(i));
			}
			return clases;
		}
		return null;
	}

	@Override
	public Clase getClaseById(Object id) {
		// TODO Auto-generated method stub
		return claseDAO.findByID(id);
	}

	@Override
	public Clase createClase(Clase entity) {
		// TODO Auto-generated method stub
		claseDAO.create(entity);
		return entity;
	}

	@Override
	public Clase updateClase(Clase entity) {
		// TODO Auto-generated method stub
		return claseDAO.update(entity);
	}

	@Override
	public void deleteClase(Clase entity) {
		// TODO Auto-generated method stub
		claseDAO.delete(entity);
	}

	// Relacion con arsenal
	@Override
	public List<Arsenal> getArsenales(int codClase) {
		// TODO Auto-generated method stub
		return getClaseById(codClase).getArsenals();
	}

	@Override
	public Arsenal getArsenal(int codClase, int codArsenal) {
		// TODO Auto-generated method stub
		List<Arsenal> list = getClaseById(codClase).getArsenals();
		Arsenal arsenal = new Arsenal();

		// arsenal.setCodArsenal(codArsenal);
		arsenal = arsenalDAO.findByID(codArsenal);

		int index = list.indexOf(arsenal);

		if (index >= 0) {
			return list.get(index);
		}
		return null;
	}

	@Override
	public Arsenal addArsenal(int codClase, int codArsenal) {
		// TODO Auto-generated method stub
		Clase clase = getClaseById(codClase);
		Arsenal arsenal = new Arsenal();

		// arsenal.setCodArsenal(codArsenal);
		arsenal = arsenalDAO.findByID(codArsenal);

		clase.getArsenals().add(arsenal);
		return getArsenal(codClase, codArsenal);
	}

	@Override
	public List<Arsenal> replaceArsenal(int codClase, List<Arsenal> list) {
		// TODO Auto-generated method stub
		Clase clase = getClaseById(codClase);
		clase.setArsenals(list);
		return clase.getArsenals();
	}

	@Override
	public void removeArsenal(int codClase, int codArsenal) {
		// TODO Auto-generated method stub
		Clase clase = getClaseById(codClase);
		Arsenal arsenal = new Arsenal();

		// arsenal.setCodArsenal(codArsenal);
		arsenal = arsenalDAO.findByID(codArsenal);

		clase.getArsenals().remove(arsenal);
	}

	// Relacion con Link
	@Override
	public Clase getNexClase(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Clase> list = claseDAO.executeListNamedQuery("Clase.nextId", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Clase getPreviusClase(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Clase> list = claseDAO.executeListNamedQuery("Clase.previousId", map);
		if (list != null && list.size() > 0) {
			return list.get(list.size() - 1);
		}
		return null;
	}

	@Override
	public Clase getLastClase() {
		List<Clase> list = claseDAO.executeListNamedQuery("Clase.lastId");
		return list.get(list.size() - 1);
	}

	@Override
	public List<Clase> getAllNex(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Clase> list = claseDAO.executeListNamedQuery("Clase.nextId", map);

		return list;
	}

}
