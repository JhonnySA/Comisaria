package csv.comisaria.com.services.dao;
/**
*
* @author Edwin  Valencia Castillo
*/

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GenericDAO<T> implements GenericDAOInterface<T> {

	private final static String UNIT_NAME = "apiComisariaCorp";
    @PersistenceContext(unitName = UNIT_NAME)
    protected  EntityManager em;
    
    /**
     * Crea un nuevo registro en la base de datos para una determinada entidad
     * @param entity Informacion a registrar
     * @return Nueva instancia manejada con su ID
     */
	@Override
	public void create(T entity) {
		
		em.persist(entity);		
	}
	
	 /**
     * Elimina un registro de una entidad en la base de datos.
     * @param id ID del registro a eliminar
     */
	@Override
	public void delete(T entity) {
		em.remove(em.merge(entity));
		
		
		//@SuppressWarnings("unchecked")
		//Class<T> entityClass = (Class<T>) ((ParameterizedType)
			//	getClass().getGenericSuperclass()).getActualTypeArguments()[0];	  
	     // T entity = em.find(entityClass, id);
	       // em.remove(entity);
	        
	    }


	 /**
     * Actualiza una instancia existente de una entidad.
     *
     * @param entity Instacia a actualizar
     * @return Instancia actualizada
     */
	@Override
	public T update(T entity) {
			return em.merge(entity);
	}

	 /**
     * Obtiene una instancia de una entidad dado el ID.
     * @param id ID de la instancia a obtener
     * @return Instancia de la entidad manejada
     */
	@SuppressWarnings("unchecked")
	@Override
	public T findByID(Object entityID) {
		Class<T> entityClass = (Class<T>) ((ParameterizedType)
				getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        // Identifica la clase real de las entidades gestionada por este objeto (T.class)
    	return em.find(entityClass, entityID); //retorna un objeto que coincide con el ID
   
	}

	/**
     * Obtiene una coleccion de  todas las intancias de una entidad
     * @return Colecion de instacias de una entidad
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> findAll() {
		Class<T> entityClass = (Class<T>) ((ParameterizedType)  
				getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    	CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
	}
	
	/**
     * Obtiene una coleccion de  intancias de una entidad de acuerdo a un rango determinado
     * @return Colecion de instacias de una entidad
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findRange(int[] range) {
		Class<T> entityClass = (Class<T>) ((ParameterizedType)  
				getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
	
	 /**
     * Obtiene la cantidad de registros en la base de datos para una determinada entidad
     * @return Numero de registros manejados por la entidad
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int count() {
		Class<T> entityClass = (Class<T>) ((ParameterizedType)
				getClass().getGenericSuperclass()).getActualTypeArguments()[0];   
    	CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
	}
	
	//mas funcionalidades 
    /**
     * Obtiene una coleccion de todas las instancias de una entidad permitiendo paginacion.
     * @param page Numero de la pagina a obtener
     * @param maxRecords Numero de registros por pagina
     * @return Coleccion de instancias de la entidad manejada
     */
    @SuppressWarnings("unchecked")
	public List<T> findAll(Integer page, Integer maxRecords) {
    	Class<T> entityClass = (Class<T>) ((ParameterizedType)  
				getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Query q = em.createQuery("select u from " + entityClass.getSimpleName() + " u");
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return q.getResultList();
    }

    /**
     * Permite ejecutar un namedQuery que retorna una coleccion.
     * @param <V> Tipo de cada elemento de la coleccion
     * @param name Nombre del  namedQuery
     * @return Colleccion de instancias de V
     */
    @SuppressWarnings("unchecked")
	public <V> List<V> executeListNamedQuery(String name) {
        return em.createNamedQuery(name).getResultList();
    }

    /**
     * Permite ejecutar un namedQuery que retorne una coleccion con paginacion.
     *
     * @param <V> Tipo de cada elemento de la coleccion
     * @param name Nombre del namedQuery
     * @param params Map de parametros para la consulta, donde la clave es el nombre del parametro
     * @return Coleccion de instancias de V
     */
    @SuppressWarnings("unchecked")
	public <V> List<V> executeListNamedQuery(String name, Map<String, Object> params) {
        Query q = em.createNamedQuery(name);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return q.getResultList();
    }

    /**
     * Permite ejecutar un namedQuery que retorna una simple instancia.
     * @param <V> Tipo d ela instancia a obtener
     * @param name Nombre del namedQuery
     * @return Instanca de V
     */
    @SuppressWarnings("unchecked")
	public <V> V executeSingleNamedQuery(String name) {
        return (V) em.createNamedQuery(name).getSingleResult();
    }

    /**
     * Permite ejecutar un namedQuery que retorna una simple instancia con paginacion.
     * @param <V> Tipo de la instancia a obtener
     * @param name Nombre del namedQuery
     * @param params Mapa de parametros para la query, donde la clave es el nombre de parametro
     * @return Instancia de V
     */
    @SuppressWarnings("unchecked")
	public <V> V executeSingleNamedQuery(String name, Map<String, Object> params) {
        Query q = em.createNamedQuery(name);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return (V) q.getSingleResult();
    }

    /**
     * Permite buscar todos los registros en una entidad por una cadena contenida en el name.
     * @param text Cadena de busqueda con nombres
     * @return Colleccon de registros con el texto contenido en el nombre
     */
    @SuppressWarnings("unchecked")
	public List<T> findByName(String text) {
    	Class<T> entityClass = (Class<T>) ((ParameterizedType)  
				getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Query q = em.createQuery("select u from " + entityClass.getSimpleName() + " u where u.name like :name");
        q.setParameter("name", "%" + text + "%");
        return q.getResultList();
    }

    /**
     * Permite buscar todos los registros en una entidad por una cadena contenida en el name con paginacion.
     * @param text Cadena de busqueda con nombres
     * @param page pagina a obtener
     * @param maxRecords Cantidad de registros a obtener
     * @return Colleccion de registros con texto contenido en el nombre
     */
    @SuppressWarnings("unchecked")
	public List<T> findByName(String text, Integer page, Integer maxRecords) {
    	Class<T> entityClass = (Class<T>) ((ParameterizedType)  
				getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Query q = em.createQuery("select u from " + entityClass.getSimpleName() + " u where u.name like :name");
        q.setParameter("name", "%" + text + "%");
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return q.getResultList();
    }
    
    /**
     * Permite ejecutar un namedQuery que retorne una coleccion.
     * @param <V> Tipo de cada elemento de la coleccion
     * @param name Nombre del namedQuery
     * @param parametroBusqueda Nombre del parametro de busqueda
     * @param textoBusqueda Nombre valor del textoa buscar de acuerdo al namedQuery
     * @return Colleccion de instancias de V
     */
    @SuppressWarnings("unchecked")
	public <V> List<V> executeListNamedQueryByString(String name, String parametroBusqueda, String textoBusqueda) {
        return em.createNamedQuery(name).setParameter(parametroBusqueda,"%"+textoBusqueda+"%").getResultList();
    }

	@SuppressWarnings("unchecked")
	@Override
	public <V> List<V> executeListNamedQueryByString(String name, String parametroBusqueda, int textoBusqueda) {
		// TODO Auto-generated method stub
		return em.createNamedQuery(name).setParameter(parametroBusqueda,"%"+textoBusqueda+"%").getResultList();
	}
 
}