package csv.comisaria.com.services.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import csv.comisaria.com.domain.Policia;

/**
 * Session Bean implementation class PoliciaDAO
 */
@Stateless
@LocalBean
public class PoliciaDAO extends GenericDAO<Policia> implements PoliciaDAOLocal {

    /**
     * Default constructor. 
     */
    public PoliciaDAO() {
        // TODO Auto-generated constructor stub
    }

}
