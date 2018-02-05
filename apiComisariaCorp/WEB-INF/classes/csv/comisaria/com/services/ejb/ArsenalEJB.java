package csv.comisaria.com.services.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import csv.comisaria.com.domain.Arsenal;
import csv.comisaria.com.domain.PoliciaArsenal;
import csv.comisaria.com.domain.PoliciaArsenalPK;
import csv.comisaria.com.services.dao.ArsenalDAOLocal;

/**
 * Session Bean implementation class ArsenalEJB
 */
@Stateless
public class ArsenalEJB implements ArsenalEJBInterface {

	@Inject
	ArsenalDAOLocal arsenalDAO;

	/**
	 * Default constructor.
	 */

	public ArsenalEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int countArsenales() {
		// TODO Auto-generated method stub
		return arsenalDAO.count();
	}

	@Override
	public List<Arsenal> getArsenales() {
		// TODO Auto-generated method stub
		return arsenalDAO.findAll();
	}

	@Override
	public List<Arsenal> getArsenales(Integer page, Integer maxRecords) {
		// TODO Auto-generated method stub
		return arsenalDAO.findAll(page, maxRecords);
	}

	@Override
	public Arsenal getArsenalById(Object id) {
		// TODO Auto-generated method stub
		return arsenalDAO.findByID(id);
	}

	@Override
	public Arsenal createArsenal(Arsenal entity) {
		// TODO Auto-generated method stub
		arsenalDAO.create(entity);
		return entity;
	}

	@Override
	public Arsenal updateArsenal(Arsenal entity) {
		// TODO Auto-generated method stub
		return arsenalDAO.update(entity);
	}

	@Override
	public void deleteArsenal(Arsenal entity) {
		// TODO Auto-generated method stub
		arsenalDAO.delete(entity);
	}

	// Relacion con PoliciaArsenal
	@Override
	public List<PoliciaArsenal> getPoliciaArsenals(int codArsenal) {
		// TODO Auto-generated method stub
		return getArsenalById(codArsenal).getPoliciaarsenals();
	}

	@Override
	public PoliciaArsenal getPoliciaArsenals(int codArsenal, int codPolicia) {
		// TODO Auto-generated method stub
		List<PoliciaArsenal> list = getArsenalById(codArsenal).getPoliciaarsenals();
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
	public PoliciaArsenal addPoliciaArsenal(int codArsenal, int codPolicia) {
		// TODO Auto-generated method stub
		Arsenal arsenal = getArsenalById(codArsenal);
		PoliciaArsenal policiaArsenal = new PoliciaArsenal();

		PoliciaArsenalPK pk = new PoliciaArsenalPK();
		pk.setCodPolicia(codPolicia);
		pk.setCodArsenal(codArsenal);

		policiaArsenal.setId(pk);

		arsenal.getPoliciaarsenals().add(policiaArsenal);
		return getPoliciaArsenals(codPolicia, codArsenal);
	}

	@Override
	public List<PoliciaArsenal> replacePoliciaArsenals(int codArsenal, List<PoliciaArsenal> list) {
		// TODO Auto-generated method stub
		Arsenal arsenal = getArsenalById(codArsenal);
		arsenal.setPoliciaarsenals(list);
		return arsenal.getPoliciaarsenals();
	}

	@Override
	public void removePoliciaArsenal(int codArsenal, int codPolicia) {
		// TODO Auto-generated method stub
		Arsenal arsenal = getArsenalById(codArsenal);
		PoliciaArsenal policiaArsenal = new PoliciaArsenal();

		PoliciaArsenalPK pk = new PoliciaArsenalPK();
		pk.setCodPolicia(codPolicia);
		pk.setCodArsenal(codArsenal);

		policiaArsenal.setId(pk);

		arsenal.getPoliciaarsenals().remove(policiaArsenal);
	}

	// Relacion con Link
	@Override
	public Arsenal getNexArsenal(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Arsenal> list = arsenalDAO.executeListNamedQuery("Arsenal.nextId", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Arsenal getPreviusArsenal(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Arsenal> list = arsenalDAO.executeListNamedQuery("Arsenal.previousId", map);
		if (list != null && list.size() > 0) {
			return list.get(list.size() - 1);
		}
		return null;
	}

}
