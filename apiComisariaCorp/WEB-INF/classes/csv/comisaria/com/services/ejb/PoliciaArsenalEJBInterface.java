package csv.comisaria.com.services.ejb;

import java.util.List;

import csv.comisaria.com.domain.PoliciaArsenal;

public interface PoliciaArsenalEJBInterface {

	public int countDetallePolArs();
    public List<PoliciaArsenal> getDetallePolArs();
    public List<PoliciaArsenal> getDetallePolArs(Integer page, Integer maxRecords);
    public PoliciaArsenal getDetallePolArs(Object id);
    public PoliciaArsenal createDetallePolArs(PoliciaArsenal entity);
    public PoliciaArsenal updateDetallePolArs(PoliciaArsenal entity);
    public void deleteDetallePolArs(PoliciaArsenal entity);
}
