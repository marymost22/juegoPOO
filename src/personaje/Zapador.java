package personaje;

import consola.Consola;
import consola.ConsolaNormal;
import entorno.Celda;
import entorno.Mapa;
import excepcion.InsuficienteEnergia;
import item.Explosivo;
import item.Item;

public class Zapador extends Jugador{
    public Zapador( String nombre, int x, int y, int salud, int energia, Mapa mapa, Consola consola){
        super(nombre, x, y, salud, energia, mapa, true, consola);    
    }
    
    public void colocar(Explosivo explosivo){
        if(explosivo == null){
            consola.imprimir("Zapador.colocar(): Explosivo nulo");
            System.exit(0);
        }
        
        getCeldaActual().setDinamitada(true);
        getMapa().getExplosivos().add(explosivo);
        getMochila().removeItem(explosivo);
    }
    
    @Override
    public void mover(Celda destino) throws InsuficienteEnergia{
        if(destino == null){
            consola.imprimir("Zapador.mover(): Destino nulo");
            System.exit(0);
        }
        
        mover(destino, 1);
    }
    
    @Override
    public void atacar(Celda celda) throws InsuficienteEnergia{
        if(celda == null){
            consola.imprimir("Zapador.atacar(): Celda nula");
            System.exit(0);
        }
        
        if(getMapa().distanciaCeldas(getCeldaActual(), celda) <= 2){
            super.atacar(celda, 1);
        }
        else{
            super.atacar(celda, 0.05);
        }        
    }

    @Override
    public void atacar(Personaje victima) throws InsuficienteEnergia{
        if(victima == null){
            consola.imprimir("Zapador.atacar(): Enemigo nulo");
            System.exit(0);
        }
        else if(!(victima instanceof Enemigo)){
            consola.imprimir("Zapador.atacar(): Victima no enemigo");
            System.exit(0);
        }
        
        if(getMapa().distanciaCeldas(getCeldaActual(), victima.getCeldaActual()) <= 2){
            super.atacar((Enemigo)victima, 1);
        }
        else{
            super.atacar((Enemigo)victima, 0.05);
        }
    }
    
    @Override
    public boolean coger(Item item){
        if(item == null){
            consola.imprimir("Zapador.coger(): Item nulo");
            System.exit(0);
        }
        
        if(getCeldaActual().contains(item) ){
            getCeldaActual().removeItem(item);
            getMochila().addItem(item);
            consola.imprimir("Has cogido " + item.getNombre() + ". Ahora está es tu mochila");
            return true;
        }
        return false;
    }
}