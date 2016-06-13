package comando;
import excepcion.ComandoExcepcion;
import excepcion.ItemNoExiste;
import item.Item;
import personaje.Jugador;

public class Tirar implements Comando{
    private final Jugador jugador;
    private final Item item;
    
    public Tirar(Jugador jugador, Item item){
        this.jugador = jugador;
        this.item = item;
    }
    
    @Override
    public void ejecutar() throws ComandoExcepcion{
        if(item == null){
            throw new ItemNoExiste();
        }
        
        jugador.tirar(item);
    }
}