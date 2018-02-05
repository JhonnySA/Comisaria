package csv.comisaria.com.services.ejb;

import java.util.List;

import csv.comisaria.com.domain.Telefono;

public interface TelefonoEJBInteface {

	public int countsTelefonos();
	public List<Telefono> getTelefonos();
	public List<Telefono> getTelefonos(Integer page, Integer maxRecords);
	public Telefono getTelefonoById(Object id);
	public Telefono createTelefono(Telefono entity);
	public Telefono updateTelefono(Telefono entity);
	public void deleteTelefono(Telefono entity);
	
	// Relacion con Link
    public Telefono getNexTelefono(String num);
	public Telefono getPreviusTelefono(String num);
}
