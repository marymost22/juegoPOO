package personaje;

import consola.Consola;
import entorno.Mapa;

public abstract class Floater extends Enemigo{
    public Floater(String nombre, int x, int y, int salud, int energia, Mapa mapa, boolean restriccion, Consola consola) {
        super(nombre, x, y, salud, energia, mapa, restriccion, consola);
    }
}
