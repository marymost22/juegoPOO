package personaje;

import consola.Consola;
import consola.ConsolaNormal;
import entorno.Celda;
import entorno.Mapa;
import excepcion.InsuficienteEnergia;


public class Marine extends Jugador{
    public Marine( String nombre, int x, int y, int salud, int energia, Mapa mapa, Consola consola){
        super(nombre, x, y, salud, energia, mapa, false, consola);        
    }
   
    @Override
    public void mover(Celda destino) throws InsuficienteEnergia{
        if(destino == null){
            consola.imprimir("Marine.mover(): Destino nulo");
            System.exit(0);
        }
        super.mover(destino, 2); //El marine es más pesado, le cuesta el doble moverse
    }
    
    @Override
    public void atacar(Celda celda) throws InsuficienteEnergia{
        if(celda == null){
            consola.imprimir("Marine.atacar(): Celda nula");
            System.exit(0);
        }
        
        if(getMapa().distanciaCeldas(getCeldaActual(), celda) <= 2){
            super.atacar(celda, 2);
        }
        else{
            super.atacar(celda, 0.05);
        }        
    }

    @Override
    public void atacar(Personaje victima) throws InsuficienteEnergia{
        if(victima == null){
            consola.imprimir("Marine.atacar(): Enemigo nulo");
            System.exit(0);
        }
        else if(!(victima instanceof Enemigo)){
            consola.imprimir("Marine.atacar(): Personaje no enemigo");
            System.exit(0);
        }
        
        if(getMapa().distanciaCeldas(getCeldaActual(), victima.getCeldaActual()) <= 2){
            super.atacar((Enemigo)victima, 2);
        }
        else{
            super.atacar((Enemigo)victima, 0.05);
        }
    }
}