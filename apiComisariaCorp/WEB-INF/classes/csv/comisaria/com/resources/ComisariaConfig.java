package csv.comisaria.com.resources;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import csv.comisaria.com.excepciones.DatosNoEncontradosMapper;
import csv.comisaria.com.excepciones.ExcepcionGeneral;
import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("/*")
public class ComisariaConfig extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	public ComisariaConfig() {

		singletons.add(ArsenalResource.class);
		classes.add(DatosNoEncontradosMapper.class);
		classes.add(ExcepcionGeneral.class);

		// Pasa swagger
		BeanConfig conf = new BeanConfig();
		conf.setTitle("Comisaria API");
		conf.setDescription("Arquitectura de procesos del negocio - Caso: COMISARIA");
		conf.setVersion("1.0.0");
		conf.setHost("localhost:8085");
		conf.setBasePath("/apiComisariaCorp/");
		conf.setSchemes(new String[] { "http" });
		conf.setResourcePackage("csv.comisaria.com.resources");
		conf.setScan(true);
	}
}
