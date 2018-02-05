package csv.comisaria.com.services.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import csv.comisaria.com.domain.DelincuenteCaso;

/**
 * Session Bean implementation class DelincuenteCasoDAO
 */
@Stateless
@LocalBean
public class DelincuenteCasoDAO extends GenericDAO<DelincuenteCaso> implements DelincuenteCasoDAOLocal {

    /**
     * Default constructor. 
     */
    public DelincuenteCasoDAO() {
        // TODO Auto-generated constructor stub
    }

}
