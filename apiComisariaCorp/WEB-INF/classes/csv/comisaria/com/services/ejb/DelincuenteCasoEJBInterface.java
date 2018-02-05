package csv.comisaria.com.services.ejb;

import java.util.List;

import csv.comisaria.com.domain.DelincuenteCaso;

public interface DelincuenteCasoEJBInterface {

	public int countDelincuenteCasos();
    public List<DelincuenteCaso> getDelincuenteCasos();
    public List<DelincuenteCaso> getDelincuenteCasos(Integer page, Integer maxRecords);
    public DelincuenteCaso getDelincuenteCasoById(Object id);
    public DelincuenteCaso createDelincuenteCaso(DelincuenteCaso entity);
    public DelincuenteCaso updateDelincuenteCaso(DelincuenteCaso entity);
    public void deleteDelincuenteCaso(DelincuenteCaso entity);
}
