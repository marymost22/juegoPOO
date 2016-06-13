package comando;
import entorno.Celda;
import excepcion.CeldaNoValida;
import excepcion.ComandoExcepcion;
import personaje.Jugador;

public class Mover implements Comando{
    private final Jugador jugador;
    private final Celda destino;
    
    public Mover(Jugador jugador, Celda destino){
        this.jugador = jugador;
        this.destino = destino;
    }
    
    @Override
    public void ejecutar() throws ComandoExcepcion{
        if(destino == null || !destino.isTransitable()){
            throw new CeldaNoValida();
        }
        
        jugador.mover(destino);
    }
}