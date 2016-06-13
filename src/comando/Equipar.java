package comando;
import excepcion.ComandoExcepcion;
import excepcion.ItemNoEnMochila;
import excepcion.ItemNoEquipable;
import item.Equipable;
import item.Item;
import personaje.Jugador;

public class Equipar implements Comando{
    private final Jugador jugador;
    private final Item item;
    
    public Equipar(Jugador jugador, Item item){
        this.jugador = jugador;
        this.item = item;
    }
    
    @Override
    public void ejecutar() throws ComandoExcepcion{
        if(item == null){
            throw new ItemNoEnMochila();
        }
        else if(!(item instanceof Equipable)){
            throw new ItemNoEquipable();
        }
        
        jugador.equipar(item);
    }
}