package csv.comisaria.com.services.ejb;

import java.util.List;

import csv.comisaria.com.domain.Categoria;
import csv.comisaria.com.domain.Policia;

public interface CategoriaEJBInterface {

	public int countCategorias();
    public List<Categoria> getCategorias();
    public List<Categoria> getCategorias(Integer page, Integer maxRecords);
    public Categoria getCategoriaById(Object id);
    public Categoria getCategoriaByIdInteger(Integer id);
    public Categoria createCategoria(Categoria entity);
    public Categoria updateCategoria(Categoria entity);
    public void deleteCategoria(Categoria entity);
    
    // Relacion con Link
  	public Categoria getNexCategoria(int cod);
  	public Categoria getPreviusCategoria(int cod);
    
    //Relacion con Policia
    public List<Policia> gePolicias(int codCategoria);
    public Policia getPolicia(int codCategoria, int codPolicia);
    public Policia addPolicia(int codCategoria, int codPolicia);
    public List<Policia> replacePolicias(int codCategoria, List<Policia> list);
    public void removePolicia(int codCategoria, int codPolicia);
}
