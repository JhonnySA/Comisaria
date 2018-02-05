package csv.comisaria.com.services.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import csv.comisaria.com.domain.Arresto;

/**
 * Session Bean implementation class ArrestoDAO
 */
@Stateless
@LocalBean
public class ArrestoDAO extends GenericDAO<Arresto> implements ArrestoDAOLocal {

    /**
     * Default constructor. 
     */
    public ArrestoDAO() {
        // TODO Auto-generated constructor stub
    }

}
