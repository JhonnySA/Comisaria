package csv.comisaria.com.services.ejb;

import java.util.List;

import csv.comisaria.com.domain.Arresto;
import csv.comisaria.com.domain.Policia;
import csv.comisaria.com.domain.PoliciaArsenal;

public interface PoliciaEJBInterface {

	public int countPolicias();
    public List<Policia> getPolicias();
    public List<Policia> getPolicias(Integer page, Integer maxRecords);
    public Policia getPoliciaById(Object id);
    public Policia createPolicia(Policia entity);
    public Policia updatePolicia(Policia entity);
    public void deletePolicia(Policia entity);
    
    // Relacion con Link
    public Policia getNexPolicia(int cod);
	public Policia getPreviusPolicia(int cod);
    
    // Relacion con PoliciaArsenal
    public List<PoliciaArsenal> getPoliciaArsenals(int codPolicia);
    public PoliciaArsenal getPoliciaArsenals(int codPolicia, int codArsenal);
    public PoliciaArsenal addPoliciaArsenal(int codPolicia, int codArsenal);
    public List<PoliciaArsenal> replacePoliciaArsenals(int codPolicia, List<PoliciaArsenal> list);
    public void removePoliciaArsenal(int codPolicia, int codArsenal);
    
  // Relacion con Arresto (PoliciaDelincuente)
    public List<Arresto> getArresto(int codPolicia);
    // public Arresto getArresto(int codPolicia, String dni);
    // public Arresto addArresto(int codPolicia, String dni);
    // public List<Arresto> replaceArresto(int codPolicia, List<Arresto> list);
    // public void removeArresto(int codPolicia, String dni);
    
    
}
