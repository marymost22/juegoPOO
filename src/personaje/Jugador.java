package personaje;

import consola.Consola;
import entorno.Celda;
import entorno.Mapa;
import entorno.Ruta;
import excepcion.InsuficienteEnergia;
import item.Binoculares;
import item.Equipable;
import item.Item;
import java.util.HashSet;
import java.util.Random;
import juego.Acciones;
import juego.Const;

public abstract class Jugador extends Personaje{
    private final Acciones acciones;    
    
    public abstract void atacar(Celda celda) throws InsuficienteEnergia;
    
    public Jugador(String nombre, int x, int y, int salud, int energia, Mapa mapa, boolean restriccion,Consola consola){
        super(nombre, x, y, salud, energia, mapa, restriccion, consola);
        acciones = new Acciones(getMapa().getAlto() + 1);
        modificarRangoVision();
    }

    public Acciones getAcciones() {
        return acciones;
    }
    
    @Override
    public void setCeldaActual(Celda celda){
        if(celda == null){
            consola.imprimir("Jugador.setCeldaActual(): Celda nula");
            System.exit(0);
        }
        
        getCeldaActual().setContieneJugador(false);
        super.setCeldaActual(celda);
        getCeldaActual().setContieneJugador(true);
    }
    
    protected void atacar(Enemigo enemigo, double multDano) throws InsuficienteEnergia{
        
        if(enemigo == null){
            consola.imprimir("Jugador.atacar(): Enemigo nulo");
            System.exit(0);
        }
        
        if(this.getEnergia()<Const.energiaAtacar){
            throw new InsuficienteEnergia();
        }
        
        int critico = new Random().nextInt(4) == 0? 3:1;
        
        int dano = (int)(10 * multDano * critico * getEquipacion()
                .getIndiceDano(getMapa(), getCeldaActual(), enemigo.getCeldaActual()));
        
        if(dano == 0){
            consola.imprimir("Estás demasiado lejos del enemigo");
            return;
        }        
  
        consola.imprimir("Atacas con " + getEquipacion()
                .armasToString(getMapa(), getCeldaActual(), enemigo.getCeldaActual()));

        if(critico == 3){
            consola.imprimir("Golpe crítico!");
        }

        setEnergia(getEnergia()-Const.energiaAtacar);
        
        enemigo.setSalud((int)(enemigo.getSalud() - dano*enemigo.getEquipacion().getIndiceArmadura()));

        if(enemigo.getSalud() <= 0){
            enemigo.getCeldaActual().getEnemigos().remove(enemigo);
            consola.imprimir("Has acabado con " + enemigo.getNombre());
        }       
    }
    
    protected void atacar(Celda celda, double multDano) throws InsuficienteEnergia{
        if(celda == null){
            consola.imprimir("Jugador.atacar(): Celda nula");
            System.exit(0);
        }
        
        if(this.getEnergia()<Const.energiaAtacar){
            throw new InsuficienteEnergia();
        }
        
        HashSet<Enemigo> enemigosCelda = celda.getEnemigos();
        
        int critico = new Random().nextInt(4) == 0? 3:1;
        
        int dano = (int)(10 * multDano * critico * getEquipacion()
                .getIndiceDano(getMapa(), getCeldaActual(), celda));
        
        if(dano == 0){
            consola.imprimir("Estás demasiado lejos del enemigo");
            return;
        }
        
        consola.imprimir("Atacas con " + getEquipacion()
                .armasToString(getMapa(), getCeldaActual(), celda));
            
        if(critico == 3){
            consola.imprimir("Golpe crítico!");
        }

        dano /= enemigosCelda.size();
        HashSet<Enemigo> enemigos = new HashSet(enemigosCelda);
        
        consola.imprimir("Has atacado a " + enemigosCelda.size() + " enemigos");
        setEnergia(getEnergia()-Const.energiaAtacar);
        
        for(Enemigo e:enemigos){
            e.setSalud((int)(e.getSalud() - dano*e.getEquipacion().getIndiceArmadura()));

            if(e.getSalud() <= 0){
                HashSet<Item> contenidoMochila = e.getMochila().getContenido();
                
                for(Item o : contenidoMochila){
                    e.getCeldaActual().addItem(o);
                }
                HashSet <Equipable> equipacion = e.getEquipacion().getEquipables();
                for (Item o: equipacion){
                    e.getCeldaActual().addItem(o);
                }
                
                enemigosCelda.remove(e);
                consola.imprimir("Has acabado con " + e.getNombre());
            }
        }
    }
    
    protected void mover(Celda celdaDestino, int indiceConsumo) throws InsuficienteEnergia{ 
        if(celdaDestino == null){
            consola.imprimir("Jugador.mover(): Destino nulo");
            System.exit(0);
        }
        
        Ruta ruta = new Ruta(this.getMapa(), this.getCeldaActual(), celdaDestino);
        int energiaNecesaria = indiceConsumo * ruta.getPasos() *( 5 + (int)this.getMochila().getPesoActual()/5);
        
        if (this.getEnergia() < energiaNecesaria){
            throw new InsuficienteEnergia();
        }
        
        this.setCeldaActual(celdaDestino);
        this.setEnergia(this.getEnergia()- energiaNecesaria);        
        this.modificarRangoVision();
    }
    
