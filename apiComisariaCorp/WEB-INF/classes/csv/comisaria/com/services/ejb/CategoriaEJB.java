package csv.comisaria.com.services.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import csv.comisaria.com.domain.Categoria;
import csv.comisaria.com.domain.Policia;
import csv.comisaria.com.services.dao.CategoriaDAOLocal;
import csv.comisaria.com.services.dao.PoliciaDAOLocal;

/**
 * Session Bean implementation class CategoriaEJB
 */
@Stateless
public class CategoriaEJB implements CategoriaEJBInterface {

	@Inject
	CategoriaDAOLocal categoriaDAO;
	@Inject
	PoliciaDAOLocal policiaDAO;

	/**
	 * Default constructor.
	 */

	public CategoriaEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int countCategorias() {
		// TODO Auto-generated method stub
		return categoriaDAO.count();
	}

	@Override
	public List<Categoria> getCategorias() {
		// TODO Auto-generated method stub
		return categoriaDAO.findAll();
	}

	@Override
	public List<Categoria> getCategorias(Integer page, Integer maxRecords) {
		// TODO Auto-generated method stub
		return categoriaDAO.findAll(page, maxRecords);
	}

	@Override
	public Categoria getCategoriaById(Object id) {
		// TODO Auto-generated method stub
		return categoriaDAO.findByID(id);
	}

	@Override
	public Categoria getCategoriaByIdInteger(Integer id) {
		// TODO Auto-generated method stub
		return categoriaDAO.findByID(id);
	}

	@Override
	public Categoria createCategoria(Categoria entity) {
		// TODO Auto-generated method stub
		categoriaDAO.create(entity);
		return entity;
	}

	@Override
	public Categoria updateCategoria(Categoria entity) {
		// TODO Auto-generated method stub
		return categoriaDAO.update(entity);
	}

	@Override
	public void deleteCategoria(Categoria entity) {
		// TODO Auto-generated method stub
		categoriaDAO.delete(entity);
	}

	// Relacion con policia
	@Override
	public List<Policia> gePolicias(int codCategoria) {
		// TODO Auto-generated method stub
		return getCategoriaById(codCategoria).getPolicias();
	}

	@Override
	public Policia getPolicia(int codCategoria, int codPolicia) {
		// TODO Auto-generated method stub
		List<Policia> list = getCategoriaByIdInteger(codCategoria).getPolicias();
		Policia policia = new Policia();

		policia = policiaDAO.findByID(codPolicia);

		int index = list.indexOf(policia);

		if (index >= 0) {
			return list.get(index);
		}
		return null;
	}

	@Override
	public Policia addPolicia(int codCategoria, int codPolicia) {
		// TODO Auto-generated method stub
		Categoria categoria = getCategoriaById(codCategoria);
		Policia policia = new Policia();

		// policia.setCodPolicia(codPolicia);
		policia = policiaDAO.findByID(codPolicia);

		categoria.getPolicias().add(policia);
		return getPolicia(codCategoria, codPolicia);
	}

	@Override
	public List<Policia> replacePolicias(int codCategoria, List<Policia> list) {
		// TODO Auto-generated method stub
		Categoria categoria = getCategoriaById(codCategoria);
		categoria.setPolicias(list);
		return categoria.getPolicias();
	}

	@Override
	public void removePolicia(int codCategoria, int codPolicia) {
		// TODO Auto-generated method stub
		Categoria categoria = getCategoriaById(codCategoria);
		Policia policia = new Policia();

		// policia.setCodPolicia(codPolicia);
		policia = policiaDAO.findByID(codPolicia);

		categoria.getPolicias().remove(policia);
	}

	// Relacion con Link
	@Override
	public Categoria getNexCategoria(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Categoria> list = categoriaDAO.executeListNamedQuery("Categoria.nextId", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Categoria getPreviusCategoria(int cod) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cod", cod);
		List<Categoria> list = categoriaDAO.executeListNamedQuery("Categoria.previousId", map);
		if (list != null && list.size() > 0) {
			return list.get(list.size() - 1);
		}
		return null;
	}

}