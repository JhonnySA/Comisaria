package csv.comisaria.com.services.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import csv.comisaria.com.domain.Arresto;
import csv.comisaria.com.domain.Policia;
import csv.comisaria.com.domain.PoliciaArsenal;
import csv.comisaria.com.domain.PoliciaArsenalPK;
import csv.comisaria.com.services.dao.PoliciaDAOLocal;

/**
 * Session Bean implementation class PoliciaEJB
 */
@Stateless
public class PoliciaEJB implements PoliciaEJBInterface {

	@Inject
	PoliciaDAOLocal policiaDAO;

	/**
	 * Default constructor.
	 */

	public PoliciaEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int countPolicias() {
		// TODO Auto-generated method stub
		return policiaDAO.count();
	}

	@Override
	public List<Policia> getPolicias() {
		// TODO Auto-generated method stub
		return policiaDAO.findAll();
	}

	@Override
	public List<Policia> getPolicias(Integer page, Integer maxRecords) {
		// TODO Auto-generated method stub
		return policiaDAO.findAll(page, maxRecords);
	}

	@Override
	public Policia getPoliciaById(Object id) {
		// TODO Auto-generated method stub
		return policiaDAO.findByID(id);
	}

	@Override
	public Policia createPolicia(Policia entity) {
		// TODO Auto-generated method stub
		policiaDAO.create(entity);
		return entity;
	}

	@Override
	public Policia updatePolicia(Policia entity) {
		// TODO Auto-generated method stub
		return policiaDAO.update(entity);
	}

	@Override
	public void deletePolicia(Policia entity) {
		// TODO Auto-generated method stub
		policiaDAO.delete(entity);
	}

	// Relacion con PoliciaArsenal
	@Override
	public List<PoliciaArsenal> getPoliciaArsenals(int codPolicia) {
		// TODO Auto-generated method stub
		return getPoliciaById(codPolicia).getPoliciaarsenals();
	}

	@Override
	public PoliciaArsenal getPoliciaArsenals(int codPolicia, int codArsenal) {
		// TODO Auto-generated method stub
		List<PoliciaArsenal> list = getPoliciaById(codPolicia).getPoliciaarsenals();
		PoliciaArsenal policiaArsenal = new PoliciaArsenal();

		PoliciaArsenalPK pk = new PoliciaArsenalPK();
		pk.setCodPolicia(codPolicia);
		pk.setCodArsenal(codArsenal);

		policiaArsenal.setId(pk);

		int index = list.indexOf(policiaArsenal);
		if (index >= 0) {
			return list.get(index);
		}
		return null;
	}

	@Override
	public PoliciaArsenal addPoliciaArsenal(int codPolicia, int codArsenal) {
		// TODO Auto-generated method stub
		Policia policia = getPoliciaById(codPolicia);
		PoliciaArsenal policiaArsenal = new PoliciaArsenal();

		PoliciaArsenalPK pk = new PoliciaArsenalPK();
		pk.setCodPolicia(codPolicia);
		pk.setCodArsenal(codArsenal);

		policiaArsenal.setId(pk);

		policia.getPoliciaarsenals().add(policiaArsenal);
		return getPoliciaArsenals(codPolicia, codArsenal);
	}

	@Override
	public List<PoliciaArsenal> replacePoliciaArsenals(int codPolicia, List<PoliciaArsenal> list) {
		// TODO Auto-generated method stub
		Policia policia = getPoliciaById(codPolicia);
		policia.setPoliciaarsenals(list);
		return policia.getPoliciaarsenals();
	}

	@Override
	public void removePoliciaArsenal(int codPolicia, int codArsenal) {
		// TODO Auto-generated method stub
		Policia policia = getPoliciaById(codPolicia);
		PoliciaArsenal policiaArsenal = new PoliciaArsenal();

		PoliciaArsenalPK pk = new PoliciaArsenalPK();
		pk.setCodPolicia(codPolicia);
		pk.setCodArsenal(codArsenal);

		policiaArsenal.setId(pk);

		policia.getPoliciaarsenals().remove(policiaArsenal);
	}

	// Relacion con arresto
	@Override
	public List<Arresto> getArresto(int codPolicia) {
		// TODO Auto-generated method stub
		return getPoliciaById(codPolicia).getArrestos();
	}

	// Relacion con Link
	@Override
	public Policia getNexPolicia(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Policia> list = policiaDAO.executeListNamedQuery("Policia.nextId", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Policia getPreviusPolicia(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Policia> list = policiaDAO.executeListNamedQuery("Policia.previousId", map);
		if (list != null && list.size() > 0) {
			return list.get(list.size() - 1);
		}
		return null;
	}

}
