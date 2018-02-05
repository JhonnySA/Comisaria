package csv.comisaria.com.services.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import csv.comisaria.com.domain.PoliciaArsenal;

/**
 * Session Bean implementation class PoliciaArsenalDAO
 */
@Stateless
@LocalBean
public class PoliciaArsenalDAO extends GenericDAO<PoliciaArsenal> implements PoliciaArsenalDAOLocal {

    /**
     * Default constructor. 
     */
    public PoliciaArsenalDAO() {
        // TODO Auto-generated constructor stub
    }

}
