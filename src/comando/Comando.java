package comando;
import excepcion.ComandoExcepcion;

public interface Comando {
    public void ejecutar() throws ComandoExcepcion;
}
