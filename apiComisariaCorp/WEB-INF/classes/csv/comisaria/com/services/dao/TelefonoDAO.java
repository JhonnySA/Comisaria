package csv.comisaria.com.services.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import csv.comisaria.com.domain.Telefono;

/**
 * Session Bean implementation class TelefonoDAO
 */
@Stateless
@LocalBean
public class TelefonoDAO extends GenericDAO<Telefono> implements TelefonoDAOLocal {

    /**
     * Default constructor. 
     */
    public TelefonoDAO() {
        // TODO Auto-generated constructor stub
    }

}
