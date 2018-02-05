package csv.comisaria.com.services.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import csv.comisaria.com.domain.Funcion;

/**
 * Session Bean implementation class FuncionDAO
 */
@Stateless
@LocalBean
public class FuncionDAO extends GenericDAO<Funcion> implements FuncionDAOLocal {

    /**
     * Default constructor. 
     */
    public FuncionDAO() {
        // TODO Auto-generated constructor stub
    }

}
