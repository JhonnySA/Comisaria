package csv.comisaria.com.services.ejb;

import java.util.List;

import csv.comisaria.com.domain.Caso;
import csv.comisaria.com.domain.DelincuenteCaso;
import csv.comisaria.com.domain.Policia;

public interface CasoEJBInterface {

	public int countCasos();
    public List<Caso> getCasos();
    public List<Caso> getCasos(Integer page, Integer maxRecords);
    public Caso getCasoById(Object id);
    public Caso createCaso(Caso entity);
    public Caso updateCaso(Caso entity);
    public void deleteCaso(Caso entity);
    
    // Relacion con Link
 	public Caso getNexCaso(int cod);
 	public Caso getPreviusCaso(int cod);
    
    //Relacion con Policia
    public List<Policia> getPolicias(int codCaso);
    public Policia getPolicia(int codCaso, int codPolicia);
    public Policia addPolicia(int codCaso, int codPolicia);
    public List<Policia> replacePolicia(int codCaso, List<Policia> list);
    public void removePolicia(int codCaso, int codPolicia);
    

  //Relacion con DelincuenteCaso
    public List<DelincuenteCaso> getDelincuenteCasos(int codCaso);
    public DelincuenteCaso getDelincuenteCaso(int codCaso, String dni);
    public DelincuenteCaso addDelincuenteCaso(int codCaso, String dni);
    public List<DelincuenteCaso> replaceDelincuenteCaso(int codCaso, List<DelincuenteCaso> list);
    public void removeDelincuenteCaso(int codCaso, String dni);
}
