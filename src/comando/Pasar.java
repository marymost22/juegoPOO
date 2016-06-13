package comando;

import excepcion.ComandoExcepcion;
import item.Explosivo;
import java.util.HashSet;
import java.util.Iterator;
import juego.Juego;
import personaje.Enemigo;
import personaje.Jugador;

public class Pasar implements Comando{
    private final Jugador jugador;
    private final Juego juego;
    private final HashSet<Enemigo> enemigos;
    
    public Pasar(Juego juego, HashSet<Enemigo> enemigos){
        this.jugador = juego.getJugador();
        this.enemigos = enemigos;
        this.juego = juego;
    }
    
    @Override
    public void ejecutar() throws ComandoExcepcion {
        int energia = (int)(jugador.isToreado()? jugador.getEnergiaBase()*0.9:jugador.getEnergiaBase());
        
        jugador.setToreado(false);
        
        if(jugador.getBinoculares()){
            jugador.desequipar(jugador.getEquipacion().getBinoculares());
            jugador.setBinoculares(false);
        }
        
        jugador.setEnergia(energia);
        
        Iterator<Explosivo> explosivos = jugador.getMapa().getExplosivos().iterator();
        
        while(explosivos.hasNext()){
            Explosivo e = explosivos.next();
            e.explotar();
            jugador.getMapa().getExplosivos().remove(e);
        }
        
        HashSet<Enemigo> enemAux = new HashSet(enemigos);
        
        for(Enemigo e:enemAux){
            if(e.getSalud() == 0){
                juego.getEnemigos().remove(e);
            }
        }
        
        if(juego.getEnemigos().isEmpty()) juego.ganarJuego();
        
        for(Enemigo e:enemigos){
            e.IA(jugador);
        }
        
        if(jugador.getSalud() == 0){
            juego.perderJuego();
        }
    }
}
