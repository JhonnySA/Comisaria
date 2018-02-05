package csv.comisaria.com.services.ejb;

import java.util.List;

import csv.comisaria.com.domain.Banda;

public interface BandaEJBInterface {

	public int countBandas();
	public List<Banda> getBandas();
	public List<Banda> getBandas(Integer page, Integer maxRecords);
	public Banda getBandaById(Object id);
	public Banda createBanda(Banda entity);
	public Banda updateBanda(Banda entity);
	public void deleteBanda(Banda entity);

	// Relacion con Link
	public Banda getNexBanda(int cod);
	public Banda getPreviusBanda(int cod);
}
