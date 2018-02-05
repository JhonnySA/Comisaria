package csv.comisaria.com.services.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import csv.comisaria.com.domain.Funcion;
import csv.comisaria.com.domain.Policia;
import csv.comisaria.com.services.dao.FuncionDAOLocal;
import csv.comisaria.com.services.dao.PoliciaDAOLocal;

/**
 * Session Bean implementation class FuncionEJB
 */
@Stateless
public class FuncionEJB implements FuncionEJBInterface {

	@Inject
	FuncionDAOLocal funcionDAO;
	@Inject
	PoliciaDAOLocal policiaDAO;

	/**
	 * Default constructor.
	 */
	public FuncionEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int countFunciones() {
		// TODO Auto-generated method stub
		return funcionDAO.count();
	}

	@Override
	public List<Funcion> getFunciones() {
		// TODO Auto-generated method stub
		return funcionDAO.findAll();
	}

	@Override
	public List<Funcion> getFunciones(Integer page, Integer maxRecords) {
		// TODO Auto-generated method stub
		return funcionDAO.findAll(page, maxRecords);
	}

	@Override
	public Funcion getFuncionById(Object id) {
		// TODO Auto-generated method stub
		return funcionDAO.findByID(id);
	}

	@Override
	public Funcion createFuncion(Funcion entity) {
		// TODO Auto-generated method stub
		funcionDAO.create(entity);
		return entity;
	}

	@Override
	public Funcion updateFuncion(Funcion entity) {
		// TODO Auto-generated method stub
		return funcionDAO.update(entity);
	}

	@Override
	public void deleteFuncion(Funcion entity) {
		// TODO Auto-generated method stub
		funcionDAO.delete(entity);
	}

	// Relacion con Policia
	@Override
	public List<Policia> getPolicias(int codFuncion) {
		// TODO Auto-generated method stub
		return getFuncionById(codFuncion).getPolicias();
	}

	@Override
	public Policia getPolicia(int codFuncion, int codPolicia) {
		// TODO Auto-generated method stub
		List<Policia> list = getFuncionById(codFuncion).getPolicias();
		Policia policia = new Policia();

		// policia.setCodPolicia(codPolicia);
		policia = policiaDAO.findByID(codPolicia);

		int index = list.indexOf(policia);

		if (index >= 0) {
			return list.get(index);
		}
		return null;
	}

	@Override
	public Policia addPolicia(int codFuncion, int codPolicia) {
		// TODO Auto-generated method stub
		Funcion funcion = getFuncionById(codFuncion);
		Policia policia = new Policia();

		// policia.setCodPolicia(codPolicia);
		policia = policiaDAO.findByID(codPolicia);

		funcion.getPolicias().add(policia);
		return getPolicia(codFuncion, codPolicia);
	}

	@Override
	public List<Policia> replacePolicias(int codFuncion, List<Policia> list) {
		// TODO Auto-generated method stub
		Funcion funcion = getFuncionById(codFuncion);
		funcion.setPolicias(list);
		return funcion.getPolicias();
	}

	@Override
	public void removePolicia(int codFuncion, int codPolicia) {
		// TODO Auto-generated method stub
		Funcion funcion = getFuncionById(codFuncion);
		Policia policia = new Policia();

		// policia.setCodPolicia(codPolicia);
		policia = policiaDAO.findByID(codPolicia);

		funcion.getPolicias().remove(policia);
	}
	
	// Relacion con Link
	@Override
	public Funcion getNexFuncion(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Funcion> list = funcionDAO.executeListNamedQuery("Funcion.nextId", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Funcion getPreviusFuncion(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Funcion> list = funcionDAO.executeListNamedQuery("Funcion.previousId", map);
		if (list != null && list.size() > 0) {
			return list.get(list.size() - 1);
		}
		return null;
	}

}