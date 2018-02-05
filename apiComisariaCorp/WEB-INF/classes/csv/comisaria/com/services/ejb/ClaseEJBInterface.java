package csv.comisaria.com.services.ejb;

import java.util.List;

import csv.comisaria.com.domain.Arsenal;
import csv.comisaria.com.domain.Clase;

public interface ClaseEJBInterface {

	public int countClases();
    public List<Clase> getClases();
    public List<Clase> getClases(Integer page, Integer maxRecords);
    public Clase getClaseById(Object id);
    public Clase createClase(Clase entity);
    public Clase updateClase(Clase entity);
    public void deleteClase(Clase entity);
    
    // Hateoas (Paginacion especializada)
    public List<Clase> getPageClases(Integer page, Integer maxRecords, List<Clase>list);
    
    // Relacion con arsenal
    public List<Arsenal> getArsenales(int codClase);
    public Arsenal getArsenal(int codClase, int codArsenal);
    public Arsenal addArsenal(int codClase, int codArsenal);
    public List<Arsenal> replaceArsenal(int codClase, List<Arsenal> list);
    public void removeArsenal(int codClase, int codArsenal);
    
    // Relacion con Link
    public Clase getNexClase(int cod);
	public Clase getPreviusClase(int cod);
	public Clase getLastClase();
	public List<Clase> getAllNex(int cod);
	
}
