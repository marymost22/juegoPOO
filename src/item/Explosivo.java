package item;

import entorno.Celda;
import entorno.Mapa;
import java.util.Random;
import personaje.Enemigo;
import personaje.Personaje;
import personaje.Zapador;

public class Explosivo extends Usable {
    private static int count = 0;
    private final int radio;
    private Mapa mapa;
    private Celda celda;
    
    public Explosivo() {
        super("explosivo" + (++count), "Hace boom", new Random().nextInt(6)+5);
        radio = new Random().nextInt(3) + 1;
        mapa = null;
        celda = null;
    }
    
    public void explotar(){
        for(int y = 0; y < mapa.getAlto(); y++){
            for(int x = 0; x < mapa.getAncho(); x++){
                if(Math.pow(x - celda.getCoordenadaX(), 2) + Math.pow(y - celda.getCoordenadaY(), 2) <= Math.pow(radio, 2)){
                    mapa.getCelda(x, y).setTransitable(true);
                    
                    for(Enemigo e:mapa.getCelda(x, y).getEnemigos()){
                        e.setSalud(0);
                        mapa.getCelda(x, y).getEnemigos().remove(e);
                    }
                }
            }
        }
        
        mapa.getExplosivos().remove(this);
        celda.setDinamitada(false);
    }

    @Override
    public void usar(Personaje personaje){
        ((Zapador)personaje).colocar(this);
        mapa = personaje.getMapa();
        celda = personaje.getCeldaActual();
    }    
    
    @Override
    public String getDetalles(){
        StringBuilder cadena = new StringBuilder();
        cadena.append("\tTipo: Explosivo\n");
        cadena.append("\tRadio de alcance: ").append(radio).append("\n");
    
    return cadena.toString();
    }    
}