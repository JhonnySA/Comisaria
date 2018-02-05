package csv.comisaria.com.services.ejb;

import java.util.List;

import csv.comisaria.com.domain.Arsenal;
import csv.comisaria.com.domain.PoliciaArsenal;

public interface ArsenalEJBInterface {

	public int countArsenales();
	public List<Arsenal> getArsenales();
	public List<Arsenal> getArsenales(Integer page, Integer maxRecords);
	public Arsenal getArsenalById(Object id);
	public Arsenal createArsenal(Arsenal entity);
	public Arsenal updateArsenal(Arsenal entity);
	public void deleteArsenal(Arsenal entity);

	
	// Relacion con Link
	public Arsenal getNexArsenal(int cod);
	public Arsenal getPreviusArsenal(int cod);

	// Relacion con PoliciaArsenal
	public List<PoliciaArsenal> getPoliciaArsenals(int codArsenal);
	public PoliciaArsenal getPoliciaArsenals(int codArsenal, int codPolicia);
	public PoliciaArsenal addPoliciaArsenal(int codArsenal, int codPolicia);
	public List<PoliciaArsenal> replacePoliciaArsenals(int codArsenal, List<PoliciaArsenal> list);
	public void removePoliciaArsenal(int codArsenal, int codPolicia);
}
