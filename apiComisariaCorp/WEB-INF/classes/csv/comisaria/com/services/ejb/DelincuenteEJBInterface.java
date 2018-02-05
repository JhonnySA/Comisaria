package csv.comisaria.com.services.ejb;

import java.util.List;

import csv.comisaria.com.domain.Arresto;
import csv.comisaria.com.domain.Delincuente;
import csv.comisaria.com.domain.DelincuenteCaso;
import csv.comisaria.com.domain.Telefono;

public interface DelincuenteEJBInterface {

	public int countDelincuentes();
    public List<Delincuente> getDelincuentes();
    public List<Delincuente> getDelincuente(Integer page, Integer maxRecords);
    public Delincuente getDelincuenteById(Object id);
    public Delincuente createDelincuente(Delincuente entity);
    public Delincuente updateDelincuente(Delincuente entity);
    public void deleteDelincuente(Delincuente entity);
    
    // Relacion con Link
    public Delincuente getNexDelincuente(String dni);
	public Delincuente getPreviusDelincuente(String dni);
    
	// Relacion con DelincuenteCaso
    public List<DelincuenteCaso> getDelincuenteCasos(String dni);
    public DelincuenteCaso getDelincuenteCaso(String dni, int codCaso);
    public DelincuenteCaso addDelincuenteCaso(String dni, int codCaso);
    public List<DelincuenteCaso> replaceDelincuenteCaso(String dni, List<DelincuenteCaso> list);
    public void removeDelincuenteCaso(String dni, int codCaso);
    
    // Relacion con Arresto (PoliciaDelincuente)
    public List<Arresto> getArresto(String dni);
    // public Arresto getArresto(String dni, int codPolicia);
    // public Arresto addArresto(String dni, int codPolicia);
    // public List<Arresto> replaceArresto(String dni, List<Arresto> list);
    // public void removeArresto(String dni, int codPolicia);
    
    // Relacion con Telefono
    public List<Telefono> getTelefonos(String dni);
    public Telefono getTelefono(String dni, String numero);
    public Telefono addTelefono(String dni, String numero);
    public List<Telefono> replaceTelefono(String dni, List<Telefono> list);
    public void removeTelefono(String dni, String numero);
}
