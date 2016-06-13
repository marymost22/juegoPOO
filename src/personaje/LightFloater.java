package personaje;

import consola.Consola;
import entorno.Mapa;


public class LightFloater extends Floater{
    public LightFloater( String nombre, int x, int y, int salud, int energia, Mapa mapa,Consola consola){
        super(nombre, x, y, salud, energia, mapa, false, consola);
    }    
}
