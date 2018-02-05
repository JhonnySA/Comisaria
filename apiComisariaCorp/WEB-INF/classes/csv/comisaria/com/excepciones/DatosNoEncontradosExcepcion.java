package csv.comisaria.com.excepciones;

public class DatosNoEncontradosExcepcion extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DatosNoEncontradosExcepcion(String mensaje) {
		super(mensaje);
	}
}
