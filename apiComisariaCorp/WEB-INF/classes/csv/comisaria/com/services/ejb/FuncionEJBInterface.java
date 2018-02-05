package csv.comisaria.com.services.ejb;

import java.util.List;

import csv.comisaria.com.domain.Funcion;
import csv.comisaria.com.domain.Policia;

public interface FuncionEJBInterface {

	public int countFunciones();
    public List<Funcion> getFunciones();
    public List<Funcion> getFunciones(Integer page, Integer maxRecords);
    public Funcion getFuncionById(Object id);
    public Funcion createFuncion(Funcion entity);
    public Funcion updateFuncion(Funcion entity);
    public void deleteFuncion(Funcion entity);
    
    // Relacion con Link
    public Funcion getNexFuncion(int cod);
	public Funcion getPreviusFuncion(int cod);
    
    //Relacion con Policia
    public List<Policia> getPolicias(int codFuncion);
    public Policia getPolicia(int codFuncion, int codPolicia);
    public Policia addPolicia(int codFuncion, int codPolicia);
    public List<Policia> replacePolicias(int codFuncion, List<Policia> list);
    public void removePolicia(int codFuncion, int codPolicia);
}
