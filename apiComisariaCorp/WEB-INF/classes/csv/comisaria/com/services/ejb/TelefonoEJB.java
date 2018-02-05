package csv.comisaria.com.services.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import csv.comisaria.com.domain.Telefono;
import csv.comisaria.com.services.dao.TelefonoDAOLocal;

/**
 * Session Bean implementation class TelefonoEJB
 */
@Stateless
public class TelefonoEJB implements TelefonoEJBInteface {

	@Inject
	TelefonoDAOLocal telefonoDAO;

	/**
	 * Default constructor.
	 */

	public TelefonoEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int countsTelefonos() {
		// TODO Auto-generated method stub
		return telefonoDAO.count();
	}

	@Override
	public List<Telefono> getTelefonos() {
		// TODO Auto-generated method stub
		return telefonoDAO.findAll();
	}

	@Override
	public List<Telefono> getTelefonos(Integer page, Integer maxRecords) {
		// TODO Auto-generated method stub
		return telefonoDAO.findAll(page, maxRecords);
	}

	@Override
	public Telefono getTelefonoById(Object id) {
		// TODO Auto-generated method stub
		return telefonoDAO.findByID(id);
	}

	@Override
	public Telefono createTelefono(Telefono entity) {
		// TODO Auto-generated method stub
		telefonoDAO.create(entity);
		return entity;
	}

	@Override
	public Telefono updateTelefono(Telefono entity) {
		// TODO Auto-generated method stub
		return telefonoDAO.update(entity);
	}

	@Override
	public void deleteTelefono(Telefono entity) {
		// TODO Auto-generated method stub
		telefonoDAO.delete(entity);
	}

	// Relacion con Link
	@Override
	public Telefono getNexTelefono(String num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", num);
		List<Telefono> list = telefonoDAO.executeListNamedQuery("Telefono.nextId", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Telefono getPreviusTelefono(String num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", num);
		List<Telefono> list = telefonoDAO.executeListNamedQuery("Telefono.previousId", map);
		if (list != null && list.size() > 0) {
			return list.get(list.size() - 1);
		}
		return null;
	}

}
