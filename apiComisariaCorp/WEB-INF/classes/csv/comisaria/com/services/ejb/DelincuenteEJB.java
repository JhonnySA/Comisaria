package csv.comisaria.com.services.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import csv.comisaria.com.domain.Arresto;
import csv.comisaria.com.domain.Delincuente;
import csv.comisaria.com.domain.DelincuenteCaso;
import csv.comisaria.com.domain.DelincuenteCasoPK;
import csv.comisaria.com.domain.Telefono;
import csv.comisaria.com.services.dao.DelincuenteDAOLocal;
import csv.comisaria.com.services.dao.TelefonoDAOLocal;

/**
 * Session Bean implementation class DelincuenteEJB
 */
@Stateless
public class DelincuenteEJB implements DelincuenteEJBInterface {

	@Inject
	DelincuenteDAOLocal delincuenteDAO;
	@Inject
	TelefonoDAOLocal telefonoDAO;

	/**
	 * Default constructor.
	 */

	public DelincuenteEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int countDelincuentes() {
		// TODO Auto-generated method stub
		return delincuenteDAO.count();
	}

	@Override
	public List<Delincuente> getDelincuentes() {
		// TODO Auto-generated method stub
		return delincuenteDAO.findAll();
	}

	@Override
	public List<Delincuente> getDelincuente(Integer page, Integer maxRecords) {
		// TODO Auto-generated method stub
		return delincuenteDAO.findAll(page, maxRecords);
	}

	@Override
	public Delincuente getDelincuenteById(Object id) {
		// TODO Auto-generated method stub
		return delincuenteDAO.findByID(id);
	}

	@Override
	public Delincuente createDelincuente(Delincuente entity) {
		// TODO Auto-generated method stub
		delincuenteDAO.create(entity);
		return entity;
	}

	@Override
	public Delincuente updateDelincuente(Delincuente entity) {
		// TODO Auto-generated method stub
		return delincuenteDAO.update(entity);
	}

	@Override
	public void deleteDelincuente(Delincuente entity) {
		// TODO Auto-generated method stub
		delincuenteDAO.delete(entity);
	}

	@Override
	public List<DelincuenteCaso> getDelincuenteCasos(String dni) {
		// TODO Auto-generated method stub
		return getDelincuenteById(dni).getDelincuentecasos();
	}

	@Override
	public DelincuenteCaso getDelincuenteCaso(String dni, int codCaso) {
		// TODO Auto-generated method stub
		List<DelincuenteCaso> list = getDelincuenteById(dni).getDelincuentecasos();
		DelincuenteCaso delincuenteCaso = new DelincuenteCaso();
		DelincuenteCasoPK pk = new DelincuenteCasoPK();
		pk.setCodCaso(codCaso);
		pk.setDni(dni);

		delincuenteCaso.setId(pk);

		int index = list.indexOf(delincuenteCaso);
		if (index >= 0) {
			return list.get(index);
		}
		return null;
	}

	@Override
	public DelincuenteCaso addDelincuenteCaso(String dni, int codCaso) {
		// TODO Auto-generated method stub
		Delincuente delincuente = getDelincuenteById(dni);
		DelincuenteCaso delincuenteCaso = new DelincuenteCaso();

		DelincuenteCasoPK pk = new DelincuenteCasoPK();
		pk.setCodCaso(codCaso);
		pk.setDni(dni);
		delincuenteCaso.setId(pk);

		delincuente.getDelincuentecasos().add(delincuenteCaso);
		return getDelincuenteCaso(dni, codCaso);
	}

	@Override
	public List<DelincuenteCaso> replaceDelincuenteCaso(String dni, List<DelincuenteCaso> list) {
		// TODO Auto-generated method stub
		Delincuente delincuente = getDelincuenteById(dni);
		delincuente.setDelincuentecasos(list);
		return delincuente.getDelincuentecasos();
	}

	@Override
	public void removeDelincuenteCaso(String dni, int codCaso) {
		// TODO Auto-generated method stub
		Delincuente delincuente = getDelincuenteById(dni);
		DelincuenteCaso delincuenteCaso = new DelincuenteCaso();
		DelincuenteCasoPK pk = new DelincuenteCasoPK();
		pk.setCodCaso(codCaso);
		pk.setDni(dni);
		delincuenteCaso.setId(pk);
		delincuente.getDelincuentecasos().remove(delincuenteCaso);
	}

	// Relacion con arresto
	@Override
	public List<Arresto> getArresto(String dni) {
		// TODO Auto-generated method stub
		return getDelincuenteById(dni).getArrestos();
	}

	// Relacion con telefono
	@Override
	public List<Telefono> getTelefonos(String dni) {
		// TODO Auto-generated method stub
		return getDelincuenteById(dni).getTelefonos();
	}

	@Override
	public Telefono getTelefono(String dni, String numero) {
		// TODO Auto-generated method stub
		List<Telefono> list = getDelincuenteById(dni).getTelefonos();
		Telefono telefono = new Telefono();

		telefono = telefonoDAO.findByID(numero);

		int index = list.indexOf(telefono);
		if (index >= 0) {
			return list.get(index);
		}
		return null;
	}

	@Override
	public Telefono addTelefono(String dni, String numero) {
		// TODO Auto-generated method stub
		Delincuente delincuente = getDelincuenteById(dni);
		Telefono telefono = new Telefono();

		telefono = telefonoDAO.findByID(numero);

		delincuente.getTelefonos().add(telefono);
		return getTelefono(dni, numero);
	}

	@Override
	public List<Telefono> replaceTelefono(String dni, List<Telefono> list) {
		// TODO Auto-generated method stub
		Delincuente delincuente = getDelincuenteById(dni);
		delincuente.setTelefonos(list);
		return delincuente.getTelefonos();
	}

	@Override
	public void removeTelefono(String dni, String numero) {
		// TODO Auto-generated method stub
		Delincuente delincuente = getDelincuenteById(dni);
		Telefono telefono = new Telefono();

		telefono = telefonoDAO.findByID(numero);

		delincuente.getTelefonos().remove(telefono);
	}

	// Relacion con Link
	@Override
	public Delincuente getNexDelincuente(String dni) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dni", dni);
		List<Delincuente> list = delincuenteDAO.executeListNamedQuery("Delincuente.nextId", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Delincuente getPreviusDelincuente(String dni) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dni", dni);
		List<Delincuente> list = delincuenteDAO.executeListNamedQuery("Delincuente.previousId", map);
		if (list != null && list.size() > 0) {
			return list.get(list.size() - 1);
		}
		return null;
	}

}