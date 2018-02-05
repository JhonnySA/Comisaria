package csv.comisaria.com.services.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import csv.comisaria.com.domain.Clase;

/**
 * Session Bean implementation class ClaseDAO
 */
@Stateless
@LocalBean
public class ClaseDAO extends GenericDAO<Clase> implements ClaseDAOLocal {

    /**
     * Default constructor. 
     */
    public ClaseDAO() {
        // TODO Auto-generated constructor stub
    }

}
