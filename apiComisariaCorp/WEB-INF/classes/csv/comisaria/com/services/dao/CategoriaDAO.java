package csv.comisaria.com.services.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import csv.comisaria.com.domain.Categoria;

/**
 * Session Bean implementation class CategoriaDAO
 */
@Stateless
@LocalBean
public class CategoriaDAO extends GenericDAO<Categoria> implements CategoriaDAOLocal {

    /**
     * Default constructor. 
     */
    public CategoriaDAO() {
        // TODO Auto-generated constructor stub
    }

}
