package personaje;

import consola.Consola;
import consola.ConsolaNormal;
import entorno.Celda;
import entorno.Mapa;
import excepcion.InsuficienteEnergia;


public class Francotirador extends Jugador{
    public Francotirador( String nombre, int x, int y, int salud, int energia, Mapa mapa,Consola consola){
        super(nombre, x, y, salud, energia, mapa, true, consola);           
    }   

 
    @Override
    public void mover(Celda destino) throws InsuficienteEnergia{
        if(destino == null){
            consola.imprimir("Francotirador.mover(): Destino nulo");
            System.exit(0);
        }
        
        super.mover(destino, 1);
    }
    
    @Override
    public void atacar(Celda celda) throws InsuficienteEnergia{
        if(celda == null){
            consola.imprimir("Francotirador.atacar(): Celda nula");
            System.exit(0);
        }
        
        super.atacar(celda, Math.pow(getMapa().distanciaCeldas(getCeldaActual(), celda), 1.2));     
    }

    @Override
    public void atacar(Personaje victima) throws InsuficienteEnergia{
        if(victima == null){
            consola.imprimir("Francotirador.atacar(): Enemigo nulo");
            System.exit(0);
        }
        else if(!(victima instanceof Enemigo)){
            consola.imprimir("Francotirador.atacar(): Víctima no enemigo");
            System.exit(0);
        }
        
        super.atacar((Enemigo)victima, Math.pow(getMapa().distanciaCeldas(getCeldaActual(), victima.getCeldaActual()), 1.2));
    }
}