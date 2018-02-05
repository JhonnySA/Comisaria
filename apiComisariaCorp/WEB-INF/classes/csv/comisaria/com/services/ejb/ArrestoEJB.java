package csv.comisaria.com.services.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import csv.comisaria.com.domain.Arresto;
import csv.comisaria.com.services.dao.ArrestoDAOLocal;

/**
 * Session Bean implementation class ArrestoEJB
 */
@Stateless
public class ArrestoEJB implements ArrestoEJBInterface {

	@Inject
	ArrestoDAOLocal arrestoDAO;
    /**
     * Default constructor. 
     */
	
    public ArrestoEJB() {
        // TODO Auto-generated constructor stub
    }
    
    
	@Override
	public int countArrestos() {
		// TODO Auto-generated method stub
		return arrestoDAO.count();
	}
	
	@Override
	public List<Arresto> getArrestos() {
		// TODO Auto-generated method stub
		return arrestoDAO.findAll();
	}
	
	@Override
	public List<Arresto> getArrestos(Integer page, Integer maxRecords) {
		// TODO Auto-generated method stub
		return arrestoDAO.findAll(page, maxRecords);
	}
	
	@Override
	public Arresto getArrestoById(Object id) {
		// TODO Auto-generated method stub
		return arrestoDAO.findByID(id);
	}
	
	@Override
	public Arresto createArresto(Arresto entity) {
		// TODO Auto-generated method stub
		arrestoDAO.create(entity);
		return entity;
	}
	
	@Override
	public Arresto updateArresto(Arresto entity) {
		// TODO Auto-generated method stub
		return arrestoDAO.update(entity);
	}
	
	@Override
	public void deleteArresto(Arresto entity) {
		// TODO Auto-generated method stub
		arrestoDAO.delete(entity);
	}

}
