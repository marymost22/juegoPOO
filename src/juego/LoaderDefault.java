package juego;

import consola.Consola;
import entorno.Mapa;
import java.util.HashSet;
import java.util.Random;
import personaje.*;


public class LoaderDefault implements LoaderJuego{
    @Override
    public Juego cargarJuego(String nombreJugador, Consola consola){
        Mapa mapa = new Mapa();
        Jugador jugador = null;
        HashSet<Enemigo> enemigos = new HashSet<>();
        
        int cordX = mapa.getAlto()/2;
        int cordY = mapa.getAlto()/2;
        
        mapa.getCelda(cordX, cordY).setTransitable(true);
        
        int numeroEnemigos = Const.numEnemigosDefecto;
        Random rnd = new Random();
        Enemigo enemigo = null;
        
        switch (nombreJugador){            
            case "marine":
                jugador = new Marine(nombreJugador, cordX, cordY, 100, 100, mapa, consola);
                break;
                
            case "zapador":
                jugador = new Zapador(nombreJugador, cordX, cordY, 100, 100, mapa, consola);
                break;

            case "francotirador":
                jugador = new Francotirador(nombreJugador, cordX, cordY, 100, 100, mapa, consola);
                break;
            default:
                return (null);
        }
        
        for (int i = 0; i < numeroEnemigos; i++){
            do{
                cordX = rnd.nextInt(Const.tamMapaDefecto);
                cordY = rnd.nextInt(Const.tamMapaDefecto);
            }while(!mapa.getCelda(cordX, cordY).isTransitable());
            
            switch (rnd.nextInt(3)){
                case 0:
                    enemigo = new Sectoid("sectoid", cordX, cordY, rnd.nextInt(100),rnd.nextInt(100), mapa, consola);
                    break;
                case 1:
                    enemigo = new HeavyFloater("heavyfloater", cordX, cordY, rnd.nextInt(100),rnd.nextInt(100), mapa, consola);
                    break;
                case 2:
                    enemigo = new LightFloater("lightfloater", cordX, cordY, rnd.nextInt(100),rnd.nextInt(100), mapa, consola);
                    break;
            }
            
            enemigos.add(enemigo);
        }
        
                
        for(Enemigo e:enemigos){
            e.asignarCelda();
        }
        Juego juego = new Juego(mapa, jugador, enemigos, consola);
        
        return(juego);
    }
}