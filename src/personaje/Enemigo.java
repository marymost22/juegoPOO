package personaje;
import consola.Consola;
import consola.ConsolaNormal;
import entorno.Celda;
import entorno.Mapa;
import entorno.Ruta;
import item.Equipable;
import item.*;
import java.util.HashSet;
import java.util.Random;

public abstract class Enemigo extends Personaje{
    private static int count = 0;
    
    public Enemigo(String nombre, int x, int y, int salud, int energia, Mapa mapa, boolean restriccion, Consola consola){
        super(nombre + (++count), x, y, salud, energia, mapa, restriccion, consola );
        setBinoculares(false);        
    }    
    
    @Override
    public void mover(Celda celdaDestino){       
        if(celdaDestino == null){
           consola.imprimir("Enemigo.mover(): Destino nulo");
            System.exit(0);
        }
        
        Ruta ruta = new Ruta(this.getMapa(), this.getCeldaActual(), celdaDestino);
        int energiaNecesaria = ruta.getPasos() *( 5 + (int)this.getMochila().getPesoActual()/5);
        
        if(ruta.getRuta() == null || this.getEnergia() < energiaNecesaria){
            return;
        }

        this.setCeldaActual(celdaDestino);
        this.setEnergia(this.getEnergia()- energiaNecesaria);        
    }  
    
    @Override
    public void atacar(Personaje jugador) { //En este caso, ataca al jugador si puede, de lo contrario se mueve.
        if(jugador == null){
           consola.imprimir("Enemigo.atacar(): Jugador nulo");
            System.exit(0);
        }
        
        Random rnd = new Random();
        int golpeCritico = rnd.nextInt(4) == 0? 3:1;
        int dano = (int)((rnd.nextInt(2) + 5) * golpeCritico * getEquipacion()
                .getIndiceDano(getMapa(), getCeldaActual(), jugador.getCeldaActual()));

        if(dano == 0){
            Celda celdaDestino = null;
            
            do{
                 celdaDestino= elegirCelda(this.getCeldaActual());
            }while(celdaDestino == null || !celdaDestino.isTransitable());
            
            mover(celdaDestino);
            equiparMejorObjeto();
        }     
         
        else{
        
            jugador.setSalud((int)(jugador.getSalud() - dano*jugador.getEquipacion().getIndiceArmadura()));

            setEnergia(getEnergia() - 30);

            consola.imprimir(getNombre() + " te ha atacado usando "+
                    getEquipacion().armasToString(getMapa(), getCeldaActual(), jugador.getCeldaActual()));

            if(golpeCritico == 3){
                consola.imprimir("Golpe crítico!");
            }
        }
    }
    
    protected Celda elegirCelda(Celda celdaActual){
        if(celdaActual == null){
           consola.imprimir("Enemigo.elegirCelda(): Celda nula");
            System.exit(0);
        }
        
        int min = -1;
        int max = 1;
        
        int cordX = (int) (celdaActual.getCoordenadaX() + (Math.random()*(max-min+1))+min);
        int cordY = (int) (celdaActual.getCoordenadaY() + (Math.random()*(max-min+1))+min);
        
        Celda celdaDestino = this.getMapa().getCelda(cordX, cordY);
        
        return celdaDestino;
    }
    
    protected void equiparMejorObjeto(){ //Los enemigos NO se pueden equipar binoculares
        HashSet<Item> items = new HashSet(this.getCeldaActual().getItems());
        boolean equipado;
        for (Item o : items){
            if(o instanceof Equipable){
                equipado = getEquipacion().equipar((Equipable)o);
                if(!equipado){
                    if (o instanceof Arma && getEquipacion().better((Arma)o)){
                        this.getEquipacion().desequipar(getEquipacion().getWorstArma());
                        this.equipar(o);
                        this.getCeldaActual().removeItem(o);
                    }
                    if ( o instanceof Armadura && getEquipacion().better((Armadura)o)){
                        this.getEquipacion().desequipar(getEquipacion().getArmadura());
                        this.equipar(o);
                        this.getCeldaActual().removeItem(o);
                    }
                }
            }
        }
    }
    
    public void asignarCelda(){
        getCeldaActual().addEnemigo(this);
    }
    
    //Función que controla de forma automática el comportamiento de los enemigos
    public void IA(Jugador jugador){
        if(jugador == null){
           consola.imprimir("Enemigo.IA(): Jugador nulo");
            System.exit(0);
        }
        
        ConsolaNormal consola = new ConsolaNormal();
        Random rnd = new Random();
        int maximoAcciones = rnd.nextInt(8);

        while(this.getEnergia() >= 20 ){
            //Sí el jugador está dentro del rango de visión, se le ataca, en caso contrarío el enemigo se mueve aleatoriamente
            if (Math.pow(jugador.getCeldaActual().getCoordenadaX() - this.getCeldaActual().getCoordenadaX(), 2) 
                    + Math.pow(jugador.getCeldaActual().getCoordenadaY() - this.getCeldaActual().getCoordenadaY(), 2)
                    > Math.pow(this.getRangoVisionBase(), 2) && maximoAcciones>0 && getEnergia()>=20) {
                Celda celdaDestino = elegirCelda(this.getCeldaActual());
                if(celdaDestino != null && celdaDestino.isTransitable()){
                    mover(celdaDestino);
                    maximoAcciones--;
                    equiparMejorObjeto();
                }
            }
            else if(this.getEnergia()>=30 && maximoAcciones>0){
                atacar(jugador);
                maximoAcciones--;
            }
            
            else{
                setEnergia(0);
            }

        }
        this.setEnergia(getEnergiaBase());   
    }
    @Override
     public void setCeldaActual(Celda celdaActual) {
         
        if(celdaActual == null){
            consola.imprimir("Personaje.setCeldaActual(): Celda nula");
            System.exit(0);
        }
        else if(!celdaActual.isTransitable()){
            consola.imprimir("Personaje.setCeldaActual(): Celda no transitable");
        }
        else{
            
            getCeldaActual().removeEnemigo(this);
            super.setCeldaActual(celdaActual);
            getCeldaActual().addEnemigo(this);
        }
    }
}