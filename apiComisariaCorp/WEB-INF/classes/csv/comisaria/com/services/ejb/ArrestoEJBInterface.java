package csv.comisaria.com.services.ejb;

import java.util.List;

import csv.comisaria.com.domain.Arresto;

public interface ArrestoEJBInterface {

	public int countArrestos();
    public List<Arresto> getArrestos();
    public List<Arresto> getArrestos(Integer page, Integer maxRecords);
    public Arresto getArrestoById(Object id);
    public Arresto createArresto(Arresto entity);
    public Arresto updateArresto(Arresto entity);
    public void deleteArresto(Arresto entity);
    
}
