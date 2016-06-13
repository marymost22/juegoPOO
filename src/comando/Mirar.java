package comando;
import entorno.Celda;
import excepcion.CeldaNoValida;
import excepcion.ComandoExcepcion;
import excepcion.EnemigoNoAqui;
import excepcion.EnemigoNoExiste;
import excepcion.ItemNoExiste;
import item.Item;
import personaje.Enemigo;
import personaje.Jugador;

public class Mirar implements Comando{
    private final Jugador jugador;
    private final Item item;
    private final Celda celda;
    private final Enemigo enemigo;
    private final int formaCmd;
    
    public Mirar(Jugador jugador){
        this.jugador = jugador;
        this.item = null;
        this.celda = null;
        this.enemigo = null;
        this.formaCmd = 0;
    }
    
    public Mirar(Jugador jugador, Item item){
        this.jugador = jugador;
        this.item = item;
        this.celda = null;
        this.enemigo = null;
        this.formaCmd = 1;
    }
    
    public Mirar(Jugador jugador, Celda celda){
        this.jugador = jugador;
        this.item = null;
        this.celda = celda;
        this.enemigo = null;
        this.formaCmd = 2;
    }
    
    public Mirar(Jugador jugador, Celda celda, Enemigo enemigo){
        this.jugador = jugador;
        this.item = null;
        this.celda = celda;
        this.enemigo = enemigo;
        this.formaCmd = 3;
    }
    
    @Override
    public void ejecutar() throws ComandoExcepcion{
        switch(formaCmd){
            case 0:
                jugador.mirar();
                break;
            case 1:
                if(item == null){
                    throw new ItemNoExiste();
                }
                jugador.mirar(item);
                break;
            case 2:
                if(celda == null){
                    throw new CeldaNoValida();
                }
                jugador.mirar(celda);
                break;
            case 3:
                if(celda == null){
                    throw new CeldaNoValida();
                }
                else if(enemigo == null){
                    throw new EnemigoNoExiste();
                }
                else if(!celda.getEnemigos().contains(enemigo)){
                    throw new EnemigoNoAqui();
                }              
                jugador.mirar(celda, enemigo);
        }
    }
}