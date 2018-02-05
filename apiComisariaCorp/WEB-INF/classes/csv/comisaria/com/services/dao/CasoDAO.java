package csv.comisaria.com.services.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import csv.comisaria.com.domain.Caso;

/**
 * Session Bean implementation class CasoDAO
 */
@Stateless
@LocalBean
public class CasoDAO extends GenericDAO<Caso> implements CasoDAOLocal {

    /**
     * Default constructor. 
     */
    public CasoDAO() {
        // TODO Auto-generated constructor stub
    }

}
