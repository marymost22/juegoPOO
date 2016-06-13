package comando;
import entorno.Celda;
import excepcion.CeldaNoValida;
import excepcion.ComandoExcepcion;
import excepcion.EnemigoNoAqui;
import excepcion.EnemigoNoExiste;
import excepcion.SinEnemigos;
import java.util.HashSet;
import juego.Juego;
import personaje.Enemigo;
import personaje.Jugador;

public class Atacar implements Comando{
    private final Jugador jugador;
    private final Juego juego;
    private final Celda celda;
    private final Enemigo enemigo;
    private final int formaCmd;
    
    public Atacar(Juego juego, Celda celda){
        this.juego = juego;
        this.jugador = juego.getJugador();
        this.celda = celda;
        this.enemigo = null;
        this.formaCmd = 0;
    }
    
    public Atacar(Juego juego, Celda celda, Enemigo enemigo){
        this.juego = juego;
        this.jugador = juego.getJugador();
        this.celda = celda;
        this.enemigo = enemigo;
        this.formaCmd = 1;
    }
    
    @Override
    public void ejecutar() throws ComandoExcepcion{
        if(celda == null){
            throw new CeldaNoValida();
        }
        
        switch(formaCmd){
            case 0:
                if(celda.getEnemigos().isEmpty()){
                    throw new SinEnemigos();
                }
                jugador.atacar(celda);
                break;
            case 1:
                if(enemigo == null){
                    throw new EnemigoNoExiste();
                }
                else if(!celda.getEnemigos().contains(enemigo)){
                    throw new EnemigoNoAqui();
                }
                jugador.atacar(enemigo);
        }
        
        HashSet<Enemigo> enemAux = new HashSet(juego.getEnemigos());
        
        for(Enemigo e:enemAux){
            if(e.getSalud() == 0){
                juego.getEnemigos().remove(e);
            }
        }
        
        if(juego.getEnemigos().isEmpty()) juego.ganarJuego();
    }
}