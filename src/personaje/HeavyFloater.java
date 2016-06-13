package personaje;

import consola.Consola;
import entorno.Mapa;

public class HeavyFloater extends Floater {
    public HeavyFloater( String nombre, int x, int y, int salud, int energia, Mapa mapa, Consola consola){
        super(nombre, x, y, salud, energia, mapa, false, consola);
    }    
}