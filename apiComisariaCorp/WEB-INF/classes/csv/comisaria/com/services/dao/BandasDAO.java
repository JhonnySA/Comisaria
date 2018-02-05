package csv.comisaria.com.services.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import csv.comisaria.com.domain.Banda;

/**
 * Session Bean implementation class BandasDAO
 */
@Stateless
@LocalBean
public class BandasDAO extends GenericDAO<Banda> implements BandasDAOLocal {

    /**
     * Default constructor. 
     */
    public BandasDAO() {
        // TODO Auto-generated constructor stub
    }

}
