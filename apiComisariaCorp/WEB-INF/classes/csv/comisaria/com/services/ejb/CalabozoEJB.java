package csv.comisaria.com.services.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import csv.comisaria.com.domain.Calabozo;
import csv.comisaria.com.domain.Delincuente;
import csv.comisaria.com.services.dao.CalabozoDAOLocal;
import csv.comisaria.com.services.dao.DelincuenteDAOLocal;

/**
 * Session Bean implementation class CalabozoEJB
 */
@Stateless
public class CalabozoEJB implements CalabozoEJBInterface {

	@Inject
	CalabozoDAOLocal calabozoDAO;
	@Inject
	DelincuenteDAOLocal delincuenteDAO;

	/**
	 * Default constructor.
	 */

	public CalabozoEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int countCalabozos() {
		// TODO Auto-generated method stub
		return calabozoDAO.count();
	}

	@Override
	public List<Calabozo> getCalabozos() {
		// TODO Auto-generated method stub
		return calabozoDAO.findAll();
	}

	@Override
	public List<Calabozo> getCalabozos(Integer page, Integer maxRecords) {
		// TODO Auto-generated method stub
		return calabozoDAO.findAll(page, maxRecords);
	}

	@Override
	public Calabozo getCalabozoById(Object id) {
		// TODO Auto-generated method stub
		return calabozoDAO.findByID(id);
	}

	@Override
	public Calabozo createCalabozo(Calabozo entity) {
		// TODO Auto-generated method stub
		calabozoDAO.create(entity);
		return entity;
	}

	@Override
	public Calabozo updateCalabozo(Calabozo entity) {
		// TODO Auto-generated method stub
		return calabozoDAO.update(entity);
	}

	@Override
	public void deleteCalabozo(Calabozo entity) {
		// TODO Auto-generated method stub
		calabozoDAO.delete(entity);
	}

	@Override
	public List<Delincuente> getDelincuentes(int codCalabozo) {
		// TODO Auto-generated method stub
		return getCalabozoById(codCalabozo).getDelincuentes();
	}

	@Override
	public Delincuente getDelincuente(int codCalabozo, String dni) {
		// TODO Auto-generated method stub
		List<Delincuente> list = getCalabozoById(codCalabozo).getDelincuentes();
		Delincuente delincuente = new Delincuente();

		// delincuente.setDni(dni);
		delincuente = delincuenteDAO.findByID(dni);

		int index = list.indexOf(delincuente);
		if (index >= 0) {
			return list.get(index);
		}
		return null;
	}

	@Override
	public Delincuente addDelincuente(int codCalabozo, String dni) {
		// TODO Auto-generated method stub
		Calabozo calabozo = getCalabozoById(codCalabozo);
		Delincuente delincuente = new Delincuente();

		// delincuente.setDni(dni);
		delincuente = delincuenteDAO.findByID(dni);

		calabozo.getDelincuentes().add(delincuente);
		return getDelincuente(codCalabozo, dni);
	}

	@Override
	public List<Delincuente> replaceDelincuentes(int codCalabozo, List<Delincuente> list) {
		// TODO Auto-generated method stub
		Calabozo calabozo = getCalabozoById(codCalabozo);
		calabozo.setDelincuentes(list);
		return calabozo.getDelincuentes();
	}

	@Override
	public void removeDelincuente(int codCalabozo, String dni) {
		// TODO Auto-generated method stub
		Calabozo calabozo = getCalabozoById(codCalabozo);
		Delincuente delincuente = new Delincuente();

		// delincuente.setDni(dni);
		delincuente = delincuenteDAO.findByID(dni);

		calabozo.getDelincuentes().remove(delincuente);
	}
	
	// Relacion con Link
	@Override
	public Calabozo getNexCalabozo(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Calabozo> list = calabozoDAO.executeListNamedQuery("Calabozo.nextId", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Calabozo getPreviusCalabozo(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Calabozo> list = calabozoDAO.executeListNamedQuery("Calabozo.previousId", map);
		if (list != null && list.size() > 0) {
			return list.get(list.size() - 1);
		}
		return null;
	}

}