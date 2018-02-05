package csv.comisaria.com.services.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import csv.comisaria.com.domain.PoliciaArsenal;
import csv.comisaria.com.services.dao.PoliciaArsenalDAOLocal;

/**
 * Session Bean implementation class PoliciaArsenalEJB
 */
@Stateless
public class PoliciaArsenalEJB implements PoliciaArsenalEJBInterface {

	@Inject
	PoliciaArsenalDAOLocal policiaArsenalDAO;

	/**
	 * Default constructor.
	 */

	public PoliciaArsenalEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int countDetallePolArs() {
		// TODO Auto-generated method stub
		return policiaArsenalDAO.count();
	}

	@Override
	public List<PoliciaArsenal> getDetallePolArs() {
		// TODO Auto-generated method stub
		return policiaArsenalDAO.findAll();
	}

	@Override
	public List<PoliciaArsenal> getDetallePolArs(Integer page, Integer maxRecords) {
		// TODO Auto-generated method stub
		return policiaArsenalDAO.findAll(page, maxRecords);
	}

	@Override
	public PoliciaArsenal getDetallePolArs(Object id) {
		// TODO Auto-generated method stub
		return policiaArsenalDAO.findByID(id);
	}

	@Override
	public PoliciaArsenal createDetallePolArs(PoliciaArsenal entity) {
		// TODO Auto-generated method stub
		policiaArsenalDAO.create(entity);
		return entity;
	}

	@Override
	public PoliciaArsenal updateDetallePolArs(PoliciaArsenal entity) {
		// TODO Auto-generated method stub
		return policiaArsenalDAO.update(entity);
	}

	@Override
	public void deleteDetallePolArs(PoliciaArsenal entity) {
		// TODO Auto-generated method stub
		policiaArsenalDAO.delete(entity);
	}

}
