package csv.comisaria.com.services.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import csv.comisaria.com.domain.Arsenal;

/**
 * Session Bean implementation class ArsenalDAO
 */
@Stateless
@LocalBean
public class ArsenalDAO extends GenericDAO<Arsenal> implements ArsenalDAOLocal {

    /**
     * Default constructor. 
     */
    public ArsenalDAO() {
        // TODO Auto-generated constructor stub
    }

}
