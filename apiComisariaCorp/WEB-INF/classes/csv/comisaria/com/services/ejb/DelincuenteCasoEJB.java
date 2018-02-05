package csv.comisaria.com.services.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import csv.comisaria.com.domain.DelincuenteCaso;
import csv.comisaria.com.services.dao.DelincuenteCasoDAOLocal;

/**
 * Session Bean implementation class DelincuenteCasoEJB
 */
@Stateless
public class DelincuenteCasoEJB implements DelincuenteCasoEJBInterface {

	@Inject
	DelincuenteCasoDAOLocal delincuenteCasoDAO;

	/**
	 * Default constructor.
	 */

	public DelincuenteCasoEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int countDelincuenteCasos() {
		// TODO Auto-generated method stub
		return delincuenteCasoDAO.count();
	}

	@Override
	public List<DelincuenteCaso> getDelincuenteCasos() {
		// TODO Auto-generated method stub
		return delincuenteCasoDAO.findAll();
	}

	@Override
	public List<DelincuenteCaso> getDelincuenteCasos(Integer page, Integer maxRecords) {
		// TODO Auto-generated method stub
		return delincuenteCasoDAO.findAll(page, maxRecords);
	}

	@Override
	public DelincuenteCaso getDelincuenteCasoById(Object id) {
		// TODO Auto-generated method stub
		return delincuenteCasoDAO.findByID(id);
	}

	@Override
	public DelincuenteCaso createDelincuenteCaso(DelincuenteCaso entity) {
		// TODO Auto-generated method stub
		delincuenteCasoDAO.create(entity);
		return entity;
	}

	@Override
	public DelincuenteCaso updateDelincuenteCaso(DelincuenteCaso entity) {
		// TODO Auto-generated method stub
		return delincuenteCasoDAO.update(entity);
	}

	@Override
	public void deleteDelincuenteCaso(DelincuenteCaso entity) {
		// TODO Auto-generated method stub
		delincuenteCasoDAO.delete(entity);
	}

}
