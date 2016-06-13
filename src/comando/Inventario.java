package comando;
import personaje.Jugador;

public class Inventario implements Comando{
    private final Jugador jugador;
    
    public Inventario(Jugador jugador){
        this.jugador = jugador;
    }
    
    @Override
    public void ejecutar() {
        jugador.inventario();
    }
}