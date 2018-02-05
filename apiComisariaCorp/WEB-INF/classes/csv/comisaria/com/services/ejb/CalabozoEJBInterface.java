package csv.comisaria.com.services.ejb;

import java.util.List;

import csv.comisaria.com.domain.Calabozo;
import csv.comisaria.com.domain.Delincuente;

public interface CalabozoEJBInterface {

	public int countCalabozos();
    public List<Calabozo> getCalabozos();
    public List<Calabozo> getCalabozos(Integer page, Integer maxRecords);
    public Calabozo getCalabozoById(Object id);
    public Calabozo createCalabozo(Calabozo entity);
    public Calabozo updateCalabozo(Calabozo entity);
    public void deleteCalabozo(Calabozo entity);
    
    // Relacion con Link
 	public Calabozo getNexCalabozo(int cod);
 	public Calabozo getPreviusCalabozo(int cod);
    
    //Relacion con Delincuente
    public List<Delincuente> getDelincuentes(int codCalabozo);
    public Delincuente getDelincuente(int codCalabozo, String dni);
    public Delincuente addDelincuente(int codCalabozo, String dni);
    public List<Delincuente> replaceDelincuentes(int codCalabozo, List<Delincuente> list);
    public void removeDelincuente(int codCalabozo, String dni);
}
