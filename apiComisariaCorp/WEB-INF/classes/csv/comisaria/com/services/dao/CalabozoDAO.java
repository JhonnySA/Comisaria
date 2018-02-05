package csv.comisaria.com.services.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import csv.comisaria.com.domain.Calabozo;

/**
 * Session Bean implementation class CalabozoDAO
 */
@Stateless
@LocalBean
public class CalabozoDAO extends GenericDAO<Calabozo> implements CalabozoDAOLocal {

    /**
     * Default constructor. 
     */
    public CalabozoDAO() {
        // TODO Auto-generated constructor stub
    }

}