    public void mirar(){       
        if (getCeldaActual().getItems().isEmpty()) {
            consola.imprimir("No has encontrado nada");
        }
        else {
            consola.imprimir("Has encontrado: ");
            consola.imprimir(getCeldaActual().toString());
        }
    }
    
    public void mirar(Item item){
        if(item == null){
            consola.imprimir("Jugador.mirar(): Item nulo");
            System.exit(0);
        }
        
        consola.imprimir(item.getDetalles());        
    }
    
    public void mirar(Celda celda){
        if(celda == null){
            consola.imprimir("Jugador.mirar(1): Celda nula");
            System.exit(0);
        }
        
        HashSet<Enemigo> enemigos = celda.getEnemigos();
        
        if(!enemigos.isEmpty()){
            consola.imprimir("Aquí hay:");
            
            for(Enemigo e: enemigos){
                consola.imprimir("\t- " + e.getNombre());
            }
        }
        else{
            consola.imprimir("No hay ningún enemigo aquí.");
        }
    }
    
    public void mirar(Celda celda, Enemigo enemigo) {
        if(celda == null){
            consola.imprimir("Jugador.mirar(1): Celda nula");
            System.exit(0);
        }
        else if(enemigo == null){
            consola.imprimir("Jugador.mirar(1): Enemigo nulo");
            System.exit(0);
        }
        
        if(celda.getEnemigos().contains(enemigo)){
            consola.imprimir(enemigo.getDetalles());
        }
        else{
            consola.imprimir("Ese enemigo existe, pero no está aquí.");
        }      
    }
    
     public final void modificarRangoVision() {
        for (int y = 0; y < getMapa().getAlto(); y++) {
            for (int x = 0; x < getMapa().getAncho(); x++) {
                if(getEquipacion().getBinoculares()!=null){
                    if (Math.pow(x - getCeldaActual().getCoordenadaX(), 2) + Math.pow(y - getCeldaActual().getCoordenadaY(), 2) 
                            > Math.pow(getRangoVisionBase()+getEquipacion().getRangoBinoculares(), 2)) {
                        getMapa().getCelda(x, y).setVisible(false);
                    } else {
                        getMapa().getCelda(x, y).setVisible(true);
                    }
                }
                else{
                    if (Math.pow(x - getCeldaActual().getCoordenadaX(), 2) + Math.pow(y - getCeldaActual().getCoordenadaY(), 2)  
                            > Math.pow(getRangoVisionBase(), 2)) {
                        getMapa().getCelda(x, y).setVisible(false);
                    } else {
                        getMapa().getCelda(x, y).setVisible(true);
                    }
                }
            }
        }
        getCeldaActual().setContieneJugador(true);
    }
    
    @Override
    public boolean equipar(Item item){
        if(item == null){
            consola.imprimir("Jugador.equipar(): Item nulo");
            System.exit(0);
        }
        
        if(super.equipar(item)){
            consola.imprimir("Te has equipado " + item.getNombre());
            if(item instanceof Binoculares){
                modificarRangoVision();
            }
            return true;
        }
        
        consola.imprimir("No te has podio equipar " + item.getNombre());
        return false;
    }
    
    @Override
    public boolean desequipar(Item item){
        if(item == null){
            consola.imprimir("Jugador.desequipar(): Item nulo");
            System.exit(0);
        }
        
        if(super.desequipar(item)){
            consola.imprimir("Te has desequipado " + item.getNombre());
            if(item instanceof Binoculares){
                modificarRangoVision();
            }
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean coger (Item item){
        if(item == null){
            consola.imprimir("Jugador.coger(): Item nulo");
            System.exit(0);
        }
        
        if(super.coger(item)){
            consola.imprimir("Has cogido " + item.getNombre() + ". Ahora está es tu mochila");
            return true;
        }
        
        consola.imprimir("No puedes coger " + item.getNombre());
        return false;
    }
    
    @Override
    public boolean tirar (Item item){
        if(item == null){
            consola.imprimir("Jugador.tirar(): Item nulo");
            System.exit(0);
        }
        
        if(super.tirar(item)){
            consola.imprimir("Has dejado " + item.getNombre());
            return true;
        }
        
        return false;
    }
    
    public void inventario(){
        consola.imprimir( getMochila().getNombre()+": " + getMochila().getDescripcion() + "\n" 
                + getMochila().toString() + "\n" + "Peso actual: " + getMochila().getPesoActual()+ "/" + getMochila().getPesoMax() + "\n"
                +"Elementos dentro: " + getMochila().getNumeroElementosActual()+"/"+getMochila().getNumeroElementosMax());
        
        consola.imprimir(this.getDetalles());
    }
}