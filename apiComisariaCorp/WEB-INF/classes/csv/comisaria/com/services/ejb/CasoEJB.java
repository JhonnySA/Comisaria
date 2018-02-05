package csv.comisaria.com.services.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import csv.comisaria.com.domain.Caso;
import csv.comisaria.com.domain.DelincuenteCaso;
import csv.comisaria.com.domain.DelincuenteCasoPK;
import csv.comisaria.com.domain.Policia;
import csv.comisaria.com.services.dao.CasoDAOLocal;
import csv.comisaria.com.services.dao.PoliciaDAOLocal;

/**
 * Session Bean implementation class CasoEJB
 */
@Stateless
public class CasoEJB implements CasoEJBInterface {

	@Inject
	CasoDAOLocal casoDAO;
	@Inject
	PoliciaDAOLocal policiaDAO;

	/**
	 * Default constructor.
	 */
	public CasoEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int countCasos() {
		// TODO Auto-generated method stub
		return casoDAO.count();
	}

	@Override
	public List<Caso> getCasos() {
		// TODO Auto-generated method stub
		return casoDAO.findAll();
	}

	@Override
	public List<Caso> getCasos(Integer page, Integer maxRecords) {
		// TODO Auto-generated method stub
		return casoDAO.findAll(page, maxRecords);
	}

	@Override
	public Caso getCasoById(Object id) {
		// TODO Auto-generated method stub
		return casoDAO.findByID(id);
	}

	@Override
	public Caso createCaso(Caso entity) {
		// TODO Auto-generated method stub
		casoDAO.create(entity);
		return entity;
	}

	@Override
	public Caso updateCaso(Caso entity) {
		// TODO Auto-generated method stub
		return casoDAO.update(entity);
	}

	@Override
	public void deleteCaso(Caso entity) {
		// TODO Auto-generated method stub
		casoDAO.delete(entity);
	}

	// Relacion con Policia
	@Override
	public List<Policia> getPolicias(int codCaso) {
		// TODO Auto-generated method stub
		return getCasoById(codCaso).getPolicias();
	}

	@Override
	public Policia getPolicia(int codCaso, int codPolicia) {
		// TODO Auto-generated method stub
		List<Policia> list = getCasoById(codCaso).getPolicias();
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
	public Policia addPolicia(int codCaso, int codPolicia) {
		// TODO Auto-generated method stub
		Caso caso = getCasoById(codCaso);
		Policia policia = new Policia();

		// policia.setCodPolicia(codPolicia);
		policia = policiaDAO.findByID(codPolicia);

		caso.getPolicias().add(policia);
		return getPolicia(codCaso, codPolicia);
	}

	@Override
	public List<Policia> replacePolicia(int codCaso, List<Policia> list) {
		// TODO Auto-generated method stub
		Caso caso = getCasoById(codCaso);
		caso.setPolicias(list);
		return caso.getPolicias();
	}

	@Override
	public void removePolicia(int codCaso, int codPolicia) {
		// TODO Auto-generated method stub
		Caso caso = getCasoById(codPolicia);
		Policia policia = new Policia();

		// policia.setCodPolicia(codPolicia);
		policia = policiaDAO.findByID(codPolicia);

		caso.getPolicias().remove(policia);
	}

	// Relacion con DelincuenteCaso
	@Override
	public List<DelincuenteCaso> getDelincuenteCasos(int codCaso) {
		// TODO Auto-generated method stub
		return getCasoById(codCaso).getDelincuentecasos();
	}

	@Override
	public DelincuenteCaso getDelincuenteCaso(int codCaso, String dni) {
		// TODO Auto-generated method stub
		List<DelincuenteCaso> list = getCasoById(codCaso).getDelincuentecasos();
		DelincuenteCaso delincuenteCaso = new DelincuenteCaso();
		DelincuenteCasoPK pk = new DelincuenteCasoPK();
		pk.setCodCaso(codCaso);
		pk.setDni(dni);

		delincuenteCaso.setId(pk);

		int index = list.indexOf(delincuenteCaso);
		if (index >= 0) {
			return list.get(index);
		}
		return null;
	}

	@Override
	public DelincuenteCaso addDelincuenteCaso(int codCaso, String dni) {
		// TODO Auto-generated method stub
		Caso caso = getCasoById(codCaso);
		DelincuenteCaso delincuenteCaso = new DelincuenteCaso();

		DelincuenteCasoPK pk = new DelincuenteCasoPK();
		pk.setCodCaso(codCaso);
		pk.setDni(dni);
		delincuenteCaso.setId(pk);

		caso.getDelincuentecasos().add(delincuenteCaso);
		return getDelincuenteCaso(codCaso, dni);
	}

	@Override
	public List<DelincuenteCaso> replaceDelincuenteCaso(int codCaso, List<DelincuenteCaso> list) {
		// TODO Auto-generated method stub
		Caso caso = getCasoById(codCaso);
		caso.setDelincuentecasos(list);
		return caso.getDelincuentecasos();
	}

	@Override
	public void removeDelincuenteCaso(int codCaso, String dni) {
		// TODO Auto-generated method stub
		Caso caso = getCasoById(codCaso);
		DelincuenteCaso delincuenteCaso = new DelincuenteCaso();
		DelincuenteCasoPK pk = new DelincuenteCasoPK();
		pk.setCodCaso(codCaso);
		pk.setDni(dni);
		delincuenteCaso.setId(pk);
		caso.getDelincuentecasos().remove(delincuenteCaso);
	}

	// Relacion con Link
	@Override
	public Caso getNexCaso(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Caso> list = casoDAO.executeListNamedQuery("Caso.nextId", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Caso getPreviusCaso(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Caso> list = casoDAO.executeListNamedQuery("Caso.previousId", map);
		if (list != null && list.size() > 0) {
			return list.get(list.size() - 1);
		}
		return null;
	}

}