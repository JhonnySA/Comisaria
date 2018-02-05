package csv.comisaria.com.services.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import csv.comisaria.com.domain.Delincuente;

/**
 * Session Bean implementation class DelincuenteDAO
 */
@Stateless
@LocalBean
public class DelincuenteDAO extends GenericDAO<Delincuente> implements DelincuenteDAOLocal {

    /**
     * Default constructor. 
     */
    public DelincuenteDAO() {
        // TODO Auto-generated constructor stub
    }

}
